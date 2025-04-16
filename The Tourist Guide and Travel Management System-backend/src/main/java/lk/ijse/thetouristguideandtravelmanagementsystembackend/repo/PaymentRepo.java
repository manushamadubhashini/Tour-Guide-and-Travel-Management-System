package lk.ijse.thetouristguideandtravelmanagementsystembackend.repo;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,String> {
}
