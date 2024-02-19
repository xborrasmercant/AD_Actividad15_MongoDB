import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Vector;

public class Main {
    private static Vector<Booking> bookingsCollection;
    private static BookingDAO dao = new BookingDAO();

    public static void main(String[] args) {
        loadBookingsFile();

        // CRUD functions
        insertBookings();
        updateBookingPrice("10000", 7000.0);
        deleteBooking("20000");
        printBookings();

    }

    public static boolean bookingExists(String bookingID) {
        for (Booking booking : dao.getBookings()) {
            if (booking.getBookingID().equals(bookingID)) {
                return true;
            }
        }

        return false;
    }
    public static void insertBookings() {
        try {
            System.out.println("[ACTION] Bookings insertion in progress...");
            for (Booking booking : bookingsCollection) {
                dao.insertBooking(booking);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Bookings insertion failed.");
        }
    }

    public static void updateBookingPrice(String bookingID, Double price) {

        if (bookingExists(bookingID)) {
            dao.updateBookingPrice(bookingID, price);
            System.out.println("[SUCCESS] Booking '" + bookingID + "' price has changed to " + price);
        } else {
            System.out.println("[ERROR] Booking '" + bookingID + "' does not exists ");
        }
    }

    public static void deleteBooking(String bookingID) {
        if (bookingExists(bookingID)) {
            dao.deleteBooking(bookingID);
            System.out.println("[SUCCESS] Booking '" + bookingID + "' has been deleted");
        } else {
            System.out.println("[ERROR] Booking '" + bookingID + "' does not exists ");
        }
    }

    public static void printBookings() {
        try {
            System.out.println("[ACTION] Printing bookings...");
            for (Booking booking : dao.getBookings()) {
                booking.printBooking();
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Print of bookings failed.");
        }
    }

    public static void loadBookingsFile() {
        File bookingsFile = new File("src/main/resources/bookings.xml");
        parseBookingsFile(bookingsFile);
    }

    public static void parseBookingsFile(File bookingsFile) {
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLSaxHandler SAXHandler = new XMLSaxHandler()   ;
            parser.parse(bookingsFile, SAXHandler);

            bookingsCollection = SAXHandler.getBookingsCollection(); // The bookings collection is retrieved after being fully parsed
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
