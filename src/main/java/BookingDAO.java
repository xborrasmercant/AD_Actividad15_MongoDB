import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;

public class BookingDAO {
    private MongoCollection<Document> collection;
    private MongoDBConnector connector;

    public BookingDAO() {
        connector = new MongoDBConnector();
        collection = connector.getDatabase().getCollection("bookings");
    }

    // CREATE
    public void insertBooking(Booking booking) {

        Document document = new Document();
        document.append("booking_id", booking.getBookingID())
                .append("client_id", booking.getClientID())
                .append("client_name", booking.getClientName())
                .append("agency_id", booking.getAgencyID())
                .append("agency_name", booking.getAgencyName())
                .append("price", booking.getPrice())
                .append("room_type", booking.getRoomType())
                .append("hotel_id", booking.getHotelID())
                .append("hotel_name", booking.getHotelName())
                .append("check_in", booking.getCheckIn())
                .append("room_nights", booking.getRoomNights());

        collection.insertOne(document);
    }

    // READ
    public ArrayList<Booking> getBookings() {

        ArrayList<Booking> bookings = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                bookings.add(Booking.bookingFromDocument(document));
            }
        } finally {
            cursor.close();
        }
        return bookings;
    }

    // CREATE
    public void updateBookingPrice(String bookingID, double newPrice) {

        Document filter = new Document("booking_id", bookingID);
        Document update = new Document("$set", new Document("price", newPrice));
        collection.updateOne(filter, update);
    }

    // DELETE
    public void deleteBooking(String bookingID) {
        Document filter = new Document("booking_id", bookingID);
        collection.deleteOne(filter);
    }

    public Booking findBookingByID(String bookingID) {
        Booking booking = null;
        try {

            collection = connector.getDatabase().getCollection("bookings");
            Document query = new Document("booking_id", bookingID);
            Document document = collection.find(query).first();

            if (document != null) {
                booking = Booking.bookingFromDocument(document);
            }
            else {
                System.out.println("ERROR: Booking with ID (location_number) '" + bookingID + "' does not exists");
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return booking;
    }


    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public MongoDBConnector getConnector() {
        return connector;
    }

    public void setConnector(MongoDBConnector connector) {
        this.connector = connector;
    }
}
