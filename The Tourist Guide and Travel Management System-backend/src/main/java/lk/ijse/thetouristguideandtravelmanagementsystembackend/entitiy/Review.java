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
    @JoinColumn(name = "tourist_id",nullable = false)
    private User user;
    @Column(nullable = false,length = 400)
    private String comment;
    @Column(nullable = false)
    private String rating;
    @Column(nullable = false)
    private String date;

}
