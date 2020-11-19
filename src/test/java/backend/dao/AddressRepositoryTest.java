package backend.dao;

import backend.datalayer.dao.AddressRepository;
import backend.datalayer.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AddressRepositoryTest {
    //Class to be tested
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    TestEntityManager entityManager;
    //Dependencies (to be mocked)
    //private EntityManager entityManager;

    //Test data
    private Address sampleAddress;
    private Address persistedAddress;

    @BeforeEach
    public void setup(){
        sampleAddress = new Address("Bumlevej 41", 3000, "Bumleby");
        persistedAddress = new Address("testvej 69", 6969, "1234");
        entityManager.persist(persistedAddress);
        entityManager.flush();
    }


    @Test
    public void mustCreateNewAddressTest() {
        Address address = addressRepository.save(sampleAddress);
        System.out.println(address.getId());
        assertNotNull(address.getId());
    }

    @Test
    public void addressNotPresentInDbTest() {
        assertTrue(addressRepository.findById(1L).isEmpty());
    }

    @Test
    public void mustFindAddressByIDTest(){
        //arrange
        Long expected = persistedAddress.getId();
        //act
        Address address = addressRepository.findById(expected).get();
        Long actual = address.getId();
        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void mustDeleteAddressByIDTest(){
        //arrange
        Long expected = persistedAddress.getId();
        //act
        Address address = entityManager.find(Address.class, persistedAddress.getId());
        addressRepository.delete(address);

        //assert
        assertTrue(addressRepository.findById(1L).isEmpty());
    }

}
