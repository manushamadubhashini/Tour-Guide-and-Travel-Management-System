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
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor

public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepository;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private ModelMapper modelMapper;


    private final String merchantSecret = "MzI2NTk1NTM2MDExMDY3MzA5ODAyNDY4NDU1NzIxMTE5NTIwODAw"; // Replace with your actual merchant secret
    private final String merchantId = "1230021";

    // Change the method signature to accept the bookingId parameter
    public PaymentDTO calculateHash(double amount, String bookingId) {
        // Use the provided bookingId if it exists, otherwise generate a timestamp-based ID
        String orderID = (bookingId != null && !bookingId.isEmpty()) ?
                bookingId : Long.toString(System.currentTimeMillis());

        DecimalFormat df = new DecimalFormat("0.00");
        String amountFormatted = df.format(amount);
        String currency = "LKR";

        String hash = getMd5(merchantId + orderID + amountFormatted + currency + getMd5(merchantSecret));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setBookingId(orderID);
        paymentDTO.setHash(hash);
        paymentDTO.setAmount(amountFormatted);

        return paymentDTO;
    }

    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Payment savePayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        Booking booking=bookingRepo.findById(paymentDTO.getBookingId())
                        .orElseThrow(() -> new RuntimeException("Invvalid booking Id"));
        payment.setBooking(booking);
        payment.setPaymentTitle(paymentDTO.getPaymentTitle());
        payment.setAmount(Double.parseDouble(paymentDTO.getAmount()));
        payment.setStatus(paymentDTO.getStatus());

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByBookingId(String bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    public List<Payment> getPaymentsBySenderId(Long senderId) {
        return paymentRepository.findBySenderId(senderId);
    }

    public List<Payment> getPaymentsByReceiverId(Long receiverId) {
        return paymentRepository.findByReceiverId(receiverId);
    }

    public List<PaymentDTO> getAllPayments() {
        return modelMapper.map(paymentRepository.findAll(),new TypeToken<List<PaymentDTO>>(){}.getType());
    }
    public Payment updatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

}
