package booking;

import booking.datalayer.dao.BookingRepository;
import booking.dto.BookingCriteria;
import booking.entity.Address;
import booking.entity.Place;
import booking.eto.InvalidInputException;
import booking.servicelayer.ContractImpl;
import org.hibernate.annotations.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import java.time.LocalDateTime;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class RmiClient {
    @Bean RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1099/test");
        rmiProxyFactory.setServiceInterface(Contract.class);
        return rmiProxyFactory;
    }

    public static void main(String[] args) throws InvalidInputException {
        var service = new SpringApplicationBuilder().sources(RmiClient.class).profiles("client").web(WebApplicationType.NONE).run(args).getBean(Contract.class);
        var result = service.calculateFee(createBookingCriteria());
        System.out.println("FROM RMI-SERVER: " + result);
        /*Contract service = SpringApplication.run(RmiClient.class, args).getBean(Contract.class);
        var result = service.calculateFee(createBookingCriteria());
        System.out.println(result);*/
    }



    private static BookingCriteria createBookingCriteria(){
        Address address = new Address("testvej", 1111, "testby");
        Address address2 = new Address("testvej2", 11112, "testby2");
        Place place = new Place("test",address,true);
        var bookingCriteria = new BookingCriteria(place,place, LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        return bookingCriteria;
    }
}
