package cloud.worx;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello cloudworx!");
    }

    public static String getStateFromZipCode(String zipCode) {

        // This method does not provide exact results since the state
        // can not be determined just based on the first char of a
        // zip code in germany. Miss matches occur.
        // This is just for demo purposes - some states are missing.

        char firstDigit = zipCode.charAt(0);

        return switch (firstDigit) {
            case '0' -> States.SACHSEN.name();
            case '1' -> States.BRANDENBURG.name();
            case '2' -> States.SCHLESWIG_HOLSTEIN.name();
            case '3' -> States.NIEDERSACHSEN.name();
            case '4' -> States.NIEDERSACHSEN.name();
            case '5' -> States.NORDRHEIN_WESTFALEN.name();
            case '6' -> States.HESSEN.name();
            case '7' -> States.BADEN_WÜRTTEMBERG.name();
            case '8' -> States.BAYERN.name();
            case '9' -> States.THÜRINGEN.name();
            default -> States.BERLIN.name();
        };
    }

    // First potential mistake is the return type (but that is not a logical mistake)
    // || should be && in the last if statement (logical mistake)
    // Explanation:
    // If the request postal code is smaller (larger) than blockedPostalCodes.Ende and smaller (larger) than blockedPostalCodes.Start
    // the statement is true, but it should not be.
    // Example:
    // Start = 86150, Ende = 86633 => Blocked Interval: [86150, 86151, ..., 86632, 86633]
    // BillingPostalCode = 81673
    // ((81673 >= 86150) || (81673 <= 86633)) => ture
    // But 81673 is not an element of [86150, 86151, ..., 86632, 86633]

    public static boolean checkBlockedPLZ(List<Anfrage> anfragen, Map<Id, Anfrage> oldAnfragen, boolean isInsert) {

        // Removed the db query to increase simplicity
        // => Changed the gesperrtePLZByFirmenIds logic a little

        Map<Id, List<GesperrtePLZ>> gesperrtePLZByFirmenIds =  new HashMap<>();
        for (Anfrage anfrage : anfragen) {
            // State is set according to postal code of requesting person
            anfrage.setState(getStateFromZipCode(anfrage.Person.BillingPostalCode));
            gesperrtePLZByFirmenIds.put(anfrage.Id, anfrage.Firma.gesperrtePLZ);
        }

        for (Anfrage anfrage : anfragen) {
            if (isInsert || anfrage.Firma != oldAnfragen.get(anfrage.Id).Firma) {
                List<GesperrtePLZ> gesperrtePLZ = gesperrtePLZByFirmenIds.get(anfrage.Id);
                for (GesperrtePLZ blockedPostalCodes : gesperrtePLZ) {
                    if (Integer.parseInt(anfrage.Person.BillingPostalCode) >= Integer.parseInt(blockedPostalCodes.Start) && // Originally a ||
                            Integer.parseInt(anfrage.Person.BillingPostalCode) <= Integer.parseInt(blockedPostalCodes.Ende)) {
                        anfrage.blocked = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

