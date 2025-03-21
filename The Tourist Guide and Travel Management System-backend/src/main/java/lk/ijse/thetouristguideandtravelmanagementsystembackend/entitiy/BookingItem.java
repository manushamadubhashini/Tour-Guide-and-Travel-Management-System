package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.BookingItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "booking_item")
public class BookingItem {
     @Id
     @Column(name = "booking_item_id")
     private String id;
//     @ManyToOne
//     @JoinColumn(name = "accommodation_id",nullable = false)
//     private Accommodation accommodation;
//     @ManyToOne
//     @JoinColumn(name = "tour_id",nullable = false)
//     private Tour tour;
//     @OneToOne
//     @JoinColumn(name = "trasport_id",nullable = false)
//     private Transport transport;
     @Enumerated
     @Column(name = "booking_item_type",nullable = false)
     private BookingItemType bookingItemType;
//     @Column(name = "quantity",nullable = false)
//     private int qty;
     @Column(nullable = false)
     private double price;
     @OneToMany(mappedBy = "bookingItem",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
     private List<BookingItemDetails> bookingItemDetailsList=new ArrayList<>();
     @OneToMany(mappedBy = "bookingItem",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
     private List<AccommodationItemDetails> accommodationItemDetailsList=new ArrayList<>();
     @OneToMany(mappedBy = "bookingItem",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
     private List<TourItemDetails> tourItemDetailsList=new ArrayList<>();
     @OneToMany(mappedBy = "bookingItem",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
     private List<TransportItemDetails> transportItemDetailsList=new ArrayList<>();

}
