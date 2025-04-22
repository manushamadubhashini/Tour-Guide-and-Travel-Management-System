package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.PaymentDTO;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Payment;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.BookingService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.PaymentService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@CrossOrigin // More specific for production
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingService bookingService;

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping()
    public PaymentDTO calculateHash(
            @RequestParam("amount") double amount,
            @RequestParam(value = "bookingId", required = false) String bookingId) {
        return paymentService.calculateHash(amount, bookingId);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping()
    public ResponseEntity<Payment> savePayment(@RequestBody PaymentDTO paymentDTO) {
        Payment savedPayment = paymentService.savePayment(paymentDTO);
        return ResponseEntity.ok(savedPayment);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Payment> getPaymentByBookingID(@PathVariable String bookingId) {
        Payment payment = paymentService.getPaymentByBookingId(bookingId);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<Payment>> getPaymentsBySenderId(@PathVariable Long senderId) {
        List<Payment> payments = paymentService.getPaymentsBySenderId(senderId);
        return ResponseEntity.ok(payments);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<Payment>> getPaymentsByReceiverId(@PathVariable Long receiverId) {
        List<Payment> payments = paymentService.getPaymentsByReceiverId(receiverId);
        return ResponseEntity.ok(payments);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllPayments() {
        return new ResponseUtil(201,"Payment Load",paymentService.getAllPayments()) ;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/notify")
    public ResponseEntity<String> handlePaymentNotification(@RequestBody Map<String, String> notificationData) {
        log.info("Payment notification received: {}", notificationData);
        String orderId = notificationData.get("order_id");
        String status = notificationData.get("status_code");

        if (orderId != null) {
            Payment payment = paymentService.getPaymentByBookingId(orderId);
            if (payment != null) {
                payment.setStatus(status);
                paymentService.updatePayment(payment);
                return ResponseEntity.ok("Payment notification received");
            }
        }

        return ResponseEntity.badRequest().body("Invalid payment notification");
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/update-status")
    public ResponseEntity<?> updateBookingStatus(@RequestBody Map<String, String> request) {
        try {
            String bookingId = request.get("bookingId");
            String status = request.get("status");
            bookingService.updateBookingStatus(bookingId, status);
            return ResponseEntity.ok().body(Map.of("message", "Booking status updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}