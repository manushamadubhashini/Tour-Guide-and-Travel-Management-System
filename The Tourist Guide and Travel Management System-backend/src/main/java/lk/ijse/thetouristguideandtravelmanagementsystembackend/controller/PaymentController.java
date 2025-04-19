package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.PaymentDTO;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Payment;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.PaymentMethod;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@CrossOrigin // More specific for production
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PutMapping()
    public PaymentDTO calculateHash(@RequestParam("amount") double amount) {
        return paymentService.calculateHash(amount);
    }

    @PostMapping()
    public ResponseEntity<Payment> savePayment(@RequestBody PaymentDTO paymentDTO) {
        Payment savedPayment = paymentService.savePayment(paymentDTO);
        return ResponseEntity.ok(savedPayment);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable String orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{senderId}")
    public ResponseEntity<List<Payment>> getPaymentsBySenderId(@PathVariable Long senderId) {
        List<Payment> payments = paymentService.getPaymentsBySenderId(senderId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<Payment>> getPaymentsByReceiverId(@PathVariable Long receiverId) {
        List<Payment> payments = paymentService.getPaymentsByReceiverId(receiverId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }
    @PostMapping("/notify")
    public ResponseEntity<String> handlePaymentNotification(@RequestBody Map<String, String> notificationData) {
        log.info("Payment notification received: {}", notificationData);

        // Verify the payment with PayHere (validate merchant ID, hash, etc.)
        // Update payment status in your database

        // Example: Update the payment status if order ID exists
        String orderId = notificationData.get("order_id");
        String status = notificationData.get("status_code");

        if (orderId != null) {
            Payment payment = paymentService.getPaymentByOrderId(orderId);
            if (payment != null) {
                // Update payment status based on PayHere status code
                payment.setStatus(status);
                paymentService.updatePayment(payment);
                return ResponseEntity.ok("Payment notification received");
            }
        }

        return ResponseEntity.badRequest().body("Invalid payment notification");
    }
}