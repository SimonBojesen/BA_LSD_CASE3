package booking.servicelayer.util;

public class HelperFunctions {
    public static void nullCheck(Object o) {
        if(o == null){
            throw new NullPointerException(o.getClass().getSimpleName() + " must not be null");
        }
    }
}
