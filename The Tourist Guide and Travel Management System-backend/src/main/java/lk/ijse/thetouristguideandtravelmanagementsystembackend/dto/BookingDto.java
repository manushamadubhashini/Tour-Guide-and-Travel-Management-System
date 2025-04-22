package lk.ijse.thetouristguideandtravelmanagementsystembackend.dto;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDto {
    private String bookingId;
    private String touristId;
    private String tourId;
    private String accommodationId;
    private String transportId;
    private String date;
    private double totalAmount;
    private int noOfTravellers;
    private String status;
}

