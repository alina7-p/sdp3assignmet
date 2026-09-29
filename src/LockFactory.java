public class LockFactory {

    public static LockMechanism createLock(String lockType) {
        if (lockType == null) {
            throw new IllegalArgumentException("Lock type cannot be null");
        }

        switch (lockType.toUpperCase()) {
            case "RFID":
                return new RfidTurnstileLock();
            case "SMART_MAGLOCK":
            case "MAGLOCK":
                return new SmartMaglock();
            case "LEGACY":
            case "RS485":
                LegacyRS485RelayBoard legacyBoard = new LegacyRS485RelayBoard();
                int defaultChannel = 1;
                return new LegacyRelayAdapter(legacyBoard, defaultChannel);
            default:
                throw new IllegalArgumentException("Unknown lock type: " + lockType);
        }
    }
}