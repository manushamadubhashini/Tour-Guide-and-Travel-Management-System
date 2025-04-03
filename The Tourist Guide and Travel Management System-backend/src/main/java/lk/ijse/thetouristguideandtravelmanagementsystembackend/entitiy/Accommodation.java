package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "accommodation")
public class Accommodation {
    @Id
    @Column(name = "accommodation_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "tour_id",nullable = false)
    private Tour tour;
    @Column(name = "accommodation_name",nullable = false,length = 60)
    private String name;
    @Column(name = "accommodation_description",nullable = false,length = 200)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false,length = 50)
    private AccommodationType accommodationType;
    @Column(nullable = false,length = 100)
    private String address;
    @Column(nullable = false)
    private double price;
    @OneToMany(mappedBy = "accommodation",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Booking> bookingArrayList=new ArrayList<>();


}
