import java.util.*;
interface EvChargeable {
    double chargingFee(int kWh);
}
abstract class ParkVehicle {
    private final String regNo;
    protected ParkVehicle(String regNo) { this.regNo = regNo; }
    public String getRegNo() { return regNo; }
    protected abstract double ratePerHour();

    public double calculateFee(int minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Negative duration");
        if (minutes == 0) return 0.0;
        return ((minutes + 59) / 60) * ratePerHour();
    }
}

class BikeVehicle extends ParkVehicle {
    BikeVehicle(String regNo) { super(regNo); }
    @Override protected double ratePerHour() { return 10.0; }
}

class CarVehicle extends ParkVehicle {
    CarVehicle(String regNo) { super(regNo); }
    @Override protected double ratePerHour() { return 30.0; }
}

class EvCarVehicle extends CarVehicle implements EvChargeable {
    EvCarVehicle(String regNo) { super(regNo); }
    @Override public double chargingFee(int kWh) { return kWh * 12.0; }
}

class ActiveTicket {
    final ParkVehicle vehicle;
    final boolean hasPass;
    final int floor;
    final int slot;

    ActiveTicket(ParkVehicle vehicle, boolean hasPass, int floor, int slot) {
        this.vehicle = vehicle;
        this.hasPass = hasPass;
        this.floor = floor;
        this.slot = slot;
    }
}

public class Main {
    private final String[][] slots;
    private final Map<String, ActiveTicket> activeTickets = new HashMap<>();
    private double totalCollection = 0.0;

    public Main(int[] floors) {
        slots = new String[floors.length][];
        for (int i = 0; i < floors.length; i++) slots[i] = new String[floors[i]];
    }

    public static ParkVehicle createVehicle(String type, String regNo) {
        switch (type) {
            case "TwoWheeler": return new BikeVehicle(regNo);
            case "Car": return new CarVehicle(regNo);
            case "ElectricCar": return new EvCarVehicle(regNo);
            default: throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }

    public String enter(String type, String regNo, boolean hasPass) {
        if (activeTickets.containsKey(regNo)) {
            return "REJECTED: Duplicate active regNo " + regNo;
        }
        ParkVehicle v = createVehicle(type, regNo);
        for (int f = 0; f < slots.length; f++) {
            for (int s = 0; s < slots[f].length; s++) {
                if (slots[f][s] == null) {
                    slots[f][s] = regNo;
                    activeTickets.put(regNo, new ActiveTicket(v, hasPass, f, s));
                    return "ALLOCATED [" + f + "," + s + "] to " + regNo;
                }
            }
        }
        return "FULL: No free slot for " + regNo;
    }

    public double exit(String regNo, int minutes, int kWh) {
        ActiveTicket t = activeTickets.remove(regNo);
        if (t == null) throw new IllegalArgumentException("Ticket not found: " + regNo);
        slots[t.floor][t.slot] = null;
        double fee = t.hasPass ? 0.0 : t.vehicle.calculateFee(minutes);
        if (t.vehicle instanceof EvChargeable ev) {
            fee += ev.chargingFee(kWh);
        }
        totalCollection += fee;
        return fee;
    }

    public static void main(String[] args) {
        Main lot = new Main(new int[]{2, 1}); // Total capacity = 3 slots
        System.out.println(lot.enter("TwoWheeler", "TN01AA1001", false));
        System.out.println(lot.enter("Car", "TN02BB2002", false));
        System.out.println(lot.enter("ElectricCar", "TN03CC3003", false)); // Last slot
        System.out.println(lot.enter("Car", "TN04DD4004", false));         // Full test
        System.out.println(lot.enter("Car", "TN01AA1001", false));         // Duplicate test

        double f1 = lot.exit("TN01AA1001", 90, 0);   // 2h * 10 = 20.0
        double f2 = lot.exit("TN02BB2002", 60, 0);   // 1h * 30 = 30.0
        double f3 = lot.exit("TN03CC3003", 130, 10); // 3h * 30 + 120 = 210.0

        lot.enter("Car", "TN05EE5005", true);        // Staff pass
        double f4 = lot.exit("TN05EE5005", 240, 0);  // Pass = 0.0

        System.out.println("Fees -> f1:" + f1 + ", f2:" + f2 + ", f3:" + f3 + ", f4:" + f4);
        System.out.println("Total Daily Collection: Rs. " + lot.totalCollection);
    }
}
