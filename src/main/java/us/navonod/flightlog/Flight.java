package us.navonod.flightlog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.Duration;
import java.util.Date;

@Entity
@Table(name="flights")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pilot_id")
    @JsonIgnore
    private Pilot pilot;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date departure;
    private String origin;
    private String destination;
    private String aircraftType;
    private String pilotNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getPilotNotes() {
        return pilotNotes;
    }

    public void setPilotNotes(String pilotNotes) {
        this.pilotNotes = pilotNotes;
    }
}
