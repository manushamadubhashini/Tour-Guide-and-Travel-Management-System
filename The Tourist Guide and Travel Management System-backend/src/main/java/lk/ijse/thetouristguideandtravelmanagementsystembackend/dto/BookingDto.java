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
    private List<BookingItemDto> bookingItemDtoList;
    private String touristId;
    private String date;
    private double totalAmount;
    private String bookingStatus;
    private List<AccommodationItemDetailDto> accommodationItemDetailDtoList;
    private List<TourItemDetailsDto> tourItemDetailsDtoList;
    private List<TransportItemDetailsDto> transportItemDetailsDtoList;
}

