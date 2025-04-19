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

    private final String merchantSecret = "MzI2NTk1NTM2MDExMDY3MzA5ODAyNDY4NDU1NzIxMTE5NTIwODAw"; // Replace with your actual merchant secret
    private final String merchantId = "1230021";

    public PaymentDTO calculateHash(double amount) {
        String orderID = Long.toString(System.currentTimeMillis());
        DecimalFormat df = new DecimalFormat("0.00");
        String amountFormatted = df.format(amount);
        String currency = "LKR";

        String hash = getMd5(merchantId + orderID + amountFormatted + currency + getMd5(merchantSecret));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setOrderId(orderID);
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
        payment.setOrderId(paymentDTO.getOrderId());
        payment.setFirstName(paymentDTO.getFirstName());
        payment.setLastName(paymentDTO.getLastName());
        payment.setEmail(paymentDTO.getEmail());
        payment.setPaymentTitle(paymentDTO.getPaymentTitle());
        payment.setAmount(Double.parseDouble(paymentDTO.getAmount()));
        payment.setStatus(paymentDTO.getStatus());
        payment.setSenderId(paymentDTO.getSenderId());
        payment.setReceiverId(paymentDTO.getReceiverId());

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public List<Payment> getPaymentsBySenderId(Long senderId) {
        return paymentRepository.findBySenderId(senderId);
    }

    public List<Payment> getPaymentsByReceiverId(Long receiverId) {
        return paymentRepository.findByReceiverId(receiverId);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    public Payment updatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

}
