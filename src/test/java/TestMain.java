import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestMain {
    @Test
    void numbersTest(){
        Main main = new Main();
        assertEquals(25, main.test(5));
    }
}
