package backend.datalayer.entity;

import javax.persistence.*;

@Entity
public class Address{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String streetAddress;
    private int postalCode;
    private String city;

    public Address() {
    }

    //Wrong one
    public Address(String streetAddress, int postalCode, String city) {
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Address(booking.entity.Address address) {
        this.streetAddress = address.getStreetAddress();
        this.postalCode = address.getPostalCode();
        this.city = address.getCity();
    }

    public Long getId() {
        return id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}