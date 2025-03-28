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
                if (reviewDto.getImage() != null && !reviewDto.getImage().isEmpty()) {
                    review.setImagePath(DataConversionUtil.saveToFileSystem(reviewDto.getImage()));
                }
                reviewRepo.save(review);
                System.out.println("Save SuccessFully!");

        }catch (FileConversionException e){
            throw new FileConversionException("Cannot convert image to base64", e);
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
                Review review = reviewRepo.findById(id).orElse(null);
                if (review!= null && review.getImagePath() != null) {
                    DataConversionUtil.deleteFromFileSystem(review.getImagePath());
                }
                reviewRepo.deleteById(id);
                System.out.println("Deleted Successfully!");
            }
        }catch (RuntimeException e){
            throw new RuntimeException("Not Deleted!");
        }
    }
}
