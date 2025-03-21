package lk.ijse.thetouristguideandtravelmanagementsystembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TourDto {
    private String id;
    private String destinationId;
    private String name;
    private String description;
    private int duration;
    private double price;
    private MultipartFile image;
}
