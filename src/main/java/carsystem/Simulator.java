package carsystem;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.*;

public class Simulator {
    private final static Logger logger = Logger.getLogger(Simulator.class.getName());

    private final CarService carService;
    private final int n;

    Simulator(CarService carService, int n) {
        this.n = n;
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.setUseParentHandlers(false);
        consoleHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return "[" + record.getLevel() + "] "
                        + record.getSourceClassName() + ":"
                        + record.getSourceMethodName() + " "
                        + record.getMessage() + "\n";
            }
        });

        logger.addHandler(consoleHandler);

        this.carService = carService;
    }

    public void start() {
        DescriptiveStatistics stats = new DescriptiveStatistics();

        logger.info("Running " + n + " tests");
        for (int i = 0; i < n; i++) {
            long ts1 = System.currentTimeMillis();
            testCase1();
            logger.info("Finished running test run " + i);
            long ts2 = System.currentTimeMillis();
            stats.addValue(ts2-ts1);
        }

        logger.info("Finished running " + n + " tests");
        logger.info(LocalDateTime.now().toString());
        logger.info("Mean: " + stats.getMean());
        logger.info("Standard Deviation: " + stats.getStandardDeviation());
        logger.info("Variance: " + stats.getVariance());
        logger.info("Min: " + stats.getMin());
        logger.info("Max: " + stats.getMax());
        logger.info("Median: " + stats.getPercentile(50));

    }

    private void testCase1() {
        logger.fine("Starting test case 1");

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

        logger.fine("Successfully finished running test case 1");
    }

    private boolean hasSameTypeOnServer(Car bmw) {
        Car resultCar = carService.getCar(bmw.getRegistration());
        return resultCar.getType().equals(bmw.getType());
    }
}
