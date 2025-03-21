package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class TourItemDetailPK implements Serializable {
    @Column(name = "tour_id")
    private String tourId;
    @Column(name = "booking_item_id")
    private String bookingItemId;
}
