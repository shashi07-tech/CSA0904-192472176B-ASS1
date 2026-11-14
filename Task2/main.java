public class Main {
    public static void main(String[] args) {
        // Jagged 2D array: Floor 0 has 2 slots, Floor 1 has 4 slots
        int[] slotsPerFloor = {2, 4};
        String[][] slots = new String[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            slots[f] = new String[slotsPerFloor[f]];
        }
        slots[0][1] = "TN01AB1111";
        slots[1][3] = "TN02CD2222";

        System.out.println("Floor 0 capacity: " + slots[0].length + ", Slot [0][1]: " + slots[0][1]);
        System.out.println("Floor 1 capacity: " + slots[1].length + ", Slot [1][3]: " + slots[1][3]);

        // Casting risk demonstration for 90 minutes parking at Rs. 30/hr
        int minutes = 90;
        double ratePerHour = 30.0;

        // WRONG: Integer division truncates 90 / 60 to 1 before casting
        double wrongHours = (double) (minutes / 60);
        double wrongFee = wrongHours * ratePerHour;

        // CORRECT: Ceiling integer arithmetic avoids truncation loss
        int billableHours = (minutes <= 0) ? 0 : (minutes + 59) / 60;
        double correctFee = billableHours * ratePerHour;

        System.out.println("Duration: " + minutes + " mins");
        System.out.println("Wrong Fee (Integer Division Cast): Rs. " + wrongFee);
        System.out.println("Correct Fee (Ceiling Arithmetic): Rs. " + correctFee);
    }
}
