package booking.datalayer.entity;

import booking.entity.Rating;

import javax.persistence.*;

@Entity
@Table(name = "hotel")
public class HotelDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private AddressDB addressDB;
    private boolean active;
    private Rating rating;

    public HotelDB() {
    }

    public HotelDB(String name, AddressDB address, boolean active, Rating rating) {
        this.name = name;
        this.addressDB = address;
        this.active = active;
        this.rating = rating;
    }
    public Long getId() {
        return id;
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

    public Rating getRating() {
        return rating;
    }
}
