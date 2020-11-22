package booking;

import booking.datalayer.constants.Place;
import booking.datalayer.dao.*;
import booking.datalayer.entity.*;
import booking.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Test {
    private static AddressRepository addressRepository;
    private static HotelRepository hotelRepository;
    private static AirportRepository airportRepository;
    private static EmployeeRepository employeeRepository;
    private static DriverRepository driverRepository;
    private static CarRepository carRepository;
    private static BookingRepository bookingRepository;

    @Autowired
    public Test(AddressRepository addressRepository, HotelRepository hotelRepository, AirportRepository airportRepository, EmployeeRepository employeeRepository, DriverRepository driverRepository, CarRepository carRepository, BookingRepository bookingRepository) {
        this.addressRepository = addressRepository;
        this.hotelRepository = hotelRepository;
        this.airportRepository = airportRepository;
        this.employeeRepository = employeeRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
    }

    public static void insertIntoDB(){
        Address address = new Address("testvej", 1111, "testby");
        Address address2 = new Address("testvej2", 11112, "testby2");
        AddressDB sampleAddress = new AddressDB(address);
        AddressDB sampleAddress2 = new AddressDB(address2);
        sampleAddress = addressRepository.save(sampleAddress);
        sampleAddress2 = addressRepository.save(sampleAddress2);
        Hotel hotel = new Hotel("testHotel", address, true, 1, Rating.FIVE);
        HotelDB sampleHotelDB = new HotelDB(hotel.getName(), sampleAddress, hotel.isActive(), hotel.getRating());
        hotelRepository.save(sampleHotelDB);
        Airport airport = new Airport("Københavns Lufthavn", address2, true,  "123nrk");
        AirportDB sampleAirportDB = new AirportDB(airport.getIATA(), airport.getName(), sampleAddress2, airport.isActive());
        airportRepository.save(sampleAirportDB);
        Driver driver = new Driver("simon", address, "simon@simonsen.dk", new Date(), 1234, true, 1232344L);
        DriverDB sampleDriver = new DriverDB(driver.getName(), sampleAddress, driver.getEmail(), driver.getDateOfBirth(), driver.getSocialSecurityNumber(), driver.isActive(), driver.getLicenseNo());
        Employee employee = new Employee("martin", address2, "martin@martinson.dk", new Date(), 1234, true, "demo", "demo");
        EmployeeDB sampleEmployee = new EmployeeDB(employee.getName(), sampleAddress2, employee.getEmail(), employee.getDateOfBirth(), employee.getSocialSecurityNumber(), employee.isActive(), employee.getUsername(), employee.getPassword());
        driverRepository.save(sampleDriver);
        employeeRepository.save(sampleEmployee);
        CarDB sampleCar = new CarDB(new Car("", "", Type.B, 200.0, 2, false), Place.AIRPORT, sampleAddress2);
        CarDB sampleCar3 = new CarDB(new Car("", "", Type.B, 200.0, 2, false), Place.NONE, null);
        CarDB sampleCar2 = new CarDB(new Car("", "", Type.A, 400.0, 4, true), Place.HOTEL, sampleAddress);
        carRepository.save(sampleCar);
        carRepository.save(sampleCar3);
        carRepository.save(sampleCar2);
    }
}
