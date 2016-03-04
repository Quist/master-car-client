package carsystem;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Client {

    private final CarService carService;

    public static void main(String args[]) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: client serverUrl");
            System.exit(1);
        }

        String url = args[0];
        carsystem.Client client = new carsystem.Client(new URL(url));
        client.start();
    }

    public Client(URL url) {
        CarHttp http = new CarHttp(url);
        this.carService = new CarService(http);
    }

    public void start() {
        commandLoop();
    }

    private void commandLoop() {
        String input = "";
        Scanner scanner = new Scanner(System.in);

        while (!input.equals("exit")) {
            System.out.print("Input > ");
            input = scanner.nextLine();
            parseCommand(input);
        }

        System.out.println("Exiting..");
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("get %id - Get all cars or retrieves a car with an ID");
        System.out.println("create - %type Create a new car");
        System.out.println("update - %id %type Update type of car");
        System.out.println("delete %id -  Delete all cars, or the car with ID");

    }

    private void parseCommand(String command) {
        System.out.println("Parsing: " + command);
        if (command.equals("help")) {
            printHelp();
        } else if (command.startsWith("create ")) {
            createCar(command.substring("create ".length(), command.length()));
        } else if (command.equals("get") || command.startsWith("get ")) {
            parseGetCommand(command.substring("get".length(), command.length()));
        } else if (command.startsWith("delete ")) {
            parseDeleteCommand(command.substring("delete ".length(), command.length()));
        } else if (command.startsWith("update ")) {
            parseUpdateCommand(command.substring("update ".length(), command.length()));
        } else {
            System.out.println("Unknown command");
        }
    }

    private void parseUpdateCommand(String args) {
        String arglist[] = args.split(" ");
        if (arglist.length < 2) {
            System.out.println("Too few arguments. Usage: update %id %newtype");
            return;
        }

        Car car = new Car(arglist[0], arglist[1]);
        carService.updateCar(car);
    }

    private void createCar(String args) {
        carService.create(args);
    }

    private void parseGetCommand(String args) {
        if (args.trim().equals("")) {
           getCars();
        } else {
            getCar(args.trim());
        }
    }

    private void parseDeleteCommand(String args) {
        if (args.trim().equals("")) {
            System.out.println("Usage: delete %id");
        } else {
            deleteCar(args.trim());
        }
    }

    private void deleteCar(String carId) {
        carService.deleteCar(carId);
    }

    private void getCar(String carId) {
        Car car = carService.getCar(carId);
        if (car == null) {
            System.out.println("Car with registration number " + carId + " was not found");
        } else {
            System.out.println(car.toString());
        }
    }

    private void getCars() {
        List<Car> cars = carService.getCars();
        for(Car car : cars) {
            System.out.println(car.toString());
        }
    }
}
