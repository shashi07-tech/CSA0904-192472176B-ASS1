abstract class BaseVehicle {
    private final String regNo;
    protected BaseVehicle(String regNo) {
        this.regNo = regNo;
        System.out.println("1. BaseVehicle constructor executed for " + regNo);
    }

    public String getRegNo() {
        return regNo;
    }

    protected abstract double ratePerHour();
}

class StandardCar extends BaseVehicle {
    public StandardCar(String regNo) {
        super(regNo);
        System.out.println("2. StandardCar constructor executed");
    }

    @Override
    protected double ratePerHour() {
        return 30.0;
    }
}

class ElectricCarModel extends StandardCar {
    public ElectricCarModel(String regNo) {
        super(regNo);
        System.out.println("3. ElectricCarModel constructor executed");
    }

    public double chargingFee(int kWh) {
        return kWh * 12.0;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Creating ElectricCarModel object:");
        ElectricCarModel ev = new ElectricCarModel("TN03C3");
        System.out.println("RegNo: " + ev.getRegNo() + " | Hourly Rate: Rs. " + ev.ratePerHour()
                + " | 10 kWh Charge: Rs. " + ev.chargingFee(10));
    }
}
