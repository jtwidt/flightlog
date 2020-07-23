package us.navonod.flightlog;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/plane-model")
public class PlaneModelController {

    private final PlaneModelRepository repository;

    public PlaneModelController(PlaneModelRepository repository) {
        this.repository = repository;
    }

    // Endpoints

    //Create
    @PostMapping("")
    public PlaneModel create(@RequestBody PlaneModel planeModel) {
        return this.repository.save(planeModel);
    }

    //Read
    @GetMapping("/{id}")
    public PlaneModel read(@PathVariable(value="id") Long id) {
        return this.repository.findById(id).orElse(new PlaneModel());
    }

    //Update
    @PatchMapping("/{id}")
    public PlaneModel update(
            @PathVariable(value="id") Long id,
            @RequestBody PlaneModel newPlaneModelInfo
    ) {
        Optional<PlaneModel> oldPlaneModelInfo = this.repository.findById(id);
        if (newPlaneModelInfo.getType() != null) {
            oldPlaneModelInfo.get().setType(newPlaneModelInfo.getType());
        }
        if (newPlaneModelInfo.getPassengers() != 0) {
            oldPlaneModelInfo.get().setPassengers(newPlaneModelInfo.getPassengers());
        }
        return this.repository.save(oldPlaneModelInfo.get());
    }

    //Delete
    @DeleteMapping("/{id}")
    public Map<String, Long> delete(@PathVariable(value="id") Long id) {
        Map<String, Long> count = new HashMap<>();
        Optional<PlaneModel> planeModel = this.repository.findById(id);
        planeModel.ifPresent(this.repository::delete);
        count.put("count", this.repository.count());
        return count;
    }

    //List
    @GetMapping("")
    public Iterable<PlaneModel> getAll() {
        return this.repository.findAll();
    }
}