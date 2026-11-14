import java.util.*;
abstract class TrackedVehicle {
    private final String regNo;

    protected TrackedVehicle(String regNo) {
        this.regNo = regNo;
    }

    public final String getRegNo() {
        return regNo;
    }

    protected abstract double ratePerHour();

    public final double calculateFee(int minutes) {
        if (minutes <= 0) return 0.0;
        return ((minutes + 59) / 60) * ratePerHour();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + regNo + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrackedVehicle)) return false;
        TrackedVehicle other = (TrackedVehicle) o;
        return regNo.equals(other.regNo);
    }

    @Override
    public int hashCode() {
        return regNo.hashCode();
    }
}

class Bike extends TrackedVehicle {
    Bike(String regNo) { super(regNo); }
    @Override protected double ratePerHour() { return 10.0; }
}

class Sedan extends TrackedVehicle {
    Sedan(String regNo) { super(regNo); }
    @Override protected double ratePerHour() { return 30.0; }
}

public class Main {
    public static void main(String[] args) {
        TrackedVehicle v1 = new Sedan("TN02B2");
        TrackedVehicle v2 = new Bike("TN02B2");

        System.out.println("toString(): " + v1.toString());
        System.out.println("equals() same plate: " + v1.equals(v2));
        System.out.println("hashCode() match: " + (v1.hashCode() == v2.hashCode()));

        Set<TrackedVehicle> activeSet = new HashSet<>();
        activeSet.add(v1);
        System.out.println("Duplicate entry detected in HashSet: " + activeSet.contains(v2));
    }
}
