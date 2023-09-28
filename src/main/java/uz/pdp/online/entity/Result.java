package uz.pdp.online.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private User user;

    @ManyToOne
    private Task task;

    @Column(nullable = false)
    private String responseResult;

    @Column(nullable = false)
    private boolean isCorrect;
}
