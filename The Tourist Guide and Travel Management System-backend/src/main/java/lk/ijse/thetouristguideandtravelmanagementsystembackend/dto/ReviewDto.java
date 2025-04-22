package lk.ijse.thetouristguideandtravelmanagementsystembackend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Tour;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDto {
    private String id;
    private String userId;
    private String comment;
    private String rating;
    private String touristName;
}
