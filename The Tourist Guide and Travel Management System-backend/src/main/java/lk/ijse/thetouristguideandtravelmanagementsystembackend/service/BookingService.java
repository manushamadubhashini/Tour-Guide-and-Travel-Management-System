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

   public void save(BookingDto bookingDto){
       try {
           Booking booking=modelMapper.map(bookingDto, Booking.class);
           if (booking.getId() == null) {
               booking.setId(UUID.randomUUID().toString());
           }
           booking.setBookingStatus(BookingStatus.valueOf(bookingDto.getBookingStatus()));
           bookingRepo.save(booking);
       }catch (IllegalArgumentException e){
           throw new RuntimeException("Invalid Booking");
       }catch (RuntimeException e){
           throw new RuntimeException("Booking Not Saved!");
       }
   }
   public BookingDto getAll(){
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
