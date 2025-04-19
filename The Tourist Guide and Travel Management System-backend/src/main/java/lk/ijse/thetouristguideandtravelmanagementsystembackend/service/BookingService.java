package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import jakarta.transaction.Transactional;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.BookingStatus;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
   @Autowired
    private ModelMapper modelMapper;
   @Autowired
    private BookingRepo bookingRepo;
   @Autowired
   private AccommodationRepo accommodationRepo;
   @Autowired
   private TransportRepo transportRepo;
   @Autowired
   private UserRepo userRepo;
   @Autowired
   private TourRepo tourRepo;

    @Transactional
    public void save(BookingDto bookingDto) {
        try {
            Booking booking = new Booking();

            if (bookingDto.getBookingId() == null) {
                booking.setId(UUID.randomUUID().toString());
            } else {
                booking.setId(bookingDto.getBookingId());
            }

            User user = userRepo.findById(bookingDto.getTouristId())
                    .orElseThrow(() -> new RuntimeException("Invalid User ID"));
            booking.setUser(user);

            Accommodation accommodation = accommodationRepo.findById(bookingDto.getAccommodationId())
                    .orElseThrow(() -> new RuntimeException("Invalid Accommodation ID"));
            booking.setAccommodation(accommodation);

            Tour tour = tourRepo.findById(bookingDto.getTourId())
                    .orElseThrow(() -> new RuntimeException("Invalid Tour ID"));
            booking.setTour(tour);

            Transport transport = transportRepo.findById(bookingDto.getTransportId())
                    .orElseThrow(() -> new RuntimeException("Invalid Transport ID"));
            booking.setTransport(transport);

            booking.setDate(bookingDto.getDate());
            booking.setTotalAmount(bookingDto.getTotalAmount());
            booking.setNoOfTravellers(bookingDto.getNoOfTravellers());

            bookingRepo.save(booking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Booking Not Saved!");
        }
    }
   public List<BookingDto> getAll(){
       try {
           return modelMapper.map(bookingRepo.findAll(),new TypeToken<List<BookingDto>>(){}.getType());
       }catch (RuntimeException e){
           e.printStackTrace();
           throw new RuntimeException("Not Load!");
       }
   }
   public void delete(String id){
       try {
           if (bookingRepo.existsById(id)){
               bookingRepo.deleteById(id);
               System.out.println("Deleted Successfully!");
           }
       }catch (RuntimeException e) {
           throw new RuntimeException("Not Deleted!");
       }
   }



}
