package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.TourDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.TourService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("api/v1/tour")
@CrossOrigin
public class TourController {
    @Autowired
    private TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil save(@ModelAttribute TourDto tourDto){
        tourService.save(tourDto);
        return new ResponseUtil(201,"Tour is Saved!",tourDto);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAll(){
        return new ResponseUtil(200,"Tour Load",tourService.getAll());
    }
    @DeleteMapping(path = "delete/{id}")
    public ResponseUtil delete(@PathVariable(value = "id")String id){
        tourService.delete(id);
        return new ResponseUtil(200,"Tour Deleted!",id);
    }
    @PutMapping
    public ResponseUtil update(@ModelAttribute TourDto tourDto){
        tourService.update(tourDto);
        return new ResponseUtil(200,"Tour Updated!",tourDto);
    }
//    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseUtil getTourById(@PathVariable String id) {
//        try {
//            return new ResponseUtil(200, "Tour Found", tourService.findById(id));
//        } catch (Exception e) {
//            return new ResponseUtil(404, "Tour Not Found", null);
//        }
//    }
@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseUtil getTourById(@PathVariable String id) {
    try {
        List<TourDto> tours = tourService.findById(id);
        if (tours != null && !tours.isEmpty()) {
            // Return the first tour from the list
            return new ResponseUtil(200, "Tour Found", tours.get(0));
        } else {
            return new ResponseUtil(404, "Tour Not Found", null);
        }
    } catch (Exception e) {
        return new ResponseUtil(404, "Tour Not Found", null);
    }
}
}
