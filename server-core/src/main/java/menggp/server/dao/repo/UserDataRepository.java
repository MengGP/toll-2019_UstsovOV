package menggp.server.dao.repo;

import menggp.server.dao.UserData;
import org.springframework.data.repository.CrudRepository;

public interface UserDataRepository extends CrudRepository<UserData,Integer> {
}
