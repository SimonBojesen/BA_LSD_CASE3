package backend.datalayer.entity;

import booking.entity.Address;
import booking.entity.Place;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String streetAddress;
    private int postalCode;
    private String city;

    public AddressDB() {
    }

    //Wrong one
    /*public AddressDB(String streetAddress, int postalCode, String city) {
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city = city;
    }*/

    public AddressDB(booking.entity.Address address) {
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

    public Address toAddress()
    {
        return new Address(streetAddress, postalCode, city);
    }
}