package lk.ijse.thetouristguideandtravelmanagementsystembackend.repo;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.TransportItemDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportDetailsRepo extends JpaRepository<TransportItemDetails,String> {
}
