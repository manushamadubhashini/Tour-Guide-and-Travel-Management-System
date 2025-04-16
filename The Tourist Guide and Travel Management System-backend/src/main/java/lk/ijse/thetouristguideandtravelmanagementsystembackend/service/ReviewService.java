package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.advicer.FileConversionException;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.ReviewDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Review;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Tour;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.ReviewRepo;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.DataConversionUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepo reviewRepo;
    @Autowired
    private ModelMapper modelMapper;


    public void save(ReviewDto reviewDto){
        try {
//            if (reviewDto.getTourId() !=null) {
                Review review = modelMapper.map(reviewDto, Review.class);
                if (review.getId() == null) {
                    review.setId(UUID.randomUUID().toString());
                }
                reviewRepo.save(review);
                System.out.println("Save SuccessFully!");

        }catch (RuntimeException e){
            throw new RuntimeException("Not Saved!");
        }
    }
    public List<ReviewDto>  getAll(){
        try {
            return modelMapper.map(reviewRepo.findAll(),new TypeToken<List<ReviewDto>>(){}.getType());
        }catch (RuntimeException e){
            throw new RuntimeException("Not Load");
        }

    }
    public void delete(String id){
        try {
            if (reviewRepo.existsById(id)){
                reviewRepo.deleteById(id);
                System.out.println("Deleted Successfully!");
            }
        }catch (RuntimeException e){
            throw new RuntimeException("Not Deleted!");
        }
    }
}
