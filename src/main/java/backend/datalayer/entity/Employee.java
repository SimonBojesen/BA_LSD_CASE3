package backend.datalayer.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Employee {
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
    private String username;
    private transient String password;

    public Employee(booking.entity.Employee employee) {
        this.name = employee.getName();
        this.address = new Address(employee.getAddress());
        this.email = employee.getEmail();
        this.dateOfBirth = employee.getDateOfBirth();
        this.socialSecurityNumber = employee.getSocialSecurityNumber();
        this.active = employee.isActive();
        this.username = employee.getUsername();
        this.password = employee.getPassword();
    }

    public Employee() {
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
