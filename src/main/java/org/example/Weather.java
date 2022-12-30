package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    public String getWeather(String city, Model model) {
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=075166e3720f4882df2bd4556586fd4c");
            Scanner in = new Scanner((InputStream) url.getContent());

            StringBuilder result = new StringBuilder();
            while (in.hasNext()) {
                result.append(in.nextLine());
            }

            JSONObject object = new JSONObject(result.toString());
            model.setName(object.getString("name"));

            JSONObject main = object.getJSONObject("main");
            model.setTemp(main.getDouble("temp"));
            model.setHumidity(main.getDouble("humidity"));

            JSONArray getArray = object.getJSONArray("weather");
            for (int i = 0; i < getArray.length(); i++) {
                JSONObject obj = getArray.getJSONObject(i);
                model.setMain((String) obj.get("main"));
            }

            return "City: " + model.getName() + "\n" +
                    "Temperature: " + model.getTemp() + "C" + "\n" +
                    "Humiditi: " + model.getHumidity() + "%" + "\n" +
                    "Main: " + model.getMain();
        } catch (IOException e) {
            throw new RuntimeException("The city with the name - \"" + city + "\" ,does not exist");
        }
    }
}
