package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.embedded.GuideTourPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class GuideTour {
    @EmbeddedId
    private GuideTourPK guideTourPK;
    @Column(nullable = false,length = 80)
    private String status;
    @ManyToOne
    @JoinColumn(name = "tour_id",insertable = false,updatable = false)
    private Tour tour;
    @ManyToOne
    @JoinColumn(name = "guider_id",insertable = false,updatable = false)
    private User user;
}
