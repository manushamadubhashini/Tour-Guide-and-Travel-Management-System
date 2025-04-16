package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.BookingDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.BookingService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.awt.*;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil save(@RequestBody BookingDto bookingDto){
        bookingService.save(bookingDto);
        return new ResponseUtil(201,"Booking is Saved",bookingDto);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAll(){
        return new ResponseUtil(200,"Booking is Load",bookingService.getAll());
    }
    @DeleteMapping("delete/{id}")
    public ResponseUtil delete(@PathVariable(value = "id") String id){
        return new ResponseUtil(200,"Booking is Deleted",id);
    }
    @PutMapping
    public ResponseUtil update(@RequestBody BookingDto bookingDto){
        return new ResponseUtil(200,"Booking is Updated",bookingDto);

    }
}
