class TicketSession {
    private static int ticketCounter = 1000;
    private static int activeOccupancy = 0;
    private static final String CAMPUS_CODE;

    static {
        CAMPUS_CODE = "SIMATS-MAIN";
        System.out.println("[Static Block] Initialized Parking System for " + CAMPUS_CODE);
    }

    private final String ticketId;
    private final String regNo;

    public TicketSession(String regNo) {
        this.ticketId = CAMPUS_CODE + "-" + (++ticketCounter);
        this.regNo = regNo;
        activeOccupancy++;
    }

    public static int getActiveOccupancy() {
        return activeOccupancy;
    }

    public static String formatDuration(int minutes) {
        return (minutes / 60) + "h " + (minutes % 60) + "m";
    }

    public static void checkout(TicketSession session) {
        if (session != null) {
            activeOccupancy--;
            System.out.println("Checked out: " + session.ticketId + " (" + session.regNo + ")");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TicketSession s1 = new TicketSession("TN01AA1111");
        TicketSession s2 = new TicketSession("TN02BB2222");
        System.out.println("Active Vehicles: " + TicketSession.getActiveOccupancy());
        System.out.println("Formatted Stay (135m): " + TicketSession.formatDuration(135));

        TicketSession.checkout(s1);
        s1 = null; // Object is now unreachable and eligible for Garbage Collection
        System.out.println("Active Vehicles after exit: " + TicketSession.getActiveOccupancy());
    }
}
