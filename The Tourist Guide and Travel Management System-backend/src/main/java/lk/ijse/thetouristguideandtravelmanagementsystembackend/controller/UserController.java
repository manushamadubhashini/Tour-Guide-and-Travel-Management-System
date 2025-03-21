package lk.ijse.thetouristguideandtravelmanagementsystembackend.controller;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.dto.UserDto;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.service.UserService;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil save(@RequestBody UserDto userDto){
        userService.save(userDto);
        return new ResponseUtil(201,"User is Saved",userDto);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAll(){
        return new ResponseUtil(200,"User is Load",userService.getAll());
    }
    @DeleteMapping(path = "delete/{id}")
    public ResponseUtil delete(@PathVariable(value = "id") String id){
        userService.delete(id);
        return new ResponseUtil(200,"User Deleted",id);
    }
    @PutMapping
    public ResponseUtil update(@RequestBody UserDto userDto){
        userService.update(userDto);
        return new ResponseUtil(200,"User Updated",userDto);
    }
}
