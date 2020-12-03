package booking;

import booking.datalayer.dao.*;
import booking.servicelayer.ContractImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIRegistry
{
    public static Registry registry;
    private static AddressRepository addressRepository;
    private static HotelRepository hotelRepository;
    private static AirportRepository airportRepository;
    private static EmployeeRepository employeeRepository;
    private static DriverRepository driverRepository;
    private static CarRepository carRepository;
    private static BookingRepository bookingRepository;

    @Autowired
    public RMIRegistry(AddressRepository addressRepository, HotelRepository hotelRepository, AirportRepository airportRepository, EmployeeRepository employeeRepository, DriverRepository driverRepository, CarRepository carRepository, BookingRepository bookingRepository) throws RemoteException {
        this.addressRepository = addressRepository;
        this.hotelRepository = hotelRepository;
        this.airportRepository = airportRepository;
        this.employeeRepository = employeeRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
    }

    public static void createRegistry() throws Exception
    {
        try
        {
            System.out.println("RMI server localhost starts");
            System.setProperty("java.rmi.server.hostname","207.154.197.222");
            // Create a server registry at default port 1099
            registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry created ");

            // Create engine of remote services, running on the server
            ContractImpl remoteEngine = new ContractImpl(addressRepository,employeeRepository,driverRepository,carRepository,bookingRepository,airportRepository,hotelRepository);
            System.out.println(remoteEngine);
            // Give a name to this engine
            String engineName = "BookingServices";
            System.out.println(engineName);
            // Register the engine by the name, which later will be given to the clients
            Naming.rebind("//localhost/" + engineName, remoteEngine);
            System.out.println("Engine " + engineName + " bound in registry");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Exception:" + e);
        }
    }
}