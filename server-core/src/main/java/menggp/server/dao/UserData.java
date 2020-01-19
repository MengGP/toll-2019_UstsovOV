package menggp.server.dao;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

/**
 *  Описание таблицы Userdata для БД
 */

@Entity
@Table(name="userdata")
public class UserData {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "ID")
    int id;

    @Column(name = "NAME")
    String name;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "ROLE")
    String role;

    @Column(name = "ENABLED")
    boolean enabled;

    public String toString() {
        return "{ User = "+name+", pass = "+password+", role = "+role+", enabled = "+enabled+" }";
    } // end_method

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

} // end_class
