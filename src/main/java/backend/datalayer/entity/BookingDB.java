package backend.datalayer.entity;

import backend.datalayer.dao.AddressRepository;
import booking.dto.BookingCriteria;
import booking.dto.CarSummary;
import booking.dto.DriverDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class BookingDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private CarDB car;
    @OneToOne
    private DriverDB drivers;
    @OneToOne
    private EmployeeDB employee;
    @OneToOne
    private AddressDB pickUpPlace;
    @OneToOne
    private AddressDB deliveryPlace;
    private Date pickUpDate;
    private Date deliveryDate;
    private Double price;
    private Double extraFee;

    public BookingDB(CarDB car, DriverDB drivers, EmployeeDB employee, AddressDB deliveryPlace, Date pickUpDate, Date deliveryDate, Double price, Double extraFee) {
        this.car = car;
        this.drivers = drivers;
        this.employee = employee;
        this.pickUpPlace = car.getStation();
        this.deliveryPlace = deliveryPlace;
        this.pickUpDate = pickUpDate;
        this.deliveryDate = deliveryDate;
        this.price = price;
        this.extraFee = extraFee;
    }

    public BookingDB() {

    }

    public Long getId() {
        return id;
    }

    public CarDB getCar() {
        return car;
    }

    public DriverDB getDrivers() {
        return drivers;
    }

    public EmployeeDB getEmployee() {
        return employee;
    }

    public AddressDB getPickUpPlace() {
        return pickUpPlace;
    }

    public AddressDB getDeliveryPlace() {
        return deliveryPlace;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Double getPrice() {
        return price;
    }

    public Double getExtraFee() {
        return extraFee;
    }
}
