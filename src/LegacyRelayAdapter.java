public class LegacyRelayAdapter implements LockMechanism {

    private final LegacyRS485RelayBoard legacyBoard;
    private final int defaultChannel;

    public LegacyRelayAdapter(LegacyRS485RelayBoard legacyBoard, int defaultChannel) {
        this.legacyBoard = legacyBoard;
        this.defaultChannel = defaultChannel;
    }

    @Override
    public void unlockDoor(String userId, int durationSeconds) throws DoorControlException {
        if (durationSeconds <= 0) {
            throw new DoorControlException("Invalid unlock duration: " + durationSeconds);
        }

        long pulseMs = durationSeconds * 1000L;

        String unlockHexCmd = "0xA1";

        try {
            legacyBoard.sendRelayCommand(defaultChannel, unlockHexCmd, pulseMs);
        } catch (SerialPortCommunicationException e) {
            throw new DoorControlException("Failed to operate legacy RS485 door lock", e);
        }
    }
}
