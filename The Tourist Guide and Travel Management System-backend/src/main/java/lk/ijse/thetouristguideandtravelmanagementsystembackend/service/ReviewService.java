package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.ReviewDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Review;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.ReviewRepo;
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
            Review review=modelMapper.map(reviewDto, Review.class);
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
        return modelMapper.map(reviewRepo.findAll(),new TypeToken<List<ReviewDto>>(){}.getType());
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
    public void update(ReviewDto reviewDto){
        try {
            if (reviewRepo.existsById(reviewDto.getId())){
                Review review=modelMapper.map(reviewDto, Review.class);
                reviewRepo.save(review);
                System.out.println("Updated Successfully");;
            }
        }catch (RuntimeException e){
            throw new RuntimeException("Not Updated!");
        }
    }
}
