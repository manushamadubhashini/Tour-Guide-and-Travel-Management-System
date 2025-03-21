package lk.ijse.thetouristguideandtravelmanagementsystembackend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForecastDay {
    private String date;
    private double avgTemp;
    private String condition;
}
