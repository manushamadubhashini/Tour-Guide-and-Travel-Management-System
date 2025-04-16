package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.PaymentDTO;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.PaymentMethod;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@CrossOrigin// Enable CORS for testing
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @Value("${payhere.merchant_id}")
    private String merchantId;

    @Value("${payhere.return_url}")
    private String returnUrl;

    @Value("${payhere.cancel_url}")
    private String cancelUrl;

    @Value("${payhere.notify_url}")
    private String notifyUrl;

    @Value("${payhere.sandbox_mode:true}")
    private boolean sandboxMode;

    @PostMapping("/initiate")
    public ResponseEntity<Map<String, Object>> initiatePayment(@RequestBody Map<String, Object> requestBody) {
        try {
            log.info("Payment initiation request received: {}", requestBody);

            // Extract required fields
            String bookingId = (String) requestBody.get("bookingId");
            if (bookingId == null || bookingId.isEmpty()) {
                throw new IllegalArgumentException("Booking ID is required");
            }

            // Extract payment method or default to CREDIT_CARD
            PaymentMethod paymentMethod;
            try {
                String methodStr = (String) requestBody.get("paymentMethod");
                paymentMethod = methodStr != null ? PaymentMethod.valueOf(methodStr) : PaymentMethod.CREDIT_CARD;
            } catch (IllegalArgumentException e) {
                log.warn("Invalid payment method provided, defaulting to CREDIT_CARD");
                paymentMethod = PaymentMethod.CREDIT_CARD;
            }

            // Initiate payment in the service
            PaymentDTO paymentDTO = paymentService.initiatePayment(bookingId, paymentMethod);
            log.info("Payment initiated successfully: {}", paymentDTO.getPaymentId());

            // Prepare PayHere payment data
            Map<String, Object> response = new HashMap<>();
            response.put("payment", paymentDTO);

            // Add PayHere specific data
            Map<String, Object> payhereData = new HashMap<>();
            payhereData.put("merchant_id", merchantId);
            payhereData.put("return_url", returnUrl);
            payhereData.put("cancel_url", cancelUrl);
            payhereData.put("notify_url", notifyUrl);
            payhereData.put("order_id", paymentDTO.getPaymentId());
            payhereData.put("amount", paymentDTO.getAmount());
            payhereData.put("currency", "USD");
            payhereData.put("items", "Booking #" + bookingId);

            // Add customer information
            payhereData.put("first_name", requestBody.getOrDefault("firstName", ""));
            payhereData.put("last_name", requestBody.getOrDefault("lastName", ""));
            payhereData.put("email", requestBody.getOrDefault("email", ""));
            payhereData.put("phone", requestBody.getOrDefault("phone", ""));
            payhereData.put("address", requestBody.getOrDefault("address", ""));
            payhereData.put("city", requestBody.getOrDefault("city", ""));
            payhereData.put("country", requestBody.getOrDefault("country", "Sri Lanka"));

            // Generate hash for direct API integration
            String hash = paymentService.generateHash(
                    paymentDTO.getPaymentId(),
                    paymentDTO.getAmount(),
                    "USD"
            );
            payhereData.put("hash", hash);
            payhereData.put("sandbox", sandboxMode);

            response.put("payhereData", payhereData);

            // Add gateway URLs for frontend
            response.put("gatewayUrl", sandboxMode ?
                    "https://sandbox.payhere.lk/pay/checkout" :
                    "https://www.payhere.lk/pay/checkout");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error initiating payment", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/notify")
    public ResponseEntity<String> handlePayHereNotification(@RequestParam Map<String, String> notificationData) {
        try {
            log.info("PayHere notification received: {}", notificationData);
            paymentService.processPayHereNotification(notificationData);
            return ResponseEntity.ok("Notification processed successfully");
        } catch (Exception e) {
            log.error("Error processing PayHere notification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing notification: " + e.getMessage());
        }
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentDetails(@PathVariable String paymentId) {
        try {
            log.info("Getting payment details for ID: {}", paymentId);
            PaymentDTO paymentDTO = paymentService.getPaymentDetails(paymentId);
            return ResponseEntity.ok(paymentDTO);
        } catch (Exception e) {
            log.error("Error getting payment details for ID: {}", paymentId, e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}