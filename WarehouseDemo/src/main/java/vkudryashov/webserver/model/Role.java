package vkudryashov.webserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean object that represent role of {@link User}.
 *
 * @author Vladimir Kudryashov
 * @version 1.0
 * */

@Entity
@Table(name="roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String symbol;
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "role_pages",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "page_id"))
    private Set<Page> pages;
    private boolean active;
}
