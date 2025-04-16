package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.PaymentDTO;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Booking;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Payment;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.PaymentMethod;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.PaymentStatus;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.BookingRepo;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.PaymentRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepository;
    @Autowired
    private BookingRepo bookingRepo;

    @Value("${payhere.merchant_secret}")
    private String merchantSecret;

    @Value("${payhere.merchant_id}")
    private String merchantId;

    @Transactional
    public PaymentDTO initiatePayment(String bookingId, PaymentMethod method) {
        log.info("Initiating payment for booking: {}", bookingId);

        // Find booking
        Optional<Booking> bookingOptional = bookingRepo.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            log.error("Booking not found with ID: {}", bookingId);
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }

        Booking booking = bookingOptional.get();

        // Generate payment ID (you can use UUID or your own format)
        String paymentId = "PAY_" + System.currentTimeMillis();
        log.info("Generated payment ID: {}", paymentId);

        // Create payment entity
        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setBooking(booking);
        payment.setPaymentMethod(method);
        payment.setAmount(calculateAmount(booking));
        payment.setStatus(PaymentStatus.PENDING);

        // Save payment
        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment saved successfully: {}", savedPayment.getId());

        // Create and return DTO with payment info
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(paymentId);
        paymentDTO.setBookingId(bookingId);
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setPaymentMethod(String.valueOf(method));
        paymentDTO.setStatus(String.valueOf(payment.getStatus()));

        return paymentDTO;
    }

    public String generateHash(String orderId, double amount, String currency) {
        try {
            // Format amount to exactly 2 decimal places
            String amountFormatted = String.format(Locale.US, "%.2f", amount);

            // Use uppercase merchant secret as per PayHere requirements
            String upperMerchantSecret = merchantSecret.toUpperCase();

            // Create the input string in the exact format PayHere expects
            String input = merchantId + orderId + amountFormatted + currency + upperMerchantSecret;

            log.debug("Hash input string (without secret revealed): {}",
                    merchantId + orderId + amountFormatted + currency + "[SECRET]");

            // Generate MD5 hash
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert to hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("Error generating MD5 hash", e);
            throw new RuntimeException("Error generating MD5 hash", e);
        }
    }

    private double calculateAmount(Booking booking) {
        // Implement your business logic to calculate the payment amount
        // based on the booking details
        return booking.getTotalAmount(); // Assuming Booking has this field
    }

    @Transactional
    public void processPayHereNotification(Map<String, String> notificationData) {
        log.info("Processing PayHere notification: {}", notificationData);

        String merchantId = notificationData.get("merchant_id");
        String orderId = notificationData.get("order_id");
        String paymentId = notificationData.get("payment_id");
        String payhereAmount = notificationData.get("payhere_amount");
        String payhereCurrency = notificationData.get("payhere_currency");
        String statusCode = notificationData.get("status_code");
        String md5sig = notificationData.get("md5sig");

        log.info("PayHere notification - Order ID: {}, Status Code: {}", orderId, statusCode);

        // Verify the MD5 hash (important security step)
        String localMD5Sig = generateMD5Signature(merchantId, orderId, payhereAmount,
                payhereCurrency, statusCode);

        if (!localMD5Sig.equalsIgnoreCase(md5sig)) {
            log.error("MD5 signature verification failed. Expected: {}, Received: {}", localMD5Sig, md5sig);
            throw new RuntimeException("MD5 signature verification failed");
        }

        log.info("MD5 signature verification successful");

        // Retrieve the payment from database
        Optional<Payment> paymentOptional = paymentRepository.findById(orderId);
        if (paymentOptional.isEmpty()) {
            log.error("Payment not found with ID: {}", orderId);
            throw new RuntimeException("Payment not found with ID: " + orderId);
        }

        Payment payment = paymentOptional.get();
        payment.setPayherePaymentId(paymentId);

        // Update payment status based on the notification
        updatePaymentStatus(payment, statusCode);

        // Save the updated payment
        paymentRepository.save(payment);
        log.info("Payment status updated for ID: {}", orderId);
    }

    private void updatePaymentStatus(Payment payment, String statusCode) {
        switch (statusCode) {
            case "2":
                // Payment success
                log.info("Payment completed successfully for ID: {}", payment.getId());
                payment.setStatus(PaymentStatus.COMPLETED);
                // Update booking status or any other business logic
                Booking booking = payment.getBooking();
                // Update booking status as needed
                break;
            case "0":
                // Payment pending
                log.info("Payment pending for ID: {}", payment.getId());
                payment.setStatus(PaymentStatus.PENDING);
                break;
            default:
                // Payment failed or other status
                log.info("Payment failed or other status ({}) for ID: {}", statusCode, payment.getId());
                payment.setStatus(PaymentStatus.FAILED);
                break;
        }
    }

    private String generateMD5Signature(String merchantId, String orderId, String amount,
                                        String currency, String statusCode) {
        try {
            String input = merchantId + orderId + amount + currency + statusCode + merchantSecret.toUpperCase();
            log.debug("Generating MD5 signature with input: {}", input);

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());

            // Convert to hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            String signature = sb.toString().toUpperCase();
            log.debug("Generated MD5 signature: {}", signature);

            return signature;
        } catch (NoSuchAlgorithmException e) {
            log.error("Error generating MD5 hash", e);
            throw new RuntimeException("Error generating MD5 hash", e);
        }
    }

    public PaymentDTO getPaymentDetails(String paymentId) {
        log.info("Getting payment details for ID: {}", paymentId);

        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isEmpty()) {
            log.error("Payment not found with ID: {}", paymentId);
            throw new RuntimeException("Payment not found with ID: " + paymentId);
        }

        Payment payment = paymentOptional.get();

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(payment.getId());
        paymentDTO.setBookingId(payment.getBooking().getId());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setPaymentMethod(String.valueOf(payment.getPaymentMethod()));
        paymentDTO.setStatus(String.valueOf(payment.getStatus()));
        paymentDTO.setPayherePaymentId(payment.getPayherePaymentId());

        return paymentDTO;
    }

}
