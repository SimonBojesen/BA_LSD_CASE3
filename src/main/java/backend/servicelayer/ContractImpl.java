package backend.servicelayer;

import booking.dto.*;
import booking.eto.InvalidInputException;
import booking.eto.NotFoundException;
import booking.eto.PersistanceFailedException;
import booking.eto.UnavailableException;

import java.util.Collection;

public class ContractImpl implements booking.Contract{

    public Collection<CarSummary> listAvailableCars(BookingCriteria bookingCriteria) throws NotFoundException, InvalidInputException {
        return null;
    }

    public Double calculateFee(BookingCriteria bookingCriteria) throws InvalidInputException {
        return null;
    }

    public BookingDetails createBooking(BookingCriteria bookingCriteria, Double aDouble, DriverDetails driverDetails) throws InvalidInputException {
        return null;
    }

    public BookingDetails saveBooking(BookingDetails bookingDetails) throws PersistanceFailedException, UnavailableException {
        return null;
    }

    public boolean cancelBooking(BookingIdentifier bookingIdentifier) throws PersistanceFailedException, NotFoundException, UnavailableException, InvalidInputException {
        return false;
    }

    public BookingDetails endBooking(BookingIdentifier bookingIdentifier) throws PersistanceFailedException, NotFoundException, UnavailableException, InvalidInputException {
        return null;
    }

    public BookingDetails findBooking(BookingIdentifier bookingIdentifier) throws NotFoundException, InvalidInputException {
        return null;
    }
}
