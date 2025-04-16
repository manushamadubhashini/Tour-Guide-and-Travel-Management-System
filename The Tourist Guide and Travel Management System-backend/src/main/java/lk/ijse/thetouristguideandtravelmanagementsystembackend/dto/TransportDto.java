package lk.ijse.thetouristguideandtravelmanagementsystembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransportDto {
    private String id;
    private String type;
    private double pricePerKM;
    private int passengers;
}
