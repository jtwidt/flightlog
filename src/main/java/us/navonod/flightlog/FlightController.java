package us.navonod.flightlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;

@RestController
@RequestMapping("/flights")
public class FlightController {

//    private final FlightRepository repository;
//
//    public FlightController(FlightRepository repository) {
//        this.repository = repository;
//    }

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PilotRepository pilotRepository;

    // Endpoints

    // Create
    @PostMapping("")
    public Flight create(@RequestBody Flight flight) {
        return flightRepository.save(flight);
    }

    // Read
    @GetMapping("/{id}")
    public Flight read(@PathVariable(value="id") Long id) {
        return flightRepository.findById(id).orElse(new Flight());
    }

    // Update
    @PatchMapping("/{id}")
    public Flight update(
            @PathVariable(value="id") Long id,
            @RequestBody Flight newFlightInfo,
            @RequestParam(value="pilot_id", required = false) Long pilotId
    ) {
        Optional<Flight> oldFlightInfo = flightRepository.findById(id);
        if (newFlightInfo.getPilot() != null) {
            oldFlightInfo.get().setPilot(newFlightInfo.getPilot());
        }
        // aircraftType
        if (newFlightInfo.getAircraftType() != null) {
            oldFlightInfo.get().setAircraftType(newFlightInfo.getAircraftType());
        }
        // departure
        if (newFlightInfo.getDeparture() != null) {
            oldFlightInfo.get().setDeparture(newFlightInfo.getDeparture());
        }
        // origin
        if (newFlightInfo.getOrigin() != null) {
            oldFlightInfo.get().setOrigin(newFlightInfo.getOrigin());
        }
        // destination
        if (newFlightInfo.getDestination() != null) {
            oldFlightInfo.get().setDestination(newFlightInfo.getDestination());
        }
        if (newFlightInfo.getPilotNotes() != null) {
            oldFlightInfo.get().setPilotNotes(newFlightInfo.getPilotNotes());
        }
        if (pilotId != null) {
            Optional<Pilot> pilot = pilotRepository.findById(pilotId);
            if (pilot.isPresent()) {
                oldFlightInfo.get().setPilot(pilot.get());
            }
        }
        return flightRepository.save(oldFlightInfo.get());
    }

    // Delete
    @DeleteMapping("/{id}")
    public Map<String, Long> delete(@PathVariable(value="id") Long id) {
        Map<String, Long> count = new HashMap<>();
        Optional<Flight> flight = flightRepository.findById(id);
        flight.ifPresent(flightRepository::delete);
        count.put("count", flightRepository.count());
        return count;
    }

    // List
    @GetMapping("")
    public Iterable<Flight> getAll() {
        return flightRepository.findAll();
    }

    @GetMapping("/byDate")
    public Calendar findByDepartureDate(
            @RequestParam(value="date", required = true) @DateTimeFormat(pattern="yyyy-MM-dd") Calendar date
    ) {
        return date;
    }
}
