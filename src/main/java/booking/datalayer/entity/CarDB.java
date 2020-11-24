package booking.datalayer.entity;



import booking.entity.Car;

import booking.datalayer.constants.Place;

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
    private transient boolean active;
    private Place place;
    @OneToOne
    private AddressDB station;
    /*@OneToOne
    private HotelDB hotelDB;
    @OneToOne
    private AirportDB airportDB;*/

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

    public String getVin() {
        return vin;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Type getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public boolean isActive() {
        return active;
    }

    public Place getPlace() {
        return place;
    }

    public AddressDB getStation() {
        return station;
    }

    public Car toCar()
    {
        Car car = new Car(this.vin, this.licensePlate, this.type, this.price, this.noOfSeats, this.active);
        return car;
    }
}
