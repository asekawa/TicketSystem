package reentrantLockMethods;

public class Ticket {
    private String ticketID;
    private String vendorName;
    private String event;

    public Ticket(String ticketID, String vendorName, String event) {
        this.ticketID = ticketID;
        this.vendorName = vendorName;
        this.event = event;
    }

    public String getTicketID() {
        return ticketID;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getEvent() {
        return event;
    }
}

