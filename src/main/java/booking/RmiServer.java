package booking;

import booking.datalayer.dao.*;
import booking.servicelayer.ContractImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

@Configuration
@SpringBootApplication
public class RmiServer {

    private static AddressRepository addressRepository;
    private static HotelRepository hotelRepository;
    private static AirportRepository airportRepository;
    private static EmployeeRepository employeeRepository;
    private static DriverRepository driverRepository;
    private static CarRepository carRepository;
    private static BookingRepository bookingRepository;

    @Autowired
    public RmiServer(AddressRepository addressRepository, HotelRepository hotelRepository, AirportRepository airportRepository, EmployeeRepository employeeRepository, DriverRepository driverRepository, CarRepository carRepository, BookingRepository bookingRepository) {
        this.addressRepository = addressRepository;
        this.hotelRepository = hotelRepository;
        this.airportRepository = airportRepository;
        this.employeeRepository = employeeRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
    }

    @Primary
    @Bean
    Contract contract() {
        return new ContractImpl(addressRepository,employeeRepository,driverRepository,carRepository,bookingRepository,airportRepository,hotelRepository);
    }


    @Bean
    RmiServiceExporter exporter(ContractImpl implementation) {

        // Expose a service via RMI. Remote obect URL is:
        // rmi://<HOST>:<PORT>/<SERVICE_NAME>
        // 1099 is the default port

        Class<Contract> serviceInterface = Contract.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName("test");
        exporter.setRegistryPort(1099);
        System.out.println(serviceInterface.getSimpleName());
        return exporter;
    }

    public static void main(String[] args) {
        //SpringApplication.run(RmiServer.class, args);
        new SpringApplicationBuilder().sources(RmiServer.class).profiles("server").run(args);
    }

}
