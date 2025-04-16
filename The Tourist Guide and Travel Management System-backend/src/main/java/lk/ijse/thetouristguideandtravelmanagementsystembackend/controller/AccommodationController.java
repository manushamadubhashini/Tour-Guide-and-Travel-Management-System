package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.AccommodationDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.AccommodationService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/accommodation")
@CrossOrigin
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil save(@RequestBody AccommodationDto accommodationDto){
        accommodationService.save(accommodationDto);
        return new ResponseUtil(201,"Accommodation is Saved",accommodationDto);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAll(){
        return new ResponseUtil(200,"Accommodation is load",accommodationService.getAll());
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "delete/{id}")
    public ResponseUtil delete(@PathVariable(value = "id") String id){
        accommodationService.delete(id);
        return new ResponseUtil(200,"Accommodation is deleted",id);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    public ResponseUtil update(@RequestBody AccommodationDto accommodationDto){
        accommodationService.update(accommodationDto);
        return new ResponseUtil(200,"Accommodation is updated",accommodationDto);

    }
}
