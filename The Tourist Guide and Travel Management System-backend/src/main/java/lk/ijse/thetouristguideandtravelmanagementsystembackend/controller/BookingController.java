package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.BookingDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.BookingService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.awt.*;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil save(@RequestBody BookingDto bookingDto){
        bookingService.save(bookingDto);
        return new ResponseUtil(201,"Booking is Saved",bookingDto);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAll(){
        return new ResponseUtil(200,"Booking is Load",bookingService.getAll());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseUtil delete(@PathVariable(value = "id") String id){
        bookingService.delete(id);
        return new ResponseUtil(200,"Booking is Deleted",id);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping
    public ResponseUtil update(@RequestBody BookingDto bookingDto){
        return new ResponseUtil(200,"Booking is Updated",bookingDto);

    }
}
