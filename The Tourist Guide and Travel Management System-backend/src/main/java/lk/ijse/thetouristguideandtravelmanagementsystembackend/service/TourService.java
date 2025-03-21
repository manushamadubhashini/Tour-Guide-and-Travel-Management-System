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
                if (tourDto.getImage() != null) {
                    tour.setImage(DataConversionUtil.toBase64(tourDto.getImage()));
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
                    MultipartFile imageFile=null;
                    if (tour.getImage() != null && !tour.getImage().isEmpty()){
                        imageFile=DataConversionUtil.convertToMultipartFile(tour.getImage());
                    }
                    return new TourDto(
                            tour.getId(),
                            tour.getDestination().getId(),
                            tour.getName(),
                            tour.getDescription(),
                            tour.getDuration(),
                            tour.getPrice(),
                            imageFile
                    );
                })
                .collect(Collectors.toList());

    }
    public void delete(String id){
        if (tourRepo.existsById(id)){
            tourRepo.deleteById(id);
        }

    }
    public void update(TourDto tourDto){
        try {
            if (tourRepo.existsById(tourDto.getId())){
                Tour tour=modelMapper.map(tourDto, Tour.class);
                if (tourDto.getImage() != null){
                    tour.setImage(DataConversionUtil.toBase64(tourDto.getImage()));
                }
                tourRepo.save(tour);
                System.out.println("Updated SuccessFully");
            }
        }catch (FileConversionException e){
            throw new FileConversionException("Cannot convert image to base64", e);
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("not Updated");
        }

    }
}
