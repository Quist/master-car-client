package carsystem;

import java.util.logging.Logger;

public class Simulator {
    private final static Logger logger = Logger.getLogger(Simulator.class.getName());

    private final CarService carService;

    Simulator(CarService carService){
        this.carService = carService;
    }

    public void start() {
        testCase1();
    }

    private void testCase1() {
        logger.info("Starting test case 1");

        carService.deleteAllCars();

        Car bmw = carService.create("BMW");
        if (!bmw.getType().equals("BMW")) {
            logger.warning("We created a car with type BMW, but type returned was: " + bmw.getType());
            throw new RuntimeException("We created a car with type BMW, but type returned was: " + bmw.getType());
        }

        carService.create("Mercedes");
        carService.create("JEEP");
        carService.create("Leopard 2");
        carService.create("BV 206");

        if (carService.getCars().size() != 5) {
            logger.warning("We created 5 cars, but getCars returned " + carService.getCars().size() + " cars!");
            throw new RuntimeException("We created 5 cars, but getCars returned " + carService.getCars().size() + " cars!");
        }

        if (! hasSameTypeOnServer(bmw)) {
            logger.warning("Getting our BWM based on type did not return the correct type");
            throw new RuntimeException("Getting our BWM based on type did not return the correct type");
        }

        Car updatedBmw = new Car(bmw.getRegistration(), "ULTRA BMW");
        carService.updateCar(updatedBmw);
        hasSameTypeOnServer(updatedBmw);

        carService.deleteCar(updatedBmw.getRegistration());
        if (carService.getCars().size() != 4) {
            logger.warning("We created 5 cars then deleted 1, but getCars returned " + carService.getCars().size() + " cars!");
            throw new RuntimeException("We created 5 cars then deleted 1, but getCars returned " + carService.getCars().size() + " cars!");
        }

        logger.info("Successfully finished running test case 1");
    }

    private boolean hasSameTypeOnServer(Car bmw) {
        Car resultCar = carService.getCar(bmw.getRegistration());
        return resultCar.getType().equals(bmw.getType());
    }
}
