package lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy;

import jakarta.persistence.*;
import lk.ijse.thetouristguideandtravelmanagementsystembackend.entitiy.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "transport")
public class Transport {
    @Id
    @Column(name = "trasport_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "tour_id",nullable = false)
    private Tour tour;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false,length = 50)
    private TransportType transportType;
    @Column(name = "price_per_km",nullable = false)
    private double pricePerKm;
    @Column(nullable = false)
    private int passengers;
    @OneToMany(mappedBy = "transport",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Booking> bookingArrayList=new ArrayList<>();
}
