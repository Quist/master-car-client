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


    }
}
