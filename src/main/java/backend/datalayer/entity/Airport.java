package backend.datalayer.entity;

import javax.persistence.*;

@Entity
public class Airport {
    @Id
    private String IATA;
    private String name;
    @OneToOne
    private Address address;
    private boolean active;

    public Airport() {
    }

    public Airport(booking.entity.Airport airport) {
        this.IATA = airport.getIATA();
        this.name = airport.getName();
        this.address = new Address(airport.getAddress());
        this.active = airport.isActive();
    }

    public String getIATA() {
        return IATA;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public boolean isActive() {
        return active;
    }
}
