package us.navonod.flightlog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pilots")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pilot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pilot")
    @JsonIgnore
    private List<Flight> flights;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @PreRemove
    private void removeFlightAssignments() {
        for (Flight flight : flights) {
            flight.setPilot(null);
        }
    }
}
