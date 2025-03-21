package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.AccommodationDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.TourDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Accommodation;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.AccommodationType;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.AccommodationRepo;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.DataConversionUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepo accommodationRepo;
    @Autowired
    private ModelMapper modelMapper;

    public void save(AccommodationDto accommodationDto){
        try {
            Accommodation accommodation=modelMapper.map(accommodationDto, Accommodation.class);
            if (accommodation.getId() == null) {
                accommodation.setId(UUID.randomUUID().toString());
            }
            if (accommodationDto.getImage()!=null){
                accommodation.setImage(DataConversionUtil.toBase64(accommodationDto.getImage()));
            }
            accommodation.setAccommodationType(AccommodationType.valueOf(accommodationDto.getType()));
            accommodationRepo.save(accommodation);
            System.out.println("Saved Successfully!");
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Invalid Accommodation Type");
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("Not Saved");
        }
    }

    public List<AccommodationDto> getAll(){
        return accommodationRepo.findAll()
                .stream()
                .map(accommodation -> {
                    MultipartFile imageFile=null;
                    if (accommodation.getImage() != null && !accommodation.getImage().isEmpty()){
                        imageFile=DataConversionUtil.convertToMultipartFile(accommodation.getImage());
                    }
                    return new AccommodationDto(
                            accommodation.getId(),
                            accommodation.getDestination().getId(),
                            accommodation.getName(),
                            accommodation.getDescription(),
                            accommodation.getAccommodationType().name(),
                            accommodation.getAddress(),
                            accommodation.getPrice(),
                            imageFile
                    );
                })
                .collect(Collectors.toList());
    }
    public void delete(String id){

        try {
            if (accommodationRepo.existsById(id)) {
                accommodationRepo.deleteById(id);
                System.out.println("Deleted Successfully!");

            }
        }catch (RuntimeException e){
            throw new RuntimeException("Not Deleted!");
        }

    }
    public void update(AccommodationDto accommodationDto){
        try {
            if (accommodationRepo.existsById(accommodationDto.getId())){
                Accommodation accommodation=modelMapper.map(accommodationDto, Accommodation.class);
                if (accommodationDto.getImage()!=null){
                    accommodation.setImage(DataConversionUtil.toBase64(accommodationDto.getImage()));
                }
                accommodation.setAccommodationType(AccommodationType.valueOf(accommodationDto.getType()));
                accommodationRepo.save(accommodation);
                System.out.println("Updated Successfully!");
            }
        }catch (RuntimeException e){
            throw new RuntimeException("Not Updated");
        }
    }

}
