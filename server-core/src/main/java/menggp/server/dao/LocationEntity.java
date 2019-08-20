package menggp.server.dao;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

/**
 *  Описание таблицы Locations для БД
 */
@Entity
@Table(name="locations")
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "ID")
    int id;

    @Column(name = "LOCATIONSTRING")
    String locationString;

    public String toString() {
        return "Location{ id=" + id + ", Location string =" + locationString+ " }";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }

    public String getLocationString() {
        return locationString;
    }

} // end_class
