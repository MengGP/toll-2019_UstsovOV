package menggp.server.dao;

import menggp.dto.UserDBEntry;
import menggp.dto.UserDBList;
import menggp.server.dao.repo.UserDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.soap.SOAPBinding;
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

    public UserData create(String name, String password, String role, boolean enabled) {
        UserData userData = new UserData();
        
        userData.setName(name);
        userData.setPassword(password);
        userData.setRole(role);
        userData.setEnabled(enabled);
 
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

    public void update(UserData userData, String name, String password, String role, boolean enabled) {
        userData.setName(name);
        userData.setPassword(password);
        userData.setRole(role);
        userData.setEnabled(enabled);

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
    
    public UserDBList readUserDBList() {
        UserDBList userDBList = new UserDBList();
        List<UserDBEntry> userDB = new UserDBList().getUserDBList();


        all = (List<UserData>) userDataRepository.findAll();

//        for (int i=0; i<all.size(); i++) {
//            userDB.add()
//        }

        for (UserData iterator : all ) {
           UserDBEntry userDBEntry = new UserDBEntry();
           userDBEntry.setId( iterator.getId() );
           userDBEntry.setName( iterator.getName() );
           userDBEntry.setPassword( iterator.getPassword() );
           userDBEntry.setRole( iterator.getRole() );
           userDB.add( userDBEntry );
        }

        userDBList.setUserDBList(userDB);

        return userDBList;
    } // end_method

} // end_method
