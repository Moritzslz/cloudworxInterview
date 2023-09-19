import cloud.worx.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    void testGetStateFromZipCode() {
        assertEquals("SACHSEN", Main.getStateFromZipCode("0XXXX"));
        assertEquals("BRANDENBURG", Main.getStateFromZipCode("1XXXX"));
        assertEquals("SCHLESWIG_HOLSTEIN", Main.getStateFromZipCode("2XXXX"));
        assertEquals("NIEDERSACHSEN", Main.getStateFromZipCode("3XXXX"));
        assertEquals("NIEDERSACHSEN", Main.getStateFromZipCode("4XXXX"));
        assertEquals("NORDRHEIN_WESTFALEN", Main.getStateFromZipCode("5XXXX"));
        assertEquals("HESSEN", Main.getStateFromZipCode("6XXXX"));
        assertEquals("BADEN_WÜRTTEMBERG", Main.getStateFromZipCode("7XXXX"));
        assertEquals("BAYERN", Main.getStateFromZipCode("8XXXX"));
        assertEquals("THÜRINGEN", Main.getStateFromZipCode("9XXXX"));
        assertEquals("BERLIN", Main.getStateFromZipCode("AXXXX"));
    }

    @Test
    void testCheckBlockedPLZ() {
        // Create test data
        List<GesperrtePLZ> gesperrtePLZ =  new ArrayList<>();
        for (int i = 86150; i <= 86633; i+= 100) {
            GesperrtePLZ plz= new GesperrtePLZ("" + i, "" + (i + 99));
            gesperrtePLZ.add(plz);
        }

        // valid
        Firma firma1 = new Firma(new Id(), gesperrtePLZ);
        Person person1 = new Person("81673");
        Anfrage anfrage1 = new Anfrage(new Id(), firma1, person1);

        // valid
        Firma firma2 = new Firma(new Id(), gesperrtePLZ);
        Person person2 = new Person("91111");
        Anfrage anfrage2 = new Anfrage(new Id(), firma2, person2);

        // blocked
        Firma firma3 = new Firma(new Id(), gesperrtePLZ);
        Person person3 = new Person("86350");
        Anfrage anfrage3 = new Anfrage(new Id(), firma3, person3);

        List<Anfrage> anfragen1 = Arrays.asList(anfrage1, anfrage2);
        List<Anfrage> anfragen2 = Arrays.asList(anfrage1, anfrage2, anfrage3);

        Map<Id, Anfrage> oldAnfragen = new HashMap<>();
        oldAnfragen.put(anfrage1.Id, anfrage1);
        oldAnfragen.put(anfrage2.Id, anfrage2);

        // False the request is not blocked
        boolean result1 = Main.checkBlockedPLZ(anfragen1, oldAnfragen, true);
        assertFalse(result1);

        // True the request is blocked
        boolean result2 = Main.checkBlockedPLZ(anfragen2, oldAnfragen, true);
        assertTrue(result2);
    }
}
