package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class GuideTourPK implements Serializable {
    @Column(name = "tour_id")
    private String tourId;
    @Column(name = "user_id")
    private String userId;
}
