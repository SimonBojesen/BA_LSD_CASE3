package booking.servicelayer;

import booking.datalayer.dao.*;
import booking.datalayer.entity.*;
import booking.entity.Place;
import booking.servicelayer.util.HelperFunctions;
import booking.dto.*;
import booking.entity.Address;
import booking.eto.InvalidInputException;
import booking.eto.NotFoundException;
import booking.eto.PersistanceFailedException;
import booking.eto.UnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service("ContractImpl")
public class ContractImpl implements booking.Contract {
    private AddressRepository addressRepository;
    private EmployeeRepository employeeRepository;
    private DriverRepository driverRepository;
    private CarRepository carRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public ContractImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository, DriverRepository driverRepository, CarRepository carRepository, BookingRepository bookingRepository) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
    }


    public Collection<CarSummary> listAvailableCars(BookingCriteria bookingCriteria) throws NotFoundException, InvalidInputException {
        //carRepository.findAvailableCars(bookingCriteria);
        List<CarSummary> cars = new ArrayList();
        return cars;
    }

    //Martin vil tænke over denne

    /**
     * @param bookingCriteria
     * @return
     * @throws InvalidInputException
     */
    public Double calculateFee(BookingCriteria bookingCriteria) throws InvalidInputException {
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
            //Put logger
            throw new InvalidInputException("pickup place or delivery place must not be null");
        }
    }

    public BookingDetails createBooking(BookingCriteria bookingCriteria, Double price, DriverDetails driverDetails, EmployeeDetails employeeDetails, CarSummary carSummary) throws InvalidInputException {
        try {
            HelperFunctions.nullCheck(bookingCriteria);
            HelperFunctions.nullCheck(price);
            HelperFunctions.nullCheck(driverDetails);
            HelperFunctions.nullCheck(employeeDetails);
            HelperFunctions.nullCheck(carSummary);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidInputException("We found null values in provided input");
        }

        Double fee = calculateFee(bookingCriteria);
        return new BookingDetails(null, carSummary, driverDetails, employeeDetails, bookingCriteria, fee, price);
    }

    public BookingDetails saveBooking(BookingDetails bookingDetails) throws PersistanceFailedException, UnavailableException {
        BookingDetails booking;
        Address deliveryPlace = bookingDetails.getBookingCriteria().getDeliveryPlace().getAddress();

        Optional<DriverDB> driver = driverRepository.findByLicenseNo(bookingDetails.getDriverDetails().getLicenseNo());
        Optional<CarDB> car = carRepository.findByLicensePlate(bookingDetails.getCar().getCar().getLicensePlate());
        Optional<EmployeeDB> employee = employeeRepository.findBySocialSecurityNumber(bookingDetails.getEmployeeDetails().getSocialSecurityNumber());
        Optional<AddressDB> deliveryStation = addressRepository.findByStreetAddressAndCityAndPostalCode(deliveryPlace.getStreetAddress(), deliveryPlace.getCity(), deliveryPlace.getPostalCode());

        String notfound = " is not found";
        if (!car.isPresent()) {
            throw new UnavailableException("Car" + notfound);
        }
        if (!employee.isPresent()) {
            throw new UnavailableException("Emlployee" + notfound);
        }
        if (!deliveryStation.isPresent()) {
            throw new UnavailableException("Delivery station" + notfound);
        }

        try {
            if (!driver.isPresent()) {
                AddressDB addressToSave = new AddressDB(bookingDetails.getDriverDetails().getDriver().getAddress());
                addressToSave = addressRepository.save(addressToSave);
                DriverDB driverToSave = new DriverDB(bookingDetails.getDriverDetails().getDriver(), addressToSave);
                driver = Optional.of(driverRepository.save(driverToSave));
            }
            BookingDB bookingToSave = new BookingDB(car.get(), driver.get(), employee.get(), deliveryStation.get(), bookingDetails.getBookingCriteria().getPickUpTime(), bookingDetails.getBookingCriteria().getDeliveryTime(), bookingDetails.getPrice(), bookingDetails.getFee());
            bookingToSave = bookingRepository.save(bookingToSave);
            booking = new BookingDetails(bookingToSave.getId(), bookingDetails.getCar(), bookingDetails.getDriverDetails(), bookingDetails.getEmployeeDetails(), bookingDetails.getBookingCriteria(), bookingDetails.getFee(), bookingDetails.getPrice());
        } catch (Exception ex) {
            //should be logged
            ex.printStackTrace();
            throw new PersistanceFailedException("An error happened while saving to DB");
        }

        return booking;
    }

    public boolean cancelBooking(BookingIdentifier bookingIdentifier) throws PersistanceFailedException, NotFoundException, UnavailableException, InvalidInputException {
        BookingDB bookingToCancel = checkBookingExistAndInputNotNull(bookingIdentifier);
        //should be logged
        if (bookingToCancel.getPickUpDate().isBefore(LocalDateTime.now()))
            throw new UnavailableException("The booking can't be cancelled as it already started");
        try {
            bookingRepository.delete(bookingToCancel);
            return true;
        } catch (Exception ex) {
            //should be logged
            throw new PersistanceFailedException("Failed to delete booking");
        }
    }

    public BookingDetails endBooking(BookingIdentifier bookingIdentifier) throws PersistanceFailedException, NotFoundException, UnavailableException, InvalidInputException {
        BookingDB bookingToEnd = checkBookingExistAndInputNotNull(bookingIdentifier);
        if (bookingToEnd.getDeliveryDate().isBefore(LocalDateTime.now())) throw new UnavailableException("Booking is still in progress");
        try {
            bookingToEnd.setEnded(true);
            bookingRepository.save(bookingToEnd);

            return null;
        } catch(Exception ex) {
            //should be logged
            throw new PersistanceFailedException("Failed to update booking");
        }
    }

    //Claus vil kigge på denne :)
    public BookingDetails findBooking(BookingIdentifier bookingIdentifier) throws NotFoundException, InvalidInputException {
        return null;
    }

    private BookingDB checkBookingExistAndInputNotNull(BookingIdentifier bookingIdentifier) throws NotFoundException, InvalidInputException {
        if (bookingIdentifier != null && bookingIdentifier.getId() > 0) {
            try {
                return bookingRepository.findById(bookingIdentifier.getId()).get();
            } catch (Exception ex) {
                //should be logged
                throw new NotFoundException("Failed to find booking");
            }
        } else {
            //should be logged
            throw new InvalidInputException("Given id is either null or 0");
        }
    }

}
