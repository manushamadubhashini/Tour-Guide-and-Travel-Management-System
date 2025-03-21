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
    @OneToOne(mappedBy = "booking",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Payment payment;
    @Column(name = "booking_date",nullable = false,length = 60)
    private String date;
    @Column(name = "toal_amount",nullable = false)
    private double totalAmount;
    @Enumerated
    @Column(name = "status", nullable = false)
    private BookingStatus bookingStatus;
    @OneToMany(mappedBy = "booking",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<BookingItemDetails> bookingItemDetailsList=new ArrayList<>();
}
