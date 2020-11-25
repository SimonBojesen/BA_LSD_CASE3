package booking.datalayer.entity;

import booking.datalayer.constants.Place;
import booking.entity.Car;
import booking.entity.Type;

import javax.persistence.*;

@Entity
@Table(name = "car")
public class CarDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vin;
    private String licensePlate;
    private Type type;
    private double price;
    private int noOfSeats;
    private boolean active;
    private Place place;
    @OneToOne
    private AddressDB station;

    public CarDB(booking.entity.Car car, Place place, AddressDB station) {
        this.vin = car.getVin();
        this.licensePlate = car.getLicensePlate();
        this.type = car.getType();
        this.price = car.getPrice();
        this.noOfSeats = car.getNoOfSeats();
        this.active = car.isActive();
        this.place = place;
        this.station = station;
    }


    public CarDB() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public AddressDB getStation() {
        return station;
    }

    public void setStation(AddressDB station) {
        this.station = station;
    }

    public Car toCar()
    {
        return new Car(this.vin, this.licensePlate, this.type, this.price, this.noOfSeats, this.active);
    }
}
