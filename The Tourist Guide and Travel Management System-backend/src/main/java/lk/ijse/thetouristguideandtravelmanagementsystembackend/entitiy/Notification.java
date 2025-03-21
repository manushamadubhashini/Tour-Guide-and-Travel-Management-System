package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @Column(name = "notification_id")
    private String id;
    @ManyToOne
    @JoinColumn(name="tourist_id",nullable = false)
    private User user;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private String date;
}
