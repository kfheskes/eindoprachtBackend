package nl.backend.eindoprdracht.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    @Lob
    private byte[] data;
    private String description;

    private String contentType;
    @ManyToOne
    @JsonIgnore
    private Order order;

}
