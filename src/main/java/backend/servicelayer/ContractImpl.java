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
    public Double calculateFee(BookingCriteria bookingCriteria) throws InvalidInputException {
        return null;
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
