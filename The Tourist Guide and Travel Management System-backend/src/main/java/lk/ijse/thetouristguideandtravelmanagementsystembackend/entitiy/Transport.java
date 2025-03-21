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
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false,length = 50)
    private TransportType transportType;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String distance;
    @Column(nullable = false)
    private double price;
    @OneToMany(mappedBy = "transport",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TransportItemDetails> transportItemDetailsList=new ArrayList<>();
}
