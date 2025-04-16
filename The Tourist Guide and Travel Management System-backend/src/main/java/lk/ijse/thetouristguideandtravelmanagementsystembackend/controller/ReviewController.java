package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.ReviewDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.ReviewRepo;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.ReviewService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review")
@CrossOrigin
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil save(@RequestBody ReviewDto reviewDto){
        reviewService.save(reviewDto);
        return new ResponseUtil(201,"Review is Successfully",reviewDto);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAll(){
        return new ResponseUtil(200,"Review is Load",reviewService.getAll());
    }
    @DeleteMapping(path = "delete/{id}")
    public ResponseUtil delete(@PathVariable(value = "id") String id){
        reviewService.delete(id);
        return new ResponseUtil(200,"Review Deleted",id);
    }
}
