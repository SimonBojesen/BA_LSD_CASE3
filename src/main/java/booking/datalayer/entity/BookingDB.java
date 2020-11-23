package booking.datalayer.entity;

import booking.dto.BookingDetails;
import booking.dto.CarSummary;
import booking.entity.Place;
import ch.qos.logback.core.joran.action.IADataForComplexProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BookingDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private CarDB car;
    @OneToOne
    private DriverDB driver;
    @OneToOne
    private EmployeeDB employee;
    @OneToOne
    private AddressDB pickUpPlace;
    @OneToOne
    private AddressDB deliveryPlace;
    private LocalDateTime pickUpDate;
    private LocalDateTime deliveryDate;
    private Double price;
    private Double extraFee;
    private boolean ended;

    public BookingDB(CarDB car, DriverDB driver, EmployeeDB employee, AddressDB deliveryPlace, LocalDateTime pickUpDate, LocalDateTime deliveryDate, Double price, Double extraFee) {
        this.car = car;
        this.driver = driver;
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

    /*public BookingDetails convertToBookingDetails(BookingDB bookingToConvert, Place place){
        if (bookingToConvert.getCar().getPlace() == booking.datalayer.constants.Place.NONE) {

        } else if (bookingToConvert.getCar().getPlace() == booking.datalayer.constants.Place.HOTEL) {

        } else {

        }

        Place place = new Place(bookingToConvert.getPickUpPlace();
        CarSummary carSummary = new CarSummary(bookingToConvert.getCar(), place)

        BookingDetails bookingDetails = new BookingDetails(bookingToConvert.getId(),);
    }*/

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public Long getId() {
        return id;
    }

    public CarDB getCar() {
        return car;
    }

    public DriverDB getDriver() {
        return driver;
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

    public LocalDateTime getPickUpDate() {
        return pickUpDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public Double getPrice() {
        return price;
    }

    public Double getExtraFee() {
        return extraFee;
    }
}
