package lk.ijse.thetouristguideandtravelmanagementsystembackend.repo;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Accommodation;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransportRepo extends JpaRepository<Transport,String> {
    Optional<Transport> findByTransportType(String transportType);
    List<Transport> findByTourId(String id);
}
