package us.navonod.flightlog;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface FlightRepository extends CrudRepository<Flight, Long> {

    List<Flight> findByDepartureBetween(Date date1, Date date2);

}
