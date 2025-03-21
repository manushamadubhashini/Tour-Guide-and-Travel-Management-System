package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.WeatherResponse;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<WeatherResponse> getWeatherData(@RequestParam String location) {
        if (location == null || location.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            WeatherResponse weatherData = weatherService.fetchWeatherData(location);
            return ResponseEntity.ok(weatherData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}