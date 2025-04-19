package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.AccommodationDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.TransportDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.TransportService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("api/v1/transport")
@CrossOrigin
public class TransportController {
    @Autowired
    private TransportService transportService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil save(@RequestBody TransportDto transportDto){
        transportService.save(transportDto);
        return new ResponseUtil(201,"Accommodation is Saved",transportDto);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAll(){
        return new ResponseUtil(200,"Accommodation is load",transportService.getAll());
    }
    @DeleteMapping(path = "delete/{id}")
    public ResponseUtil delete(@PathVariable(value = "id") String id){
        transportService.delete(id);
        return new ResponseUtil(200,"Accommodation is deleted",id);
    }
    @PutMapping
    public ResponseUtil update(@RequestBody TransportDto transportDto){
        transportService.update(transportDto);
        return new ResponseUtil(200,"Accommodation is updated",transportDto);

    }
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil findByTourId(@PathVariable(value = "id") String id){
        return new ResponseUtil(200,"Transport is load",transportService.findByTourId(id));
    }
}

