package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.embedded.TransportItemDetailsPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "transport_item_deatils")
public class TransportItemDetails {
    @EmbeddedId
    private TransportItemDetailsPK transportItemDetailsPK;
    private double unitPrice;
    @Column(name = "quantity")
    private int qty;
    @Column(name = "price")
    private double price;
    @ManyToOne
    @JoinColumn(name ="booking_item_id",updatable = false,insertable = false)
    private BookingItem bookingItem;
    @ManyToOne
    @JoinColumn(name = "transport_id",updatable = false,insertable = false)
    private Transport transport;
}
