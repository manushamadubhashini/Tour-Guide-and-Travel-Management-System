package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.AuthDTO;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.ResponseDTO;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.UserDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.UserService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.JwtUtil;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.ResponseUtil;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
        private final UserService userService;
        private final JwtUtil jwtUtil;

        //constructor injection
        public UserController(UserService userService, JwtUtil jwtUtil) {
            this.userService = userService;
            this.jwtUtil = jwtUtil;
        }
        @PostMapping(value = "/register")
        public ResponseEntity<ResponseDTO> registerUser(@RequestBody  UserDto userDTO) {
            try {
                int res = userService.saveUser(userDTO);
                switch (res) {
                    case VarList.Created -> {
                        String token = jwtUtil.generateToken(userDTO);
                        AuthDTO authDTO = new AuthDTO();
                        authDTO.setEmail(userDTO.getEmail());
                        authDTO.setToken(token);
                        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                    }
                    case VarList.Not_Acceptable -> {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                                .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                    }
                    default -> {
                        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                                .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                    }
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
            }
        }

    }
