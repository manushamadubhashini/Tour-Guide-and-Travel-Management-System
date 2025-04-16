package lk.ijse.thetouristguideandtravelmanagementsystembackend.service;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.UserDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.User;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.Role;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.repo.UserRepo;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    //    public void save(UserDto userDto){
//        try {
//            User user=modelMapper.map(userDto, User.class);
//            if (user.getId() == null) {
//                user.setId(UUID.randomUUID().toString());
//            }
//            user.setRole(Role.valueOf(userDto.getRole()));
//            userRepo.save(user);
//            System.out.println("Saved Successfully!");
//        }catch (IllegalArgumentException e){
//            throw new RuntimeException("Invalid User Type");
//        }catch (RuntimeException e){
//            e.printStackTrace();
//            throw new RuntimeException("Not Saved");
//        }
//    }
    public List<UserDto> getAll() {
        try {
            return modelMapper.map(userRepo.findAll(), new TypeToken<List<UserDto>>() {
            }.getType());
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Not Load");
        }
    }

    public void delete(String id) {
        try {
            if (userRepo.existsById(id)) {
                userRepo.deleteById(id);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Not Deleted");
        }

    }

    //    public void update(UserDto userDto){
//        try {
//            if (userRepo.existsById(userDto.getId())){
//                User user=modelMapper.map(userDto, User.class);
//                user.setRole(Role.valueOf(userDto.getRole()));
//                userRepo.save(user);
//                System.out.println("Updated Successfully");
//            }
//        }catch (IllegalArgumentException e){
//            throw new RuntimeException("Invalid User Type");
//        }catch (RuntimeException e){fix error

//            e.printStackTrace();
//            throw new RuntimeException("Not Updated");
//        }
//    }
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepo.findByEmail(email);
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
}
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    public UserDto loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        return modelMapper.map(user, UserDto.class);
    }


    public int saveUser(UserDto userDTO) {
        if (userRepo.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userDTO.setRole(userDTO.getRole());
            userRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }
}
