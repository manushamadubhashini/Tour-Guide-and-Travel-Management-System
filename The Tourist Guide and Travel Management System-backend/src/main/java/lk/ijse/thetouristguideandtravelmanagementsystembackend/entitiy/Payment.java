package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.PaymentMethod;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    @Enumerated(EnumType.STRING) // Added EnumType.STRING to store enum as string
    @Column(name = "method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private String payherePaymentId;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
