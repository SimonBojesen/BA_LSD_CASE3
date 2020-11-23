package booking.datalayer.entity;

import booking.entity.Address;

import javax.persistence.*;
import java.util.Objects;

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
    public AddressDB(String streetAddress, int postalCode, String city) {
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city = city;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDB addressDB = (AddressDB) o;
        return postalCode == addressDB.postalCode &&
                Objects.equals(streetAddress, addressDB.streetAddress);
    }

    public Address toAddress()
    {
        return new Address(streetAddress, postalCode, city);
    }
}