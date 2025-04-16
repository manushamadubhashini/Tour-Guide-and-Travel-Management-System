package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import jakarta.transaction.Transactional;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.DestinationDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.advicer.FileConversionException;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Destination;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.DestinationRepo;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.DataConversionUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

            if (destination.getId() == null) {
                destination.setId(UUID.randomUUID().toString());
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
    public List<DestinationDto> getAll() {
        try {
            return modelMapper.map(destinationRepo.findAll(),new TypeToken<List<DestinationDto>>(){}.getType());

        }catch (RuntimeException e){
            throw new RuntimeException("Not Load");
        }
    }




    public void delete(String id){
        if (destinationRepo.existsById(id)){
            destinationRepo.deleteById(id);
        }
    }
    public void update(DestinationDto destinationDto){
        if(destinationRepo.existsById(destinationDto.getId())){
            Destination destination=modelMapper.map(destinationDto, Destination.class);
            destinationRepo.save(destination);

        }
    }
}

