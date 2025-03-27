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
    private String imageBase64;

    public TourDto(String id, String destinationId, String name, String description, int duration, double price, MultipartFile image) {
        this.id = id;
        this.destinationId = destinationId;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.image = image;
    }
}
