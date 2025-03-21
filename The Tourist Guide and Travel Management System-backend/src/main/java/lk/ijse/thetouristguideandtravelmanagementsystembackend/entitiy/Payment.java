package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "payment_id")
    private String id;
    @OneToOne
    @JoinColumn(name = "booking_id",nullable = false)
    private Booking booking;
    @Enumerated
    @Column(name = "method",nullable = false)
    private PaymentMethod paymentMethod;
    @Column(nullable = false)
    private double amount;

}
