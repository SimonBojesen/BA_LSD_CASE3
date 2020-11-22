package backend.servicelayer;

import backend.datalayer.dao.CarRepository;
import backend.datalayer.dao.impl.CarRepositoryImpl;
import booking.dto.*;
import booking.eto.InvalidInputException;
import booking.eto.NotFoundException;
import booking.eto.PersistanceFailedException;
import booking.eto.UnavailableException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContractImpl implements booking.Contract{
    CarRepository carRepository;

    public ContractImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Collection<CarSummary> listAvailableCars(BookingCriteria bookingCriteria) throws NotFoundException, InvalidInputException {
        carRepository.findAvailableCars(bookingCriteria);
        List<CarSummary> cars = new ArrayList();
        return cars;
    }

    //Martin vil tænke over denne

    /**
     *
     * @param bookingCriteria
     * @return
     * @throws InvalidInputException
     */
    public Double calculateFee(BookingCriteria bookingCriteria) throws InvalidInputException {
        if(bookingCriteria == null){
            throw new NullPointerException("bookingCriteria must not be null");
        }else {
            var delivery = bookingCriteria.getDeliveryPlace();
            var pickup = bookingCriteria.getPickUpPlace();
            if (delivery != null && pickup != null) {
                if (delivery.getAddress().getPostalCode() == pickup.getAddress().getPostalCode()) {
                    if (delivery.getAddress().getCity().equalsIgnoreCase(pickup.getAddress().getCity())) {
                        if (delivery.getAddress().getStreetAddress().equalsIgnoreCase(pickup.getAddress().getStreetAddress())) {
                            return 0.0;
                        } else {
                            return 25.0;
                        }
                    } else {
                        return 50.0;
                    }
                } else {
                    return 100.0;
                }
            } else {
                throw new NullPointerException("pickup place or delivery place must not be null");
            }
        }
    }

    //Simon vil tænke over denne
    public BookingDetails createBooking(BookingCriteria bookingCriteria, Double aDouble, DriverDetails driverDetails) throws InvalidInputException {
        return null;
    }
    //Simon vil tænke over denne
    public BookingDetails saveBooking(BookingDetails bookingDetails) throws PersistanceFailedException, UnavailableException {
        return null;
    }

    public boolean cancelBooking(BookingIdentifier bookingIdentifier) throws PersistanceFailedException, NotFoundException, UnavailableException, InvalidInputException {
        return false;
    }

    public BookingDetails endBooking(BookingIdentifier bookingIdentifier) throws PersistanceFailedException, NotFoundException, UnavailableException, InvalidInputException {
        return null;
    }

    //Claus vil kigge på denne :)
    public BookingDetails findBooking(BookingIdentifier bookingIdentifier) throws NotFoundException, InvalidInputException {
        return null;
    }
}
