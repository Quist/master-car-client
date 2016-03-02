package carsystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;


public class CarService {

    private final CarHttp http;

    public CarService(CarHttp http) {
        this.http = http;
    }

    public void create(String type) {
        Car car = new Car(type);
        String carJson = new Gson().toJson(car);
        try {
            http.post("/cars", carJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getCars() {
        HttpResponse response;
        try {
            response = http.get("/cars");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        List<Car> cars = new Gson().fromJson(response.getResponse(), new TypeToken<List<Car>>() {
        }.getType());

        return cars;
    }

}