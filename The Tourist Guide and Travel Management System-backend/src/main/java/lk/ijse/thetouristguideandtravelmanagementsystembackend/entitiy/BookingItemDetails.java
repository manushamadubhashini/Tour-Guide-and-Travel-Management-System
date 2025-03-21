package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.embedded.BookingItemDetailsPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "booking_item_detail")
public class BookingItemDetails {
    @EmbeddedId
    private BookingItemDetailsPK bookingItemDetailsPK;
    @ManyToOne
    @JoinColumn(name = "booking_id",insertable = false,updatable = false)
    private Booking booking;
    @ManyToOne
    @JoinColumn(name = "booking_item_id",insertable = false,updatable = false)
    private BookingItem bookingItem;
}
