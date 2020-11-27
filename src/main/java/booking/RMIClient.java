package booking;

import booking.dto.BookingCriteria;
import booking.entity.Address;
import booking.entity.Place;

import java.rmi.Naming;
import java.time.LocalDateTime;

public class RMIClient
{
    public static void main(String args[])throws Exception
    {
        // name =  rmi:// + ServerIP +  /EngineName;
        String remoteEngine = "rmi:https://car-renting-service.herokuapp.com/BookingServices";

        // Create local stub, lookup in the registry searching for the remote engine - the interface with the methods we want to use remotely
        Contract obj = (Contract) Naming.lookup(remoteEngine);

        /*List<Customer> list=obj.getMillionaires();
        for(Customer c:list)
        {
            System.out.println(c.getAccnum()+ " " + c.getName() + " " + c.getAmount());
        }*/

        Address address = new Address("testvej", 1111, "testby");
        Address address2 = new Address("testvej2", 11112, "testby2");
        Place place = new Place("test",address,true);
        Place place2 = new Place("test2", address2, true);
        BookingCriteria bookingCriteria = new BookingCriteria(place,place2, LocalDateTime.now(),LocalDateTime.now().plusDays(1));

        Double fee = obj.calculateFee(bookingCriteria);

        System.out.println(fee);
    }

}