import java.util.*;
class VehicleRecord {
    String regNo;
    String category;
    int entryMinutes;
    boolean hasMonthlyPass;

    VehicleRecord(String regNo, String category, int entryMinutes, boolean hasMonthlyPass) {
        this.regNo = regNo;
        this.category = category;
        this.entryMinutes = entryMinutes;
        this.hasMonthlyPass = hasMonthlyPass;
    }
}

public class Main {
    public static void main(String[] args) {
        List<VehicleRecord> activeVehicles = new ArrayList<>();
        activeVehicles.add(new VehicleRecord("TN01AB1111", "Car", 540, false));
        activeVehicles.add(new VehicleRecord("TN02CD2222", "TwoWheeler", 555, true));
        activeVehicles.add(new VehicleRecord("TN03EF3333", "ElectricCar", 600, false));

        String searchReg = "TN03EF3333";
        for (VehicleRecord v : activeVehicles) {
            if (v.regNo.equals(searchReg)) {
                System.out.println("Found Vehicle: " + v.regNo
                        + " | Category: " + v.category
                        + " | Entry Min: " + v.entryMinutes
                        + " | Pass: " + v.hasMonthlyPass);
            }
        }
    }
}
