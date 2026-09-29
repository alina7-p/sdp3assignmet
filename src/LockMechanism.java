public interface LockMechanism {
    void unlockDoor(String userId, int durationSeconds) throws DoorControlException;
}