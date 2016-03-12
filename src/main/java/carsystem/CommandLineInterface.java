package carsystem;

import java.util.Scanner;

public class CommandLineInterface {

    private final Client client;

    public CommandLineInterface(Client client) {
        this.client = client;
    }

    public void start() {
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
            parseCreateCommand(command.substring("create ".length(), command.length()));
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

    private void parseGetCommand(String args) {
        if (args.trim().equals("")) {
            client.printCars();
        } else {
            client.printCar(args.trim());
        }
    }

    private void parseCreateCommand(String args) {
        String type = args;
        client.createCar(type);
    }

    private void parseDeleteCommand(String args) {
        if (args.trim().equals("")) {
            System.out.println("Usage: delete %id");
        } else {
            client.deleteCar(args.trim());
        }
    }

    private void parseUpdateCommand(String args) {
        String arglist[] = args.split(" ");
        if (arglist.length < 2) {
            System.out.println("Too few arguments. Usage: update %id %newtype");
            return;
        }

        String registration = arglist[0];
        String type = arglist[1];
        client.updateCar(registration, type);
    }

}
