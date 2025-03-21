package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "destination")
public class Destination {
    @Id
    @Column(name = "destination_id")
    private String id;
    @Column(name = "destination_name" ,length = 50)
    private String name;
    @Column(name = "destination_description",length = 200)
    private String description;
    private String location;
    @Column(length = 400)
    private String weather_info;
    @Column(length = 300)
    private String best_time_to_visit;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    @OneToMany(mappedBy = "destination",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Accommodation> accommodationList=new ArrayList<>();
    @OneToMany(mappedBy = "destination",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Tour> tourList=new ArrayList<>();


}
