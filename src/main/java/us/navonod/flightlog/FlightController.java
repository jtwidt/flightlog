package us.navonod.flightlog;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightRepository repository;

    public FlightController(FlightRepository repository) {
        this.repository = repository;
    }

    // Endpoints

    // Create
    @PostMapping("")
    public Flight create(@RequestBody Flight flight) {
        return this.repository.save(flight);
    }

    // Read
    @GetMapping("/{id}")
    public Flight read(@PathVariable(value="id") Long id) {
        return this.repository.findById(id).orElse(new Flight());
    }

    // Update
    @PatchMapping("/{id}")
    public Flight update(
            @PathVariable(value="id") Long id,
            @RequestBody Flight newFlightInfo
    ) {
        Optional<Flight> oldFlightInfo = this.repository.findById(id);
        if (newFlightInfo.getPilot() != null) {
            oldFlightInfo.get().setPilot(newFlightInfo.getPilot());
        }
        // plane_model_id
        if (newFlightInfo.getPlaneModel() != null) {
            oldFlightInfo.get().setPlaneModel(newFlightInfo.getPlaneModel());
        }
        // departure
        if (newFlightInfo.getDeparture() != null) {
            oldFlightInfo.get().setDeparture(newFlightInfo.getDeparture());
        }
        // arrival
        if (newFlightInfo.getArrival() != null) {
            oldFlightInfo.get().setArrival(newFlightInfo.getArrival());
        }
        // duration
        if (newFlightInfo.getDuration() != 0) {
            oldFlightInfo.get().setDuration(newFlightInfo.getDuration());
        }
        // origin
        if (newFlightInfo.getOrigin() != null) {
            oldFlightInfo.get().setOrigin(newFlightInfo.getOrigin());
        }
        // destination
        if (newFlightInfo.getDestination() != null) {
            oldFlightInfo.get().setDestination(newFlightInfo.getDestination());
        }
        return this.repository.save(oldFlightInfo.get());
    }

    // Delete
    @DeleteMapping("/{id}")
    public Map<String, Long> delete(@PathVariable(value="id") Long id) {
        Map<String, Long> count = new HashMap<>();
        Optional<Flight> flight = this.repository.findById(id);
        flight.ifPresent(this.repository::delete);
        count.put("count", this.repository.count());
        return count;
    }

    // List
    @GetMapping("")
    public Iterable<Flight> getAll() {
        return this.repository.findAll();
    }
}
