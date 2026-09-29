public class MultiFactorVIPRoutine extends AccessVerificationRoutine {

    public MultiFactorVIPRoutine(LockMechanism lockMechanism) {
        super(lockMechanism);
    }

    @Override
    public void verifyAndAccess(String userId, String credentialData) throws DoorControlException {
        System.out.println("\n--- [VIP Routine] Verifying user " + userId + " ---");

        if (credentialData == null || !credentialData.contains("BIOMETRIC_OK")) {
            System.out.println("[VIP Routine] Access DENIED: Invalid biometric credentials.");
            return;
        }

        System.out.println("[VIP Routine] Biometric verification PASSED for " + userId);

        lockMechanism.unlockDoor(userId, 10);
    }
}