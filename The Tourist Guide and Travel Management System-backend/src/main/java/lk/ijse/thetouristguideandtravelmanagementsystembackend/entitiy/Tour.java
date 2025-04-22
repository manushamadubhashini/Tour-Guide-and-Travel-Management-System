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
@Table(name = "tour")
public class Tour {
    @Id
    @Column(name = "tour_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "destination_id",nullable = false)
    private Destination destination;
    @Column(name = "tour_name",nullable = false,length = 60)
    private String name;
    @Lob
    @Column(name = "tour_description",nullable = false,columnDefinition = "LONGTEXT")
    private String description;
    @Column(nullable = false)
    private int duration;
    @Column(nullable = false)
    private double price;
    @Column(name = "image_path")
    private String imagePath;
    @OneToMany(mappedBy = "tour",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TourSchedule> tourScheduleList=new ArrayList<>();
    @OneToMany(mappedBy = "tour",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<GuideTour> guideTourList=new ArrayList<>();
    @OneToMany(mappedBy = "tour",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Booking> bookingArrayList=new ArrayList<>();
    @OneToMany(mappedBy = "tour",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Accommodation> accommodationList=new ArrayList<>();
    @OneToMany(mappedBy = "tour",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Transport> transportList=new ArrayList<>();

}
