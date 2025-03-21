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
public class TransportItemDetailsPK implements Serializable {
    @Column(name = "transport_id")
    private String transportId;
    @Column(name = "booking_item_id")
    private String bookingItemId;
}
