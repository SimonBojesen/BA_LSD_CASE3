package booking;

import booking.datalayer.dao.BookingRepository;
import booking.dto.BookingCriteria;
import booking.entity.Address;
import booking.entity.Place;
import booking.eto.InvalidInputException;
import booking.servicelayer.ContractImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import java.time.LocalDateTime;

@SpringBootApplication
public class RmiClient {
    @Bean RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1009/Contract");
        rmiProxyFactory.setServiceInterface(Contract.class);
        return rmiProxyFactory;
    }

    public static void main(String[] args) throws InvalidInputException {
        Contract service = SpringApplication.run(RmiClient.class, args).getBean(Contract.class);
        var result = service.calculateFee(createBookingCriteria());
        System.out.println(result);
    }

    private static BookingCriteria createBookingCriteria(){
        Address address = new Address("testvej", 1111, "testby");
        Address address2 = new Address("testvej2", 11112, "testby2");
        Place place = new Place("test",address,true);
        var bookingCriteria = new BookingCriteria(place,place, LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        return bookingCriteria;
    }
}
