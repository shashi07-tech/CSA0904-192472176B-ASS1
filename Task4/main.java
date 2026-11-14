class ParkingTicket {
    private static int ticketCounter = 1000;
    private final String ticketId;
    private final String regNo;
    private final String userRole;
    private final int floor;
    private final int slot;
    private final int entryMinutes;

    // Overloaded Constructor 1: Defaults entryMinutes to 0 via this() chaining
    public ParkingTicket(String regNo, String userRole, int floor, int slot) {
        this(regNo, userRole, floor, slot, 0);
    }

    // Overloaded Constructor 2: Full parameterised constructor
    public ParkingTicket(String regNo, String userRole, int floor, int slot, int entryMinutes) {
        if (regNo == null || userRole == null) {
            throw new IllegalArgumentException("Registration and Role must not be null");
        }
        this.ticketId = "TKT-" + (++ticketCounter);
        this.regNo = regNo;
        this.userRole = userRole;
        this.floor = floor;
        this.slot = slot;
        this.entryMinutes = entryMinutes;
    }

    // Copy Constructor: Re-issues a duplicate lost ticket preserving original entry time
    public ParkingTicket(ParkingTicket original) {
        this.ticketId = original.ticketId + "-DUP";
        this.regNo = original.regNo;
        this.userRole = original.userRole;
        this.floor = original.floor;
        this.slot = original.slot;
        this.entryMinutes = original.entryMinutes;
    }

    public void display() {
        System.out.println(ticketId + " | Vehicle: " + regNo + " | Role: " + userRole
                + " | Slot: [" + floor + "," + slot + "] | EntryMin: " + entryMinutes);
    }
}

public class Main {
    public static void main(String[] args) {
        ParkingTicket t1 = new ParkingTicket("TN01AB1111", "Student", 0, 0);
        ParkingTicket t2 = new ParkingTicket("TN02CD2222", "Visitor", 0, 1, 540);
        ParkingTicket t2Duplicate = new ParkingTicket(t2);

        t1.display();
        t2.display();
        t2Duplicate.display();
    }
}
