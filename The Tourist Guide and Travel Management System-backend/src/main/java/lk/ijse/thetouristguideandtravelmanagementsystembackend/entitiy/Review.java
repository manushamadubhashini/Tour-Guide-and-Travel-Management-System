package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @Column(name = "review_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "tour_id",nullable = false)
    private Tour tour;
    @Column(nullable = false,length = 400)
    private String comment;
    @Column(nullable = false)
    private String rating;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "tourist_name",nullable = false)
    private String touristName;

}
