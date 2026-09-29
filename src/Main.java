public class Main {
    public static void main(String[] args) {
        System.out.println("=== BIOMETRIC ACCESS CONTROL SYSTEM DEMO ===");

        try {
            System.out.println("\n--- SCENARIO 1: VIP Access via RFID Turnstile ---");
            LockMechanism rfidLock = LockFactory.createLock("RFID");
            AccessVerificationRoutine vipRoutine1 = new MultiFactorVIPRoutine(rfidLock);
            vipRoutine1.verifyAndAccess("USER_ALINA_01", "BIOMETRIC_OK_PIN_1234");

            System.out.println("\n--- SCENARIO 2: Emergency Evacuation via Smart Maglock ---");
            LockMechanism maglock = LockFactory.createLock("MAGLOCK");
            AccessVerificationRoutine emergencyRoutine = new EmergencyEvacuationRoutine(maglock);
            emergencyRoutine.verifyAndAccess("ALL_USERS", "FIRE_ALARM");

            System.out.println("\n--- SCENARIO 3: VIP Access via Legacy RS485 Board (Adapter) ---");
            LockMechanism legacyAdapter = LockFactory.createLock("LEGACY");
            AccessVerificationRoutine vipRoutine2 = new MultiFactorVIPRoutine(legacyAdapter);
            vipRoutine2.verifyAndAccess("USER_ALINA_02", "BIOMETRIC_OK_PIN_5678");

            System.out.println("\n--- SCENARIO 4: Legacy Hardware Failure Handling ---");
            LegacyRS485RelayBoard failingBoard = new LegacyRS485RelayBoard();
            failingBoard.setIoErrorFlag(true); // Имитируем физическую поломку платы

            LockMechanism failingAdapter = new LegacyRelayAdapter(failingBoard, 1);
            AccessVerificationRoutine failingRoutine = new MultiFactorVIPRoutine(failingAdapter);

            System.out.println("Attempting access on broken hardware...");
            failingRoutine.verifyAndAccess("USER_ALINA_03", "BIOMETRIC_OK");

        } catch (DoorControlException e) {
            System.out.println("\n[SYSTEM ALERT] Caught unified DoorControlException: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("[SYSTEM ALERT] Underlying cause: " + e.getCause().getClass().getName()
                        + ": " + e.getCause().getMessage());
            }
        }
    }
}