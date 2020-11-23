package booking.datalayer.entity;

import booking.entity.Driver;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "driver")
public class DriverDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private AddressDB addressDB;
    private String email;
    private Date dateOfBirth;
    private int socialSecurityNumber;
    private boolean active;
    private long licenseNo;

    public DriverDB(String name, AddressDB address, String email, Date date, int socialSecurityNumber, boolean active, long licenseNo) {
        this.name = name;
        this.addressDB = address;
        this.email = email;
        this.dateOfBirth = date;
        this.socialSecurityNumber = socialSecurityNumber;
        this.active = active;
        this.licenseNo = licenseNo;
    }

    public DriverDB(Driver driver, AddressDB address) {
        this.name = driver.getName();
        this.addressDB = address;
        this.email = driver.getEmail();
        this.dateOfBirth = driver.getDateOfBirth();
        this.socialSecurityNumber = driver.getSocialSecurityNumber();
        this.active = driver.isActive();
        this.licenseNo = driver.getLicenseNo();
    }

    public DriverDB() {

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

    public Driver toDriver()
    {
        return new Driver(name, addressDB.toAddress(), email, dateOfBirth, socialSecurityNumber, active, licenseNo);
    }
}
