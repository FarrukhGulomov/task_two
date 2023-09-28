package uz.pdp.online.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Language language;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String solution;
}
