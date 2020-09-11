package vkudryashov.webserver.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="pages")
@Data
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;
    private String title;

    @JsonBackReference
    @ManyToMany(mappedBy = "pages")
    private Set<Role> roles;
}
