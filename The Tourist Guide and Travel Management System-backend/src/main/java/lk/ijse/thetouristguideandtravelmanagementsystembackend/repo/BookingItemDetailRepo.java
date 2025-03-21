package lk.ijse.thetouristguideandtravelmanagementsystembackend.repo;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.BookingItemDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingItemDetailRepo extends JpaRepository<BookingItemDetails,String> {
}
