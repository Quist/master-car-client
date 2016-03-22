package carsystem;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Client {

    private final CarService carService;

    public static void main(String args[]) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: client [--client] serverUrl");
            System.exit(1);
        }


        if (args.length > 1 && args[0].equals("--client")) {
            String url = args[1];
            carsystem.Client client = new carsystem.Client(new URL(url));
            new CommandLineInterface(client).start();
        } else {
            String url = args[0];
            new Simulator(new CarService(new CarHttp(new URL(url)))).start();
        }
    }

    public Client(URL url) {
        CarHttp http = new CarHttp(url);
        this.carService = new CarService(http);
    }


    public void updateCar(String registartion, String type) {
        Car car = new Car(registartion, type);
        carService.updateCar(car);
    }

    public void createCar(String type) {
        carService.create(type);
    }

    public void deleteCar(String carId) {
        carService.deleteCar(carId);
    }

    public void printCar(String carId) {
        Car car = carService.getCar(carId);
        if (car == null) {
            System.out.println("Car with registration number " + carId + " was not found");
        } else {
            System.out.println(car.toString());
        }
    }

    public void printCars() {
        List<Car> cars = carService.getCars();
        for(Car car : cars) {
            System.out.println(car.toString());
        }
    }
}
