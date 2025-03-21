package lk.ijse.thetouristguideandtravelmanagementsystembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccommodationItemDetailDto {
    private String bookingItemId;
    private String accommodationId;
    private double unitPrice;
    private int qty;
    private double price;
}
