package us.navonod.flightlog;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/pilots")
public class PilotController {

    private final PilotRepository repository;

    public PilotController(PilotRepository repository) {
        this.repository = repository;
    }

    // Endpoints

    //Create
    @PostMapping("")
    public Pilot create(@RequestBody Pilot pilot) {
        return this.repository.save(pilot);
    }

    //Read
    @GetMapping("/{id}")
    public Pilot read(@PathVariable(value="id") Long id) {
        return this.repository.findById(id).orElse(new Pilot());
    }


    //Update
    public Pilot update(
            @PathVariable(value="id") Long id,
            @RequestBody Pilot newPilotInfo
    ) {
        Optional<Pilot> oldPilotInfo = this.repository.findById(id);
        if (newPilotInfo.getFirstName() != null) {
            oldPilotInfo.get().setFirstName(newPilotInfo.getFirstName());
        }
        if (newPilotInfo.getLastName() != null) {
            oldPilotInfo.get().setLastName(newPilotInfo.getLastName());
        }
        return this.repository.save(oldPilotInfo.get());
    }

    //Delete
    @DeleteMapping("/{id}")
    public Map<String, Long> delete(@PathVariable(value="id") Long id) {
        Map<String, Long> count = new HashMap<>();
        Optional<Pilot> pilot = this.repository.findById(id);
        pilot.ifPresent(this.repository::delete);
        count.put("count", this.repository.count());
        return count;
    }

    //List
    @GetMapping("")
    public Iterable<Pilot> getAll() {
        return this.repository.findAll();
    }
}