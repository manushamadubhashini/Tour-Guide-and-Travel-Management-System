package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "booking_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "tourist_id",nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "accommodation_id",nullable = false)
    private Accommodation accommodation;
    @ManyToOne
    @JoinColumn(name = "tour_id",nullable = false)
    private Tour tour;
    @ManyToOne
    @JoinColumn(name = "trasport_id",nullable = false)
    private Transport transport;
    @OneToOne(mappedBy = "booking",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Payment payment;
    @Column(name = "booking_date",nullable = false,length = 60)
    private String date;
    @Column(name = "toal_amount",nullable = false)
    private double totalAmount;
    @Column(name = "no_of_travellers",nullable = false)
    private int noOfTravellers;
    @Enumerated
    @Column(name = "status", nullable = false)
    private BookingStatus bookingStatus;

}
