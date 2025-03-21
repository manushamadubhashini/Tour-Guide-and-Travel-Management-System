package lk.ijse.thetouristguideandtravelmanagementsystembackend.repo;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingItemRepo extends JpaRepository<BookingItem,String> {
}
