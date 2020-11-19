package backend.datalayer.entity;

import booking.entity.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vin;
    private String licensePlate;
    private Type type;
    private double price;
    private int noOfSeats;
    private transient boolean active;

    public Car(booking.entity.Car car) {
        this.vin = car.getVin();
        this.licensePlate = car.getLicensePlate();
        this.type = car.getType();
        this.price = car.getPrice();
        this.noOfSeats = car.getNoOfSeats();
        this.active = car.isActive();
    }

    public Car() {

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
}
