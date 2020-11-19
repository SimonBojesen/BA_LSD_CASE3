package backend;

import backend.datalayer.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Test {
    private static AddressRepository addressRepository;
    private static HotelRepository hotelRepository;
    private static AirportRepository airportRepository;
    private static EmployeeRepository employeeRepository;
    private static DriverRepository driverRepository;
    private static CarRepository carRepository;

    @Autowired
    public Test(AddressRepository addressRepository, HotelRepository hotelRepository, AirportRepository airportRepository, EmployeeRepository employeeRepository, DriverRepository driverRepository, CarRepository carRepository) {
        this.addressRepository = addressRepository;
        this.hotelRepository = hotelRepository;
        this.airportRepository = airportRepository;
        this.employeeRepository = employeeRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }
}
