package backend.datalayer.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private Address address;
    private String email;
    private Date dateOfBirth;
    private int socialSecurityNumber;
    private boolean active;
    private long licenseNo;

    public Driver(booking.entity.Driver driver) {
        this.name = driver.getName();
        this.address = new Address(driver.getAddress());
        this.email = driver.getEmail();
        this.dateOfBirth = driver.getDateOfBirth();
        this.socialSecurityNumber = driver.getSocialSecurityNumber();
        this.active = driver.isActive();
        this.licenseNo = driver.getLicenseNo();
    }

    public Driver() {

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

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public int getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public boolean isActive() {
        return active;
    }

    public long getLicenseNo() {
        return licenseNo;
    }
}
