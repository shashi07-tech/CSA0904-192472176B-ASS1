class EncapsulatedTicket {
    private final Integer ticketId;
    private int durationMinutes;

    public EncapsulatedTicket(int ticketId, int durationMinutes) {
        this.ticketId = ticketId; // Autoboxed to Integer
        setDurationMinutes(durationMinutes);
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes < 0) {
            throw new IllegalArgumentException("Duration cannot be negative: " + durationMinutes);
        }
        this.durationMinutes = durationMinutes;
    }
}

public class Main {
    public static void main(String[] args) {
        EncapsulatedTicket t1 = new EncapsulatedTicket(1001, 45);
        EncapsulatedTicket t2 = new EncapsulatedTicket(1001, 60);

        // Wrapper comparison demonstration (outside -128..127 Integer cache)
        System.out.println("Using == on Integer 1001: " + (t1.getTicketId() == t2.getTicketId()));
        System.out.println("Using .equals() on Integer 1001: " + t1.getTicketId().equals(t2.getTicketId()));

        try {
            t1.setDurationMinutes(-15);
        } catch (IllegalArgumentException e) {
            System.out.println("Encapsulation Guard Caught: " + e.getMessage());
        }
    }
}
