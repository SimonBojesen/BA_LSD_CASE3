package booking;

import booking.datalayer.dao.*;
import booking.datalayer.entity.*;
import booking.servicelayer.util.HelperFunctions;
import booking.dto.*;
import booking.entity.Address;
import booking.eto.InvalidInputException;
import booking.eto.NotFoundException;
import booking.eto.PersistanceFailedException;
import booking.eto.UnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component("ContractImpl")
public class ContractImpl implements booking.Contract {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BookingRepository bookingRepository;

    /*@Autowired
    public ContractImpl(AddressRepository addressRepository, HotelRepository hotelRepository, AirportRepository airportRepository, EmployeeRepository employeeRepository, DriverRepository driverRepository, CarRepository carRepository, BookingRepository bookingRepository) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
    }*/

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
            throw new NullPointerException("pickup place or delivery place must not be null");
        }
    }

    //Simon vil tænke over denne
    public BookingDetails createBooking(BookingCriteria bookingCriteria, Double price, DriverDetails driverDetails, EmployeeDetails employeeDetails, CarSummary carSummary) throws InvalidInputException {
        //Remember null checks
        HelperFunctions.nullCheck(bookingCriteria);
        HelperFunctions.nullCheck(price);
        HelperFunctions.nullCheck(driverDetails);
        HelperFunctions.nullCheck(employeeDetails);
        HelperFunctions.nullCheck(carSummary);

        Double fee = calculateFee(bookingCriteria);

        BookingDetails booking = new BookingDetails(null, carSummary, driverDetails, employeeDetails, bookingCriteria, fee, price);
        return booking;
    }

    //Simon vil tænke over denne
    public BookingDetails saveBooking(BookingDetails bookingDetails) throws PersistanceFailedException, UnavailableException {
        BookingDetails booking = null;
        Address deliveryPlace = bookingDetails.getBookingCriteria().getDeliveryPlace().getAddress();

        Optional<DriverDB> driver = driverRepository.findByLicenseNo(bookingDetails.getDriverDetails().getLicenseNo());
        Optional<CarDB> car = carRepository.findByLicensePlate(bookingDetails.getCar().getCar().getLicensePlate());
        Optional<EmployeeDB> employee = employeeRepository.findBySocialSecurityNumber(bookingDetails.getEmployeeDetails().getSocialSecurityNumber());
        Optional<AddressDB> deliveryStation = addressRepository.findByStreetAddressAndCityAndPostalCode(deliveryPlace.getStreetAddress(), deliveryPlace.getCity(), deliveryPlace.getPostalCode());

        if (car.isPresent() && employee.isPresent() && deliveryStation.isPresent()) {
            if (!driver.isPresent()) {
                AddressDB addressToSave = new AddressDB(bookingDetails.getDriverDetails().getDriver().getAddress());
                addressToSave = addressRepository.save(addressToSave);
                DriverDB driverToSave = new DriverDB(bookingDetails.getDriverDetails().getDriver(), addressToSave);
                driver = Optional.of(driverRepository.save(driverToSave));
            }
            BookingDB bookingToSave = new BookingDB(car.get(), driver.get(), employee.get(), deliveryStation.get(), bookingDetails.getBookingCriteria().getPickUpTime(), bookingDetails.getBookingCriteria().getDeliveryTime(), bookingDetails.getPrice(), bookingDetails.getFee());
            bookingToSave = bookingRepository.save(bookingToSave);
            booking = new BookingDetails(bookingToSave.getId(), bookingDetails.getCar(), bookingDetails.getDriverDetails(), bookingDetails.getEmployeeDetails(), bookingDetails.getBookingCriteria(), bookingDetails.getFee(), bookingDetails.getPrice());
        }

        return booking;
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
