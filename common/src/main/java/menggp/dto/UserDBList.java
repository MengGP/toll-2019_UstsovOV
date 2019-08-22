package menggp.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDBList {

    private List<UserDBEntry> userDBList;

    public UserDBList() {
        userDBList = new ArrayList<>();
    }

    public List<UserDBEntry> getUserDBList() {
        return userDBList;
    }

    public void setUserDBList(List<UserDBEntry> userDBList) {
        this.userDBList = userDBList;
    }
} // end_method
