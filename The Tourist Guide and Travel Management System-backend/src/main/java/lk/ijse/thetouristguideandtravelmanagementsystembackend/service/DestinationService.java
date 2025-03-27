package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import jakarta.transaction.Transactional;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.DestinationDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.advicer.FileConversionException;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Destination;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.DestinationRepo;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.DataConversionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class DestinationService {
    @Autowired
    private DestinationRepo destinationRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Transactional
    public void save(DestinationDto destinationDto) {
        try {
            Destination destination = modelMapper.map(destinationDto, Destination.class);


            // Manually set ID if not auto-generated
            if (destination.getId() == null) {
                destination.setId(UUID.randomUUID().toString());
            }

            // Convert and set image
            if (destinationDto.getImage() != null) {
                destination.setImage(DataConversionUtil.toBase64(destinationDto.getImage()));
            }

            destinationRepo.save(destination);
            System.out.println("Saved Successfully!");

        } catch (FileConversionException e) {
            throw new FileConversionException("Cannot convert image to base64", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("not saved");
        }
    }

//    public List<DestinationDto> getAll() {
//        try {
//            // Get all destinations with ACTIVE status
////            List<Destination> destinations = destinationRepo.findAll();
////
////            // Convert entities to DTOs using ModelMapper
////            return destinations.stream()
////                    .map(destination -> modelMapper.map(destination, DestinationDto.class))
////                    .collect(Collectors.toList());
//            return modelMapper.map(destinationRepo.findAll(),
//                    new TypeToken<List<DestinationDto>>() {
//                    }.getType());
//        } catch (Exception e) {
//            throw new RuntimeException("Error retrieving destinations", e);
//        }
//    }

    public List<DestinationDto> getAll() {
        return destinationRepo.findAll()
                .stream()
                .map(destination -> {
                    MultipartFile imageFile = null;
                    if (destination.getImage() != null && !destination.getImage().isEmpty()) {
//                        imageFile = DataConversionUtil.convertToMultipartFile(destination.getImage());
                    }

                    return new DestinationDto(
                            destination.getId(),
                            destination.getName(),
                            destination.getDescription(),
                            destination.getLocation(),
                            destination.getWeather_info(),
                            destination.getBest_time_to_visit(),
                            imageFile
                    );
                })
                .collect(Collectors.toList());
    }




    public void delete(String id){
        if (destinationRepo.existsById(id)){
            destinationRepo.deleteById(id);
        }
    }
    public void update(DestinationDto destinationDto){
        if(destinationRepo.existsById(destinationDto.getId())){
            Destination destination=modelMapper.map(destinationDto, Destination.class);

            // Convert and set image
            if (destinationDto.getImage() != null) {
                destination.setImage(DataConversionUtil.toBase64(destinationDto.getImage()));
            }
            destinationRepo.save(destination);

        }
    }
}

