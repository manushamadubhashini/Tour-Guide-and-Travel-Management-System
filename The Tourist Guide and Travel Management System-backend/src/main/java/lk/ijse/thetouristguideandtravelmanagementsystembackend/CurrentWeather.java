package lk.ijse.thetouristguideandtravelmanagementsystembackend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentWeather {
    private double temp;
    private String condition;
    private String description;
    private int humidity;
    private double wind;
}
