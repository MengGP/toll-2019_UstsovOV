package menggp.tracker.dao.repo;

import org.springframework.data.repository.CrudRepository;
import menggp.tracker.dao.GpsData;

public interface GpsDataRepository extends CrudRepository<GpsData, Integer> {
}
