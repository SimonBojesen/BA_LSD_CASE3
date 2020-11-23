package backend.servicelayer;

import backend.datalayer.dao.AirportRepository;
import backend.datalayer.dao.BookingRepository;
import backend.datalayer.dao.CarRepository;
import backend.datalayer.dao.HotelRepository;
import backend.datalayer.entity.AddressDB;
import backend.datalayer.entity.AirportDB;
import backend.datalayer.entity.BookingDB;
import backend.datalayer.entity.HotelDB;
import booking.dto.*;
import booking.entity.Place;
import booking.eto.InvalidInputException;
import booking.eto.NotFoundException;
import booking.eto.PersistanceFailedException;
import booking.eto.UnavailableException;
import org.springframework.data.domain.Example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ContractImpl implements booking.Contract{
    CarRepository carRepository;
    BookingRepository bookingRepository;
    AirportRepository airportRepository;
    HotelRepository hotelRepository;

    public ContractImpl(CarRepository carRepository,
                        BookingRepository bookingRepository,
                        AirportRepository airportRepository,
                        HotelRepository hotelRepository) {
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
        this.airportRepository = airportRepository;
        this.hotelRepository = hotelRepository;
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
        BookingDB bookingDB = bookingRepository.findBooking((long) bookingIdentifier.getId()); // vi skal have ryddet casten væk.

        Place pickupPlace = CreatePlaceFrom(bookingDB.getPickUpPlace(), bookingDB.getCar().getPlace());
        Place deliveryPlace = CreatePlaceFrom(bookingDB.getDeliveryPlace(), bookingDB.getCar().getPlace());

        CarSummary carSummary = new CarSummary(bookingDB.getCar().toCar(), pickupPlace);
        DriverDetails driverDetails = new DriverDetails(bookingDB.getDrivers().toDriver(), bookingDB.getDrivers().getLicenseNo());
        LocalDateTime pickupDate = localDateTimeFrom(bookingDB.getPickUpDate());
        LocalDateTime deliveryDate = localDateTimeFrom(bookingDB.getDeliveryDate());

        BookingCriteria bookingCriteria = new BookingCriteria(pickupPlace, deliveryPlace, pickupDate, deliveryDate);
        BookingDetails bookingDetails = new BookingDetails(bookingDB.getId().intValue(), carSummary, driverDetails, bookingCriteria, bookingDB.getPrice() + bookingDB.getExtraFee());

        return bookingDetails;
    }

    private LocalDateTime localDateTimeFrom(Date date)
    {
        return new java.sql.Timestamp(
                date.getTime()).toLocalDateTime();
    }

    private Place CreatePlaceFrom(AddressDB addressDB, backend.datalayer.constants.Place placeType)
    {
        String name = "";
        boolean active = false;

        switch(placeType)
        {
            case AIRPORT:
                AirportDB airportDB = findAirportByAddress(addressDB);
                name = airportDB.getName();
                active = airportDB.isActive();
                break;
            case HOTEL:
                HotelDB hotelDB = findHotelByAddress(addressDB);
                name = hotelDB.getName();
                active = hotelDB.isActive();
                break;
            default:
                break;
        }

        return new Place(name, addressDB.toAddress(), active);
    }

    private AirportDB findAirportByAddress(AddressDB addressDB)
    {
        AirportDB airportDB = new AirportDB();
        airportDB.setAddress(addressDB);
        Example<AirportDB> example = Example.of(airportDB);
        return airportRepository.findOne(example).get();
    }

    private HotelDB findHotelByAddress(AddressDB addressDB)
    {
        HotelDB hotelDB = new HotelDB();
        hotelDB.setAddress(addressDB);
        Example<HotelDB> example = Example.of(hotelDB);
        return hotelRepository.findOne(example).get();
    }

}
