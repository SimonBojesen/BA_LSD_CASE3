package booking.datalayer.entity;

import booking.entity.Employee;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee")
public class EmployeeDB {
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
    private String username;
    private transient String password;

    public EmployeeDB(String name, AddressDB address, String email, Date date, int socialSecurityNumber, boolean active, String username, String password) {
        this.name = name;
        this.addressDB = address;
        this.email = email;
        this.dateOfBirth = date;
        this.socialSecurityNumber = socialSecurityNumber;
        this.active = active;
        this.username = username;
        this.password = password;
    }

    public EmployeeDB() {
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Employee toEmployee() {
        return new Employee(this.name,this.addressDB.toAddress(),this.email,this.dateOfBirth,this.socialSecurityNumber,this.active,this.username,this.password);
    }
}
