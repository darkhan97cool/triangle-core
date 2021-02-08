package kz.dev.triangle.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Data
@Builder(toBuilder=true)
@Table(name="TriangleEntity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class TriangleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "side1",nullable = false)
    private int side1;

    @Column(name = "side2",nullable = false)
    private int side2;

    @Column(name = "side3", nullable = false)
    private int side3;

    @Column(name = "perimeter", nullable = false)
    private int perimeter;

    @Column(name = "area", nullable = false)
    private double area;

    public static  TriangleEntityBuilder builder(int side1, int side2, int side3, int perimeter, double area) {
        return new TriangleEntityBuilder().side1(side1).side2(side2).side3(side3).perimeter(perimeter).area(area);
    }
}
