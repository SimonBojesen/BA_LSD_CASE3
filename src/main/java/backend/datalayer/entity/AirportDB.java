package backend.datalayer.entity;

import javax.persistence.*;

@Entity
@Table(name = "airport")
public class AirportDB {
    @Id
    private String IATA;
    private String name;
    @OneToOne
    private AddressDB addressDB;
    private boolean active;

    public AirportDB() {
    }

    public AirportDB(String IATA, String name, AddressDB address, boolean active) {
        this.IATA = IATA;
        this.name = name;
        this.addressDB = address;
        this.active = active;
    }

    public String getIATA() {
        return IATA;
    }

    public String getName() {
        return name;
    }

    public AddressDB getAddress() {
        return addressDB;
    }

    public boolean isActive() {
        return active;
    }
}
