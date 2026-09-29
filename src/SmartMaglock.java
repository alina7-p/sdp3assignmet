public class SmartMaglock implements LockMechanism {

    @Override
    public void unlockDoor(String userId, int durationSeconds) throws DoorControlException {
        if (durationSeconds <= 0) {
            throw new DoorControlException("Invalid unlock duration: " + durationSeconds);
        }

        System.out.println("[SmartMaglock] Releasing magnetic lock for user '"
                + userId + "' for " + durationSeconds + " seconds.");
    }
}