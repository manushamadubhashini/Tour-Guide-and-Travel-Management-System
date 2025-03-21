package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.UserDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.User;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.Role;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    public void save(UserDto userDto){
        try {
            User user=modelMapper.map(userDto, User.class);
            if (user.getId() == null) {
                user.setId(UUID.randomUUID().toString());
            }
            user.setRole(Role.valueOf(userDto.getRole()));
            userRepo.save(user);
            System.out.println("Saved Successfully!");
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Invalid User Type");
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("Not Saved");
        }
    }
    public List<UserDto> getAll(){
        try {
            return modelMapper.map(userRepo.findAll(), new TypeToken<List<UserDto>>(){}.getType());
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("Not Load");
        }
    }
    public void delete(String id){
        try {
            if (userRepo.existsById(id)){
                userRepo.deleteById(id);
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("Not Deleted");
        }

    }
    public void update(UserDto userDto){
        try {
            if (userRepo.existsById(userDto.getId())){
                User user=modelMapper.map(userDto, User.class);
                user.setRole(Role.valueOf(userDto.getRole()));
                userRepo.save(user);
                System.out.println("Updated Successfully");
            }
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Invalid User Type");
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("Not Updated");
        }
    }

}
