package app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    private Long id;
    private String name;
    private String email;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
