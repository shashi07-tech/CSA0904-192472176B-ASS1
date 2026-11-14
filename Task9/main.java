interface Chargeable {
    double chargingFee(int kWh);

    default double chargingFeeWithTax(int kWh) {
        return chargingFee(kWh) * 1.18;
    }

    static boolean isValidReading(int kWh) {
        return kWh >= 0;
    }
}

interface Payable {
    double computeNetPayable(double grossAmount);
}

abstract class AbstractVehicle {
    private final String regNo;
    protected AbstractVehicle(String regNo) { this.regNo = regNo; }
    public String getRegNo() { return regNo; }
    public abstract double calculateParkingFee(int minutes);
}

class CampusEV extends AbstractVehicle implements Chargeable, Payable {
    private final boolean hasMonthlyPass;

    public CampusEV(String regNo, boolean hasMonthlyPass) {
        super(regNo);
        this.hasMonthlyPass = hasMonthlyPass;
    }

    @Override
    public double calculateParkingFee(int minutes) {
        return ((minutes + 59) / 60) * 30.0;
    }

    @Override
    public double chargingFee(int kWh) {
        return Chargeable.isValidReading(kWh) ? kWh * 12.0 : 0.0;
    }

    @Override
    public double computeNetPayable(double grossAmount) {
        return hasMonthlyPass ? 0.0 : grossAmount;
    }
}

public class Main {
    public static void main(String[] args) {
        CampusEV ev = new CampusEV("TN09EV9999", true);
        double parking = ev.computeNetPayable(ev.calculateParkingFee(120));
        double chargingWithGst = ev.chargingFeeWithTax(10);
        System.out.println("Vehicle: " + ev.getRegNo()
                + " | Net Parking (Pass): Rs. " + parking
                + " | EV Charging + 18% GST: Rs. " + chargingWithGst);
    }
}
