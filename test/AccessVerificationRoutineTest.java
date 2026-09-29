import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class AccessVerificationRoutineTest {

    @Mock
    private LockMechanism lockMechanismMock;

    @Test
    void testVIPRoutine_Success() throws DoorControlException {
        AccessVerificationRoutine routine = new MultiFactorVIPRoutine(lockMechanismMock);
        routine.verifyAndAccess("USER_01", "BIOMETRIC_OK");

        verify(lockMechanismMock).unlockDoor("USER_01", 10);
    }

    @Test
    void testVIPRoutine_DeniedOnInvalidCredentials() throws DoorControlException {
        AccessVerificationRoutine routine = new MultiFactorVIPRoutine(lockMechanismMock);
        routine.verifyAndAccess("USER_01", "INVALID_CREDENTIALS");

        verifyNoInteractions(lockMechanismMock);
    }

    @Test
    void testEmergencyRoutine_Success() throws DoorControlException {
        AccessVerificationRoutine routine = new EmergencyEvacuationRoutine(lockMechanismMock);
        routine.verifyAndAccess("ALL_USERS", "FIRE_ALARM");

        verify(lockMechanismMock).unlockDoor("ALL_USERS", 3600);
    }
}