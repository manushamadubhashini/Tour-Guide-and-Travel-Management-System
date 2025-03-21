package lk.ijse.thetouristguideandtravelmanagementsystembackend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeatherResponse {
    private CurrentWeather current = new CurrentWeather();
    private List<ForecastDay> forecast = new ArrayList<>();
    private LocationInfo location = new LocationInfo();
}