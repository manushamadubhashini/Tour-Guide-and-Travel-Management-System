package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import jakarta.transaction.Transactional;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.embedded.AccommodationItemDetailsPK;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.embedded.BookingItemDetailsPK;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.embedded.TourItemDetailPK;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.embedded.TransportItemDetailsPK;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.BookingItemType;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.BookingStatus;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private BookingItemRepo bookingItemRepo;
    @Autowired
    private BookingItemDetailRepo bookingItemDetailRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AccommodationRepo accommodationRepo;
    @Autowired
    private TransportRepo transportRepo;
    @Autowired
    private TourRepo tourRepo;
    @Autowired
    private TourItemDetailsRepo tourItemDetailsRepo;
    @Autowired
    private TransportDetailsRepo transportDetailsRepo;
    @Autowired
    AccommodationItemDetailsRepo accommodationItemDetailsRepo;
    @Autowired
    private ModelMapper modelMapper;
//
//    @Transactional
//    public void saveBooking(BookingDto bookingDto) {
////        if (!userRepo.findById(bookingDto.getTouristId()).isPresent()) {
////            throw new RuntimeException("Tourist Not Found!");
////
////        }
//        Optional<User> existingTourist=userRepo.findById(bookingDto.getBookingId());
//        User tourist=existingTourist.get();
//
////        if(!accommodationRepo.findByName(bookingDto.getAccommodationName()).isPresent()) {
////            throw new RuntimeException("Accommodation Not Found!");
////        }
////        Optional<Accommodation> existingAccommodation = accommodationRepo.findByName(bookingDto.getAccommodationName());
////        Accommodation accommodation=existingAccommodation.get();
////
////        if (!transportRepo.findByTransportType(bookingDto.getTransportType()).isPresent()){
////            throw new RuntimeException("Travel Not Found!");
////        }
////        Optional<Transport> existingTransport=transportRepo.findByTransportType(bookingDto.getTransportType());
////        Transport transport=existingTransport.get();
////
////        if (!tourRepo.findByName(bookingDto.getTourName()).isPresent()){
////            throw new RuntimeException("Tour Not Found!");
////        }
////        Optional<Tour> existingTour=tourRepo.findByName(bookingDto.getTourName());
////        Tour tour=existingTour.get();
//
//        for (BookingItemDto bookingItemDto:bookingDto.getBookingItemDtoList()) {
//            BookingItem bookingItem=new BookingItem();
//            bookingItem.setId(bookingItemDto.getId());
//            bookingItem.setBookingItemType(BookingItemType.valueOf(bookingItemDto.getType()));
//            bookingItem.setPrice(bookingItemDto.getPrice());
//
//            bookingItemRepo.save(bookingItem);
//        }
//
////        AccommodationItemDetails accommodationItemDetails=new AccommodationItemDetails();
//
//        Booking booking=new Booking();
//        booking.setId(bookingDto.getBookingId());
////        booking.setUser(tourist);
//        booking.setDate(bookingDto.getDate());
//        booking.setTotalAmount(bookingDto.getTotalAmount());
//        booking.setBookingStatus(BookingStatus.valueOf(bookingDto.getBookingStatus()));
//        bookingRepo.save(booking);
//
//       for(AccommodationItemDetailDto accommodationItemDetailDto:bookingDto.getAccommodationItemDetailDtoList()){
//            AccommodationItemDetails accommodationItemDetails=new AccommodationItemDetails();
//            accommodationItemDetails.setAccommodationItemDetailsPK(new AccommodationItemDetailsPK(accommodationItemDetailDto.getAccommodationId(),accommodationItemDetailDto.getBookingItemId()));
//            accommodationItemDetails.setQty(accommodationItemDetailDto.getQty());
//            accommodationItemDetails.setUnitPrice(accommodationItemDetailDto.getUnitPrice());
//            accommodationItemDetails.setPrice(accommodationItemDetailDto.getPrice());
//            accommodationItemDetailsRepo.save(accommodationItemDetails);
//       }
//       for (TourItemDetailsDto tourItemDetailsDto:bookingDto.getTourItemDetailsDtoList()){
//           TourItemDetails tourItemDetails=new TourItemDetails();
//           tourItemDetails.setTourItemDetailPK(new TourItemDetailPK(tourItemDetailsDto.getTourId(),tourItemDetailsDto.getBookingItemId()));
//           tourItemDetails.setQty(tourItemDetailsDto.getQty());
//           tourItemDetails.setUnitPrice(tourItemDetails.getUnitPrice());
//           tourItemDetails.setPrice(tourItemDetails.getPrice());
//           tourItemDetailsRepo.save(tourItemDetails);
//       }
//       for(TransportItemDetailsDto transportItemDetailsDto:bookingDto.getTransportItemDetailsDtoList()){
//           TransportItemDetails transportItemDetails=new TransportItemDetails();
//           transportItemDetails.setTransportItemDetailsPK(new TransportItemDetailsPK(transportItemDetailsDto.getTransportId(),transportItemDetailsDto.getBookingItemId()));
//           transportItemDetails.setUnitPrice(transportItemDetailsDto.getUnitPrice());
//           transportItemDetails.setQty(transportItemDetailsDto.getQty());
//           transportItemDetails.setPrice(transportItemDetailsDto.getPrice());
//           transportDetailsRepo.save(transportItemDetails);
//       }
//
////        bookingItemDetailRepo.save(new BookingItemDetails(new BookingItemDetailsPK(bookingDto.getBookingId(),bookingDto.getBookingItemId()),booking,bookingItem));
//
////        accommodationItemDetailsRepo.save(new AccommodationItemDetails(new AccommodationItemDetailsPK(accommodation.getId(),bookingDto.getBookingItemId()),bookingItem,accommodation));
////        transportDetailsRepo.save(new TransportItemDetails(new TransportItemDetailsPK(transport.getId(),bookingDto.getBookingItemId()),bookingItem,transport));
////        tourItemDetailsRepo.save(new TourItemDetails(new TourItemDetailPK(tour.getId(),bookingDto.getBookingItemId()),bookingItem,tour));
//
//
//    }
    @Transactional
    public void saveBooking(BookingDto bookingDto) {
        try {
            // Check if touristId exists - currently using bookingId by mistake
            Optional<User> existingTourist = userRepo.findById(bookingDto.getTouristId());
            if (!existingTourist.isPresent()) {
                throw new RuntimeException("Tourist with ID " + bookingDto.getTouristId() + " not found!");
            }
            User tourist = existingTourist.get();

            // Save Booking first
            Booking booking = new Booking();
            booking.setId(bookingDto.getBookingId());
            booking.setUser(tourist); // Set the user relationship that was commented out
            booking.setDate(bookingDto.getDate());
            booking.setTotalAmount(bookingDto.getTotalAmount());
            booking.setBookingStatus(BookingStatus.valueOf(bookingDto.getBookingStatus()));
            bookingRepo.save(booking);

            // Save booking items
            for (BookingItemDto bookingItemDto : bookingDto.getBookingItemDtoList()) {
                BookingItem bookingItem = new BookingItem();
                bookingItem.setId(bookingItemDto.getId());
                bookingItem.setBookingItemType(BookingItemType.valueOf(bookingItemDto.getType()));
                bookingItem.setPrice(bookingItemDto.getPrice());
                // Set the relationship to booking
//                bookingItem.setBooking(booking);
                bookingItemRepo.save(bookingItem);
            }

            // Save accommodation item details
            if (bookingDto.getAccommodationItemDetailDtoList() != null) {
                for (AccommodationItemDetailDto accommodationItemDetailDto : bookingDto.getAccommodationItemDetailDtoList()) {
                    // Verify accommodation exists
                    if (!accommodationRepo.existsById(accommodationItemDetailDto.getAccommodationId())) {
                        throw new RuntimeException("Accommodation with ID " + accommodationItemDetailDto.getAccommodationId() + " not found!");
                    }

                    // Verify booking item exists
                    if (!bookingItemRepo.existsById(accommodationItemDetailDto.getBookingItemId())) {
                        throw new RuntimeException("Booking item with ID " + accommodationItemDetailDto.getBookingItemId() + " not found!");
                    }

                    AccommodationItemDetails accommodationItemDetails = new AccommodationItemDetails();
                    accommodationItemDetails.setAccommodationItemDetailsPK(new AccommodationItemDetailsPK(
                            accommodationItemDetailDto.getAccommodationId(),
                            accommodationItemDetailDto.getBookingItemId()));
                    accommodationItemDetails.setQty(accommodationItemDetailDto.getQty());
                    accommodationItemDetails.setUnitPrice(accommodationItemDetailDto.getUnitPrice());
                    accommodationItemDetails.setPrice(accommodationItemDetailDto.getPrice());

                    // Set the relationships if your entity requires them
                    Optional<Accommodation> accommodation = accommodationRepo.findById(accommodationItemDetailDto.getAccommodationId());
                    Optional<BookingItem> bookingItem = bookingItemRepo.findById(accommodationItemDetailDto.getBookingItemId());

                    if (accommodation.isPresent() && bookingItem.isPresent()) {
                        accommodationItemDetails.setAccommodation(accommodation.get());
                        accommodationItemDetails.setBookingItem(bookingItem.get());
                    }

                    accommodationItemDetailsRepo.save(accommodationItemDetails);
                }
            }

            // Save tour item details
            if (bookingDto.getTourItemDetailsDtoList() != null) {
                for (TourItemDetailsDto tourItemDetailsDto : bookingDto.getTourItemDetailsDtoList()) {
                    // Verify tour exists
                    if (!tourRepo.existsById(tourItemDetailsDto.getTourId())) {
                        throw new RuntimeException("Tour with ID " + tourItemDetailsDto.getTourId() + " not found!");
                    }

                    // Verify booking item exists
                    if (!bookingItemRepo.existsById(tourItemDetailsDto.getBookingItemId())) {
                        throw new RuntimeException("Booking item with ID " + tourItemDetailsDto.getBookingItemId() + " not found!");
                    }

                    TourItemDetails tourItemDetails = new TourItemDetails();
                    tourItemDetails.setTourItemDetailPK(new TourItemDetailPK(
                            tourItemDetailsDto.getTourId(),
                            tourItemDetailsDto.getBookingItemId()));
                    tourItemDetails.setQty(tourItemDetailsDto.getQty());
                    // FIXED: Use DTO values instead of entity values
                    tourItemDetails.setUnitPrice(tourItemDetailsDto.getUnitPrice());
                    tourItemDetails.setPrice(tourItemDetailsDto.getPrice());

                    // Set the relationships if your entity requires them
                    Optional<Tour> tour = tourRepo.findById(tourItemDetailsDto.getTourId());
                    Optional<BookingItem> bookingItem = bookingItemRepo.findById(tourItemDetailsDto.getBookingItemId());

                    if (tour.isPresent() && bookingItem.isPresent()) {
                        tourItemDetails.setTour(tour.get());
                        tourItemDetails.setBookingItem(bookingItem.get());
                    }

                    tourItemDetailsRepo.save(tourItemDetails);
                }
            }

            // Save transport item details
            if (bookingDto.getTransportItemDetailsDtoList() != null) {
                for (TransportItemDetailsDto transportItemDetailsDto : bookingDto.getTransportItemDetailsDtoList()) {
                    // Verify transport exists
                    if (!transportRepo.existsById(transportItemDetailsDto.getTransportId())) {
                        throw new RuntimeException("Transport with ID " + transportItemDetailsDto.getTransportId() + " not found!");
                    }

                    // Verify booking item exists
                    if (!bookingItemRepo.existsById(transportItemDetailsDto.getBookingItemId())) {
                        throw new RuntimeException("Booking item with ID " + transportItemDetailsDto.getBookingItemId() + " not found!");
                    }

                    TransportItemDetails transportItemDetails = new TransportItemDetails();
                    transportItemDetails.setTransportItemDetailsPK(new TransportItemDetailsPK(
                            transportItemDetailsDto.getTransportId(),
                            transportItemDetailsDto.getBookingItemId()));
                    transportItemDetails.setUnitPrice(transportItemDetailsDto.getUnitPrice());
                    transportItemDetails.setQty(transportItemDetailsDto.getQty());
                    transportItemDetails.setPrice(transportItemDetailsDto.getPrice());

                    // Set the relationships if your entity requires them
                    Optional<Transport> transport = transportRepo.findById(transportItemDetailsDto.getTransportId());
                    Optional<BookingItem> bookingItem = bookingItemRepo.findById(transportItemDetailsDto.getBookingItemId());

                    if (transport.isPresent() && bookingItem.isPresent()) {
                        transportItemDetails.setTransport(transport.get());
                        transportItemDetails.setBookingItem(bookingItem.get());
                    }

                    transportDetailsRepo.save(transportItemDetails);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving booking: " + e.getMessage(), e);
        }
    }


}
