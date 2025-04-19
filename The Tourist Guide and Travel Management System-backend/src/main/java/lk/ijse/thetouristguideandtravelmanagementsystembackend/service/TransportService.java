package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.TransportDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Accommodation;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.Transport;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.TransportType;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.AccommodationRepo;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.TransportRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransportService {
    @Autowired
    private TransportRepo transportRepo;
    @Autowired
    private ModelMapper modelMapper;
    private AccommodationRepo accommodationRepo;

    public void save(TransportDto transportDto){
        try{
            Transport transport=modelMapper.map(transportDto, Transport.class);
            if (transport.getId() == null) {
                transport.setId(UUID.randomUUID().toString());
            }
            transport.setTransportType(TransportType.valueOf(transportDto.getType()));
            transportRepo.save(transport);
            System.out.println("Saved Successfully!");
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Invalid Transport Type");
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("Not Saved");
        }
    }
    public List<TransportDto> getAll(){
        try {
            return modelMapper.map(transportRepo.findAll(),new TypeToken<List<TransportDto>>(){}.getType());
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("Not Load");
        }

    }
    public List<TransportDto> findByTourId(String id){
        List<Transport> transports=transportRepo.findByTourId(id);
        return modelMapper.map(transports,new TypeToken<List<TransportDto>>(){}.getType());



    }
    public void delete(String id){
        try {
            if (transportRepo.existsById(id)){
                transportRepo.deleteById(id);
                System.out.println("Deleted Successfully");
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("Not Deleted");
        }
    }
    public void update(TransportDto transportDto){
        try {
            if (transportRepo.existsById(transportDto.getId())){
                Transport transport=modelMapper.map(transportDto, Transport.class);
                transport.setTransportType(TransportType.valueOf(transportDto.getType()));
                transportRepo.save(transport);
                System.out.println("Updated Successfully!");
            }
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Invalid Transport Type");
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("Not Updated");
        }
    }

}
