package backend.datalayer.entity;

import booking.entity.Rating;

import javax.persistence.*;

@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private Address address;
    private boolean active;
    private Rating rating;

    public Hotel() {
    }

    public Hotel(booking.entity.Hotel hotel) {
        this.name = hotel.getName();
        this.address = new Address(hotel.getAddress());
        this.active = hotel.isActive();
        this.rating = hotel.getRating();
    }
    public Long getId() {
        return id;
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

    public Rating getRating() {
        return rating;
    }
}
