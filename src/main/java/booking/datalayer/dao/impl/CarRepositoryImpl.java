package booking.datalayer.dao.impl;

import booking.datalayer.dao.AddressRepository;
import booking.datalayer.dao.BookingRepository;
import booking.datalayer.dao.CarRepository;
import booking.datalayer.dao.CustomCarRepository;
import booking.datalayer.entity.AddressDB;
import booking.datalayer.entity.BookingDB;
import booking.datalayer.entity.CarDB;
import booking.dto.BookingCriteria;
import booking.dto.CarSummary;
import booking.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CarRepositoryImpl implements CustomCarRepository
{

    @Autowired
    @Lazy
    AddressRepository addressRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<CarDB> findAvailableCars(BookingCriteria bookingCriteria) {
        List<CarDB> result = new ArrayList<>();
        try {
            Address stationAddress = bookingCriteria.getPickUpPlace().getAddress();
            AddressDB station = addressRepository.findByStreetAddressAndCityAndPostalCode(stationAddress.getStreetAddress(), stationAddress.getCity(), stationAddress.getPostalCode()).get();
            /*String query = "SELECT DISTINCT c " +
                           "FROM CarDB c LEFT JOIN BookingDB b ON c = b.car " +
                           "WHERE c.active = TRUE AND c.station = :station " +
                           "AND ((:pickUpDate > b.deliveryDate AND :pickUpDate > b.pickUpDate) "+
                           "OR (:pickUpDate < b.deliveryDate AND :pickUpDate < b.pickUpDate))";*/

            String query = "SELECT DISTINCT c " +
                            "FROM CarDB c LEFT JOIN BookingDB b ON c = b.car " +
                            "WHERE c.active = TRUE AND c.station = :station AND (b.id IS NULL OR " +
                            "((:pickUpDate > b.deliveryDate AND :pickUpDate > b.pickUpDate) " +
                            "OR (:pickUpDate < b.deliveryDate AND :pickUpDate < b.pickUpDate)))";

            Query q = em.createQuery(query);
            q.setParameter("station", station);
            q.setParameter("pickUpDate", bookingCriteria.getPickUpTime());
            result = q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
