package booking.servicelayer;

import booking.datalayer.dao.*;
import booking.datalayer.entity.*;
import booking.entity.Hotel;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;


@Service("ContractImpl")
@RestController
public class ContractImpl extends UnicastRemoteObject implements booking.Contract {
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
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    public ContractImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository, DriverRepository driverRepository, CarRepository carRepository, BookingRepository bookingRepository, AirportRepository airportRepository, HotelRepository hotelRepository) throws RemoteException {
        super();
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
        this.airportRepository = airportRepository;
        this.hotelRepository = hotelRepository;
    }


    @GetMapping("/error")
    public String sayHello()
    {
        return String.format("Hello World!");
    }

    public Collection<CarSummary> listAvailableCars(BookingCriteria bookingCriteria) throws NotFoundException, InvalidInputException {
        HelperFunctions.nullCheck(bookingCriteria);
        HelperFunctions.nullCheck(bookingCriteria.getPickUpPlace());
        HelperFunctions.nullCheck(bookingCriteria.getPickUpTime());
        HelperFunctions.nullCheck(bookingCriteria.getDeliveryTime());
        List<CarSummary> cars = new ArrayList();
        List<CarDB> resultCarDB = carRepository.findAvailableCars(bookingCriteria);
        if (resultCarDB.isEmpty())
            throw new NotFoundException("No cars with the given bookingCriteria was found");
        for (CarDB car : carRepository.findAvailableCars(bookingCriteria)) {
            CarSummary carSummary = new CarSummary(car.toCar(), bookingCriteria.getPickUpPlace());
            cars.add(carSummary);
        }
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

        Optional<DriverDB> driver = driverRepository.findByLicenseNo(bookingDetails.getDriverDetails().getDriver().getLicenseNo());
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
        if (bookingToEnd.getDeliveryDate().isBefore(LocalDateTime.now()))
            throw new UnavailableException("Booking is still in progress");
        try {
            bookingToEnd.setEnded(true);
            bookingRepository.save(bookingToEnd);

            return null;
        } catch (Exception ex) {
            //should be logged
            throw new PersistanceFailedException("Failed to update booking");
        }
    }

    //Claus vil kigge på denne :)
    public BookingDetails findBooking(BookingIdentifier bookingIdentifier) throws NotFoundException, InvalidInputException {
        // is bookingIdentifier valid?
        if (bookingIdentifier == null || bookingIdentifier.getId() == null)
            throw new InvalidInputException("The provided identifier for this booking is invalid.");

        // Does BookingDB exist?
        Optional<BookingDB> bookingDBOptional = bookingRepository.findById(bookingIdentifier.getId());
        BookingDB bookingDB;
        BookingDetails bookingDetails;
        try
        {
            bookingDB = bookingDBOptional.get();

            Place pickupPlace = CreatePlaceFrom(bookingDB.getPickUpPlace(), bookingDB.getCar().getPlace());
            Place deliveryPlace = CreatePlaceFrom(bookingDB.getDeliveryPlace(), bookingDB.getCar().getPlace());

            CarSummary carSummary = new CarSummary(bookingDB.getCar().toCar(), pickupPlace);
            DriverDetails driverDetails = new DriverDetails(bookingDB.getDriver().toDriver());
            EmployeeDetails employeeDetails = new EmployeeDetails(bookingDB.getEmployee().toEmployee());
            LocalDateTime pickupDate = bookingDB.getPickUpDate();
            LocalDateTime deliveryDate = bookingDB.getDeliveryDate();

            BookingCriteria bookingCriteria = new BookingCriteria(pickupPlace, deliveryPlace, pickupDate, deliveryDate);
            bookingDetails = new BookingDetails(bookingDB.getId(), carSummary, driverDetails, employeeDetails, bookingCriteria, bookingDB.getExtraFee(), bookingDB.getPrice());
        }
        catch(Exception e)
        {
            throw new NotFoundException("Details for the booking could not be found.");
        }

        return bookingDetails;
    }

    private Place CreatePlaceFrom(AddressDB addressDB, booking.datalayer.constants.Place placeType) throws NotFoundException {
        String name = "";
        boolean active = false;
        //TODO Clean up this switch. Booking pickUpPlace and car place must be of the same type or the address will not be found
        switch (placeType) {
            case AIRPORT:
                Optional<AirportDB> airportDBOptional = airportRepository.findAirportDBByAddressDB(addressDB);
                if (airportDBOptional.isPresent()) {
                    AirportDB airportDB = airportDBOptional.get();
                    name = airportDB.getName();
                    active = airportDB.isActive();
                } else throw new NotFoundException("No Airport with address was found");
                break;
            case HOTEL:
                Optional<HotelDB> hotelDBOptional = hotelRepository.findHotelDBByAddressDB(addressDB);
                if (hotelDBOptional.isPresent()) {
                    HotelDB hotelDB = hotelDBOptional.get();
                    name = hotelDB.getName();
                    active = hotelDB.isActive();
                } else throw new NotFoundException("No Hotel with address was found");
                break;
            default:
                break;
        }
        return new Place(name, addressDB.toAddress(), active);
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
