package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tour_schedule")
public class TourSchedule {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "tour_id",nullable = false)
    private Tour tour;
    @Column(nullable = false)
    private String start_time;
    @Column(nullable = false)
    private String end_time;
}
