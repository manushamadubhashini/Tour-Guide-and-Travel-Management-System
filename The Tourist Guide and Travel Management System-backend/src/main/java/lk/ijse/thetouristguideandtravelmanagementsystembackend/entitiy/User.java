package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id")
    private String id;
    @Column(name = "user_name")
    private String userName;
    @Column(nullable = false,length = 200)
    private String email;
    @Column(nullable = false,length = 255)
    private String password;
    @Column(nullable = false)
    private String role;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<GuideTour> guideTourList=new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Booking> bookingList=new ArrayList<>();
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<Review> reviewList=new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Notification> notificationList=new ArrayList<>();
}
