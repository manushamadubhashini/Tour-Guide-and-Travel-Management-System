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
public class BookingItemDetailsPK implements Serializable {
    @Column(name = "booking_id")
    private String bookingId;
    @Column(name = "booking_item_id")
    private String bookingItemId;
}
