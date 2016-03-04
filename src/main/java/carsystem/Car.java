package carsystem;

import java.util.Random;

public class Car {
    private final String registration;
    private final String type;

    public Car(String type) {
        this.type = type;
        this.registration = createRegistration();
    }

    public Car(String registration, String type) {
        this.registration = registration;
        this.type = type;
    }

    public String getRegistration() {
        return registration;
    }

    @Override
    public String toString() {
        return String.format("Type: %-15s Registration: %s", type, registration);
    }

    private String createRegistration() {
        String reg = "";
        Random random = new Random();
        for (int i = 0; i< 10; i++) {
            reg+= random.nextInt(10);
        }
        return reg;
    }

}
