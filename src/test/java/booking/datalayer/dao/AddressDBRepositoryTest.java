package booking.datalayer.dao;

import booking.datalayer.entity.AddressDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AddressDBRepositoryTest {
    //Class to be tested
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    TestEntityManager entityManager;
    //Dependencies (to be mocked)
    //private EntityManager entityManager;

    //Test data
    private AddressDB sampleAddressDB;
    private AddressDB persistedAddressDB;

    @BeforeEach
    public void setup(){
        sampleAddressDB = new AddressDB("Bumlevej 41", 3000, "Bumleby");
        persistedAddressDB = new AddressDB("testvej 69", 6969, "1234");
        entityManager.persist(persistedAddressDB);
        entityManager.flush();
    }


    @Test
    public void mustCreateNewAddressTest() {
        AddressDB addressDB = addressRepository.save(sampleAddressDB);
        System.out.println(addressDB.getId());
        assertNotNull(addressDB.getId());
    }

    @Test
    public void addressNotPresentInDbTest() {
        assertTrue(addressRepository.findById(1L).isEmpty());
    }

    @Test
    public void mustFindAddressByIDTest(){
        //arrange
        Long expected = persistedAddressDB.getId();
        //act
        AddressDB addressDB = addressRepository.findById(expected).get();
        Long actual = addressDB.getId();
        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void mustDeleteAddressByIDTest(){
        //arrange
        Long expected = persistedAddressDB.getId();
        //act
        AddressDB addressDB = entityManager.find(AddressDB.class, persistedAddressDB.getId());
        addressRepository.delete(addressDB);

        //assert
        assertTrue(addressRepository.findById(1L).isEmpty());
    }

}
