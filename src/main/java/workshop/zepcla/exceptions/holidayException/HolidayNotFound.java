package workshop.zepcla.exceptions.holidayException;

public class HolidayNotFound extends RuntimeException {
    public HolidayNotFound(String message) {
        super("This holiday is not found" + message);
    }
}
