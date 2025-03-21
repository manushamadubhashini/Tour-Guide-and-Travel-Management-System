package lk.ijse.thetouristguideandtravelmanagementsystembackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String id;
    private String userName;
    private String email;
    private String password;
    private String role;
}
