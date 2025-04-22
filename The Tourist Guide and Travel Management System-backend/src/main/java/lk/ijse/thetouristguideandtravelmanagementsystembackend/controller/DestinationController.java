package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.DestinationDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.DestinationService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/destination")
@CrossOrigin
public class DestinationController {
    @Autowired
    private DestinationService destinationService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil save(@ModelAttribute DestinationDto destinationDto){
        destinationService.save(destinationDto);
        return new ResponseUtil(201,"Destination is saved!",destinationDto);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil gatAll(){
        return new ResponseUtil(201,"Destination is load!",destinationService.getAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "delete/{id}")
    public ResponseUtil delete(@PathVariable(value = "id") String id){
        destinationService.delete(id);
        return new ResponseUtil(200,"Destination Deleted",id);

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    public ResponseUtil update(@ModelAttribute DestinationDto destinationDto){
        destinationService.update(destinationDto);
        return new ResponseUtil(200,"Destination Updated",destinationDto);

    }
}
