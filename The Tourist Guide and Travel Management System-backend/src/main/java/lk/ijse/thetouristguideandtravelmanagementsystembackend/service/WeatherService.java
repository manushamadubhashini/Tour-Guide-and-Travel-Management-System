package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.CurrentWeather;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.ForecastDay;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherResponse fetchWeatherData(String location) {
        // Create the response object
        WeatherResponse response = new WeatherResponse();

        // Fetch current weather
        String currentWeatherUrl = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s",
                location, apiKey

        );

        Map<String, Object> currentWeatherResponse = restTemplate.getForObject(
                currentWeatherUrl, Map.class
        );

        // Extract current weather data
        CurrentWeather current = new CurrentWeather();

        if (currentWeatherResponse != null) {
            Map<String, Object> mainData = (Map<String, Object>) currentWeatherResponse.get("main");
            List<Map<String, Object>> weatherData = (List<Map<String, Object>>) currentWeatherResponse.get("weather");
            Map<String, Object> windData = (Map<String, Object>) currentWeatherResponse.get("wind");
            Map<String, Object> sysData = (Map<String, Object>) currentWeatherResponse.get("sys");

            if (mainData != null && weatherData != null && !weatherData.isEmpty()) {
                current.setTemp(((Number) mainData.get("temp")).doubleValue());
                current.setHumidity(((Number) mainData.get("humidity")).intValue());
                current.setCondition((String) weatherData.get(0).get("main"));
                current.setDescription((String) weatherData.get(0).get("description"));

                if (windData != null) {
                    current.setWind(((Number) windData.get("speed")).doubleValue());
                }
            }

            // Set location info
            response.getLocation().setName((String) currentWeatherResponse.get("name"));
            if (sysData != null) {
                response.getLocation().setCountry((String) sysData.get("country"));
            }
        }

        response.setCurrent(current);

        // Fetch 5-day forecast
        String forecastUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&appid=%s",
                location, apiKey
        );

        Map<String, Object> forecastResponse = restTemplate.getForObject(
                forecastUrl, Map.class
        );

        if (forecastResponse != null) {
            List<Map<String, Object>> forecastList = (List<Map<String, Object>>) forecastResponse.get("list");
            List<ForecastDay> formattedForecast = formatForecast(forecastList);
            response.setForecast(formattedForecast);
        }

        return response;
    }

    private List<ForecastDay> formatForecast(List<Map<String, Object>> forecastList) {
        // This is a simplified implementation
        // In a production app, you would group by day and calculate daily averages

        List<ForecastDay> result = new ArrayList<>();

        // Process only one entry per day (every 8th entry is roughly a day)
        for (int i = 0; i < forecastList.size() && result.size() < 5; i += 8) {
            Map<String, Object> forecast = forecastList.get(i);

            ForecastDay day = new ForecastDay();

            // Get date
            long timestamp = ((Number) forecast.get("dt")).longValue() * 1000;
            day.setDate(new java.util.Date(timestamp).toString());

            // Get temp and condition
            Map<String, Object> main = (Map<String, Object>) forecast.get("main");
            List<Map<String, Object>> weather = (List<Map<String, Object>>) forecast.get("weather");

            if (main != null && weather != null && !weather.isEmpty()) {
                day.setAvgTemp(((Number) main.get("temp")).doubleValue());
                day.setCondition((String) weather.get(0).get("main"));
            }

            result.add(day);
        }

        return result;
    }
}

