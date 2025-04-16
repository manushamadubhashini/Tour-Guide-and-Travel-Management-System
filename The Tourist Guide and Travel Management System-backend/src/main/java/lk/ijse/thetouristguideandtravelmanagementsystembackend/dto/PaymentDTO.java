package lk.ijse.thetouristguideandtravelmanagementsystembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private String paymentId;
    private String bookingId;
    private double amount;
    private String paymentMethod;
    private String status;
    private String payherePaymentId;
    private String createdAt;
    private String updatedAt;
}
