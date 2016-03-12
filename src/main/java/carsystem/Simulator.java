package carsystem;

public class Simulator {

    private final CarService carService;

    Simulator(CarService carService){
        this.carService = carService;
    }

    public void start() {
        testCase1();
    }

    private void testCase1() {
        Car bmw = carService.create("BWM");
        if (!bmw.getType().equals("BMW")) {
            System.out.println("We created a car with type BMW, but type returned was: " + bmw.getType());
        }

        carService.create("Mercedes");
        carService.create("JEEP");
        carService.create("Leopard 2");
        carService.create("BV 206");

        if (carService.getCars().size() != 5) {
            System.out.println("We created 5 cars, but getCars returned " + carService.getCars().size() + " cars!");
        }

        if (! hasSameTypeOnServer(bmw)) {
            System.out.println("Getting our BWM based on type did not return the correct type");
        }

        Car updatedBmw = new Car(bmw.getRegistration(), "ULTRA BMW");
        carService.updateCar(updatedBmw);
        hasSameTypeOnServer(updatedBmw);

        carService.deleteCar(updatedBmw.getRegistration());
        if (carService.getCars().size() != 4) {
            System.out.println("We created 5 cars then deleted 1, but getCars returned " + carService.getCars().size() + " cars!");
        }

    }

    private boolean hasSameTypeOnServer(Car bmw) {
        Car resultCar = carService.getCar(bmw.getRegistration());
        return resultCar.getType().equals(bmw.getType());
    }
}
