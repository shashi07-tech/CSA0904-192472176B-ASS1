interface ParkingPass {
    boolean coversStandardParking();
}

class OneDayPass implements ParkingPass {
    @Override
    public boolean coversStandardParking() {
        return true;
    }
}

class NoPass implements ParkingPass {
    @Override
    public boolean coversStandardParking() {
        return false;
    }
}

abstract class AdaptedVehicle {
    private final String regNo;
    protected AdaptedVehicle(String regNo) { this.regNo = regNo; }
    public String getRegNo() { return regNo; }
    protected abstract double ratePerHour();

    public double calculateFee(int minutes, int entryHour24) {
        if (minutes <= 0) return 0.0;
        return ((minutes + 59) / 60) * ratePerHour();
    }
}

class AdaptedCar extends AdaptedVehicle {
    public AdaptedCar(String regNo) { super(regNo); }
    @Override protected double ratePerHour() { return 30.0; }
}

// New Category 1: E-Bike (Flat Fee Rs. 15)
class EBike extends AdaptedVehicle {
    public EBike(String regNo) { super(regNo); }
    @Override protected double ratePerHour() { return 15.0; }

    @Override
    public double calculateFee(int minutes, int entryHour24) {
        return (minutes <= 0) ? 0.0 : 15.0;
    }
}

// New Category 2: Delivery Van (Double Car rate between 9 AM and 5 PM)
class DeliveryVan extends AdaptedCar {
    public DeliveryVan(String regNo) { super(regNo); }

    @Override
    public double calculateFee(int minutes, int entryHour24) {
        double baseFee = super.calculateFee(minutes, entryHour24);
        boolean isPeak = (entryHour24 >= 9 && entryHour24 < 17);
        return isPeak ? baseFee * 2.0 : baseFee;
    }
}

public class Main {
    public static double computeExitBill(AdaptedVehicle v, ParkingPass pass, int minutes, int entryHour) {
        if (pass.coversStandardParking()) return 0.0;
        return v.calculateFee(minutes, entryHour);
    }

    public static void main(String[] args) {
        // 5 irregular floors using jagged array
        String[][] fiveFloors = {
            new String[10], new String[15], new String[20], new String[25], new String[30]
        };
        System.out.println("5-Floor Facility Initialized. Floor 4 slots: " + fiveFloors[4].length);

        AdaptedVehicle ebike = new EBike("TN07EB101");
        AdaptedVehicle van = new DeliveryVan("TN08DV202");

        System.out.println("EBike (240 mins, NoPass): Rs. "
                + computeExitBill(ebike, new NoPass(), 240, 10));
        System.out.println("DeliveryVan (120 mins @ 11 AM Peak, NoPass): Rs. "
                + computeExitBill(van, new NoPass(), 120, 11));
        System.out.println("DeliveryVan (120 mins @ 7 PM Off-Peak, NoPass): Rs. "
                + computeExitBill(van, new NoPass(), 120, 19));
        System.out.println("DeliveryVan (120 mins @ 11 AM, Visitor OneDayPass): Rs. "
                + computeExitBill(van, new OneDayPass(), 120, 11));
    }
}
