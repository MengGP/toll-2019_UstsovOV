package menggp.server.dao;

import menggp.server.dao.repo.UserDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *  Метод реализует CRUD операции для работы с таблицей USERDATA в проекте server-core
 *      Create
 *       Read
 *        Update
 *         Delete
 */
public class CrudUserMethods {

    private static final Logger Log = LoggerFactory.getLogger(CrudUserMethods.class);
    private List<UserData> all;

    @Autowired
    UserDataRepository userDataRepository;

    public UserData create(String name, String password, String role) {
        UserData userData = new UserData();
        
        userData.setName(name);
        userData.setPassword(password);
        userData.setRole(role);
 
        return  userDataRepository.save(userData);
    } // end_method

    public void read() {
        all = (List<UserData>) userDataRepository.findAll();

        if (all.size() == 0) {
            Log.info("NO RECORDS");
        } else {
            all.stream().forEach(userData -> Log.info(userData.toString()));
        }
    } // end_method

    public void update(UserData userData, String name, String password, String role) {
        userData.setName(name);
        userData.setPassword(password);
        userData.setRole(role);

        userDataRepository.save(userData);
    } // end_method

    public void delete(UserData userData) {
        userDataRepository.delete(userData);
    } // end_method

    public void flushTable() {
        all = (List<UserData>) userDataRepository.findAll();
        if (all.size() == 0) {
            Log.info("NO RECORDS");
        }
        else {
            all.stream().forEach(userData -> delete(userData) );// log.info(userData.toString()));
        }
    } // end_method


} // end_method
