package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import jakarta.transaction.Transactional;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.advicer.FileConversionException;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.TourDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Tour;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.TourRepo;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.DataConversionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TourService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TourRepo tourRepo;

    @Transactional
    public void save(TourDto tourDto){
        try {
            if (tourDto.getDestinationId() != null) {
                Tour tour = modelMapper.map(tourDto, Tour.class);
                if (tour.getId() == null) {
                    tour.setId(UUID.randomUUID().toString());
                }
                if (tourDto.getImage() != null && !tourDto.getImage().isEmpty()) {
                    String imagePath = DataConversionUtil.saveToFileSystem(tourDto.getImage());
                    tour.setImagePath(imagePath);
                }

                tourRepo.save(tour);
                System.out.println("Saved Successfully!");
            }
        }catch (FileConversionException e){
            throw new FileConversionException("Cannot convert image to base64", e);
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("not saved");
        }

    }
    public List<TourDto> getAll(){
        return tourRepo.findAll()
                .stream()
                .map(tour -> {
                    MultipartFile imageFile = null;
                    String imageBase64 = null;
                    if (tour.getImagePath() != null && !tour.getImagePath().isEmpty()){
                        imageFile = DataConversionUtil.pathToMultipartFile(tour.getImagePath());
                        // Convert the file to base64 for frontend display
                        imageBase64 = DataConversionUtil.fileToBase64(tour.getImagePath());
                    }
                    TourDto dto = new TourDto(
                            tour.getId(),
                            tour.getDestination().getId(),
                            tour.getName(),
                            tour.getDescription(),
                            tour.getDuration(),
                            tour.getPrice(),
                            imageFile
                    );
                    dto.setImageBase64(imageBase64);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    public List<TourDto> findById(String id){
        return tourRepo.findById(id)
                .stream()
                .map(tour -> {
                    MultipartFile imageFile = null;
                    String imageBase64 = null;
                    if (tour.getImagePath() != null && !tour.getImagePath().isEmpty()) {
                        imageFile = DataConversionUtil.pathToMultipartFile(tour.getImagePath());
                        // Convert the file to base64 for frontend display
                        imageBase64 = DataConversionUtil.fileToBase64(tour.getImagePath());
                    }
                    TourDto tourDto = new TourDto(
                            tour.getId(),
                            tour.getDestination().getId(),
                            tour.getName(),
                            tour.getDescription(),
                            tour.getDuration(),
                            tour.getPrice(),
                            imageFile
                    );
                    tourDto.setImageBase64(imageBase64);
                    return tourDto;
                })
                .collect(Collectors.toList());

    }
    public void delete(String id){
        if (tourRepo.existsById(id)){
            Tour tour = tourRepo.findById(id).orElse(null);
            if (tour != null && tour.getImagePath() != null) {
                // Delete the image file from the filesystem
                DataConversionUtil.deleteFromFileSystem(tour.getImagePath());
            }
            tourRepo.deleteById(id);
        }

    }
//    public void update(TourDto tourDto){
//        try {
//            if (tourRepo.existsById(tourDto.getId())){
//                Tour tour=modelMapper.map(tourDto, Tour.class);
//                if (tourDto.getImage() != null){
//                    tour.setImage(DataConversionUtil.toBase64(tourDto.getImage()));
//                }
//                tourRepo.save(tour);
//                System.out.println("Updated SuccessFully");
//            }
//        }catch (FileConversionException e){
//            throw new FileConversionException("Cannot convert image to base64", e);
//        }catch (RuntimeException e){
//            e.printStackTrace();
//            throw new RuntimeException("not Updated");
//        }
//
//    }
@Transactional
public void update(TourDto tourDto){
    try {
        if (tourRepo.existsById(tourDto.getId())){
            // Get the existing tour to retrieve the current image path
            Tour existingTour = tourRepo.findById(tourDto.getId()).orElse(null);
            String oldImagePath = existingTour != null ? existingTour.getImagePath() : null;

            Tour tour = modelMapper.map(tourDto, Tour.class);

            // If new image is provided, save it and update the path
            if (tourDto.getImage() != null && !tourDto.getImage().isEmpty()){
                String newImagePath = DataConversionUtil.saveToFileSystem(tourDto.getImage());
                tour.setImagePath(newImagePath);

                // Delete the old image if it exists
                if (oldImagePath != null && !oldImagePath.isEmpty()) {
                    DataConversionUtil.deleteFromFileSystem(oldImagePath);
                }
            } else if (existingTour != null) {
                // Keep the old image path if no new image is provided
                tour.setImagePath(oldImagePath);
            }

            tourRepo.save(tour);
            System.out.println("Updated Successfully");
        }
    } catch (FileConversionException e){
        throw new FileConversionException("Cannot save image file", e);
    } catch (RuntimeException e){
        e.printStackTrace();
        throw new RuntimeException("not Updated");
    }
}
}
