import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Vector;

public class Main {
    private static Vector<Booking> bookingsCollection;
    private File bookingsFile;

    public Main(Vector<Booking> bookingsCollection) {
        this.bookingsCollection = bookingsCollection;
        loadBookingsFile();
    }

    public static void main(String[] args) {
        BookingDAO dao = new BookingDAO();

        // Insert all bookings
        for (Booking booking : bookingsCollection) {
            dao.insertBooking(booking);
        }

        // Update price of a booking
        try {
            dao.updateBookingPrice("4", 700);
            System.out.println("SUCCESS: Booking price update completed.");
        } catch (Exception e) {
            System.out.println("ERROR: Price update failed.");
        }

        // Delete certain booking
        try {
            dao.deleteBooking("5");
            System.out.println("SUCCESS: Booking deletion completed.");
        } catch (Exception e) {
            System.out.println("ERROR: Booking deletion failed.");
        }

        // Show all bookings
         for (Booking booking : dao.getBookings()) {
             booking.printBooking();
         }

    }

    public void loadBookingsFile() {
        bookingsFile = new File("src/bookings.xml");
        parseBookingsFile();
    }

    public void parseBookingsFile() {
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLSaxHandler SAXHandler = new XMLSaxHandler()   ;
            parser.parse(bookingsFile, SAXHandler);

            this.bookingsCollection = SAXHandler.getBookingsCollection(); // The bookings collection is retrieved after being fully parsed
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
