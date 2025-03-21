package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.AccommodationDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.AccommodationService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/accommodation")
@CrossOrigin
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil save(@ModelAttribute AccommodationDto accommodationDto){
        accommodationService.save(accommodationDto);
        return new ResponseUtil(201,"Accommodation is Saved",accommodationDto);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAll(){
        return new ResponseUtil(200,"Accommodation is load",accommodationService.getAll());
    }
    @DeleteMapping(path = "delete/{id}")
    public ResponseUtil delete(@PathVariable(value = "id") String id){
        accommodationService.delete(id);
        return new ResponseUtil(200,"Accommodation is deleted",id);
    }
    @PutMapping
    public ResponseUtil update(@ModelAttribute AccommodationDto accommodationDto){
        accommodationService.update(accommodationDto);
        return new ResponseUtil(200,"Accommodation is updated",accommodationDto);

    }
}
