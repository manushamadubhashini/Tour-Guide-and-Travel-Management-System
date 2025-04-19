package lk.ijse.thetouristguideandtravelmanagementsystembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private String orderId;
    private String hash;
    private String amount;
    private String firstName;
    private String lastName;
    private String email;
    private String paymentTitle;
    private String status;
    private Long senderId;
    private Long receiverId;
}
