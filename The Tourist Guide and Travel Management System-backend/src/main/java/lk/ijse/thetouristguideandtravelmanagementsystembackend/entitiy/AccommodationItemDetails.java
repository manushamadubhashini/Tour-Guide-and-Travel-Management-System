package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.embedded.AccommodationItemDetailsPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name ="accommodation_item_details")
public class AccommodationItemDetails {
    @EmbeddedId
    private AccommodationItemDetailsPK accommodationItemDetailsPK;
    @Column(name = "unit_price")
    private double unitPrice;
    @Column(name = "quantity")
    private int qty;
    @Column(name = "price")
    private double price;
    @ManyToOne
    @JoinColumn(name ="booking_item_id",updatable = false,insertable = false)
    private BookingItem bookingItem;
    @ManyToOne
    @JoinColumn(name = "accommodation_id",updatable = false,insertable = false)
    private Accommodation accommodation;



}
