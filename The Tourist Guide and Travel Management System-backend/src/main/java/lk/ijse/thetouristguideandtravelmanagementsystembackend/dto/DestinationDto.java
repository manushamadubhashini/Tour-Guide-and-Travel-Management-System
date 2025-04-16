package lk.ijse.thetouristguideandtravelmanagementsystembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DestinationDto {
    private String id;
    private String name;
    private String description;
    private String location;
    private String weather_info;
    private String best_time_to_visit;

}
