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
            // First validate all IDs exist before creating the booking object
            User user = userRepo.findById(bookingDto.getTouristId())
                    .orElseThrow(() -> new RuntimeException("Invalid User ID: " + bookingDto.getTouristId()));

            Accommodation accommodation = accommodationRepo.findById(bookingDto.getAccommodationId())
                    .orElseThrow(() -> new RuntimeException("Invalid Accommodation ID: " + bookingDto.getAccommodationId()));

            Tour tour = tourRepo.findById(bookingDto.getTourId())
                    .orElseThrow(() -> new RuntimeException("Invalid Tour ID: " + bookingDto.getTourId()));

            Transport transport = transportRepo.findById(bookingDto.getTransportId())
                    .orElseThrow(() -> new RuntimeException("Invalid Transport ID: " + bookingDto.getTransportId()));

            // Now create and populate the booking object
            Booking booking = new Booking();

            if (bookingDto.getBookingId() == null) {
                booking.setId(UUID.randomUUID().toString());
            } else {
                booking.setId(bookingDto.getBookingId());
            }

            booking.setUser(user);
            booking.setAccommodation(accommodation);
            booking.setTour(tour);
            booking.setTransport(transport);
            booking.setDate(bookingDto.getDate());
            booking.setTotalAmount(bookingDto.getTotalAmount());
            booking.setNoOfTravellers(bookingDto.getNoOfTravellers());
            booking.setBookingStatus(BookingStatus.valueOf(bookingDto.getStatus()));

            bookingRepo.save(booking);
        } catch (Exception e) {
            // Add more context to the error
            throw new RuntimeException("Failed to save booking: " + e.getMessage(), e);
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
    @Transactional
    public void updateBookingStatus(String bookingId, String status) {
        if (bookingId == null || status == null) {
            throw new RuntimeException("Booking ID and status cannot be null");
        }

        try {
            // Check if the booking ID exists first
            if (!bookingRepo.existsById(bookingId)) {
                throw new RuntimeException("Booking not found with ID: " + bookingId);
            }

            // Get the booking and update status
            Optional<Booking> bookingOptional = bookingRepo.findById(bookingId);
            Booking booking = bookingOptional.get(); // We already checked that it exists

            try {
                BookingStatus bookingStatus = BookingStatus.valueOf(status);
                booking.setBookingStatus(bookingStatus);
                bookingRepo.save(booking);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid booking status value: " + status);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update booking status: " + e.getMessage(), e);
        }
    }



}
