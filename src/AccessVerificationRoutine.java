public abstract class AccessVerificationRoutine {
    protected final LockMechanism lockMechanism;

    public AccessVerificationRoutine(LockMechanism lockMechanism) {
        this.lockMechanism = lockMechanism;
    }

    public abstract void verifyAndAccess(String userId, String credentialData) throws DoorControlException;
}