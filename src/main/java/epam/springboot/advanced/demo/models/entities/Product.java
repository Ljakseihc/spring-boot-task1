package epam.springboot.advanced.demo.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public Product(String name, BigDecimal cost, Boolean isAvailable) {
        this.name = name;
        this.cost = cost;
        this.isAvailable = isAvailable;
    }
}
