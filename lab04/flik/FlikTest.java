package flik;
import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void testFlik() {
        int a = 150;
        int b = 150;
        assertTrue(Flik.isSameNumber(a,b));

    }
}
