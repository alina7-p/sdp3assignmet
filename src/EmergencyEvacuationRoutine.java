public class EmergencyEvacuationRoutine extends AccessVerificationRoutine {

    public EmergencyEvacuationRoutine(LockMechanism lockMechanism) {
        super(lockMechanism);
    }

    @Override
    public void verifyAndAccess(String userId, String credentialData) throws DoorControlException {
        System.out.println("\n--- [EMERGENCY EVACUATION ROUTINE] ---");
        System.out.println("[Emergency] Triggering mandatory gate release for: " + userId);

        lockMechanism.unlockDoor(userId, 3600);
    }
}