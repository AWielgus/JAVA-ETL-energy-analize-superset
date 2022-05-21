package AW.etljava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Energy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private LocalDateTime dateTime;

    private long coal;
    private long gas;
    private long oil;
    private long solar;
    private long wind;
    private long hydro;
    private long other;

}
