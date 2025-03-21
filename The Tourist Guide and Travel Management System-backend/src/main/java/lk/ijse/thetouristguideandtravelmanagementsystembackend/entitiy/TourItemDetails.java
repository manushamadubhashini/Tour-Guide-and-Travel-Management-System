package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.embedded.TourItemDetailPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tour_item_details")
public class TourItemDetails {
    @EmbeddedId
    private TourItemDetailPK tourItemDetailPK;
    private double unitPrice;
    @Column(name = "quantity")
    private int qty;
    @Column(name = "price")
    private double price;
    @ManyToOne
    @JoinColumn(name = "booking_item_id",updatable = false,insertable = false)
    private BookingItem bookingItem;
    @ManyToOne
    @JoinColumn(name = "tour_id",updatable = false,insertable = false)
    private Tour tour;
}
