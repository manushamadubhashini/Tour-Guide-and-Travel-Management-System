package lk.ijse.thetouristguideandtravelmanagementsystembackend.repo;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepo extends JpaRepository<Accommodation,String> {
    Optional<Accommodation> findByName(String name);
    List<Accommodation> findByTourId(String tourId);
}
