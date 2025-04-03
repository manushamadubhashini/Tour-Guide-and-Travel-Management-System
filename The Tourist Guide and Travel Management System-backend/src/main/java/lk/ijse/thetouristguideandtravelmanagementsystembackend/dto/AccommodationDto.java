package lk.ijse.thetouristguideandtravelmanagementsystembackend.dto;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Destination;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccommodationDto {
    private String id;
    private String tourId;
    private String name;
    private String description;
    private String type;
    private String address;
    private double price;

}
