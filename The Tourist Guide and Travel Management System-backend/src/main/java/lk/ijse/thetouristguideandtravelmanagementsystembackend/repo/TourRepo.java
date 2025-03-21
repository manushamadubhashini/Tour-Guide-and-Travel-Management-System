package lk.ijse.thetouristguideandtravelmanagementsystembackend.repo;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourRepo extends JpaRepository<Tour,String> {
    Optional<Tour> findByName(String name);
}
