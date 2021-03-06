package booking.servicelayer.util;

import booking.eto.InvalidInputException;
import booking.eto.NotFoundException;

public class HelperFunctions {
    public static void nullCheck(Object o) throws InvalidInputException {
        if(o == null){
            throw new InvalidInputException("Object must not be null");
        }
    }

    private HelperFunctions(){}
}
