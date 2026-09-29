import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LegacyRelayAdapterTest {

    @Test
    void testUnlockDoor_SuccessConversion() throws Exception {
        // Создаем фейковую старую плату (Fake)
        TestableLegacyBoard fakeBoard = new TestableLegacyBoard();
        LegacyRelayAdapter adapter = new LegacyRelayAdapter(fakeBoard, 1);

        adapter.unlockDoor("USER_01", 5);

        assertEquals(1, fakeBoard.lastChannelIndex);
        assertEquals("0xA1", fakeBoard.lastHexCommand);
        assertEquals(5000L, fakeBoard.lastPulseTimeMs);
    }

    @Test
    void testUnlockDoor_ExceptionTranslation() {
        TestableLegacyBoard failingBoard = new TestableLegacyBoard();
        failingBoard.shouldThrowException = true;

        LegacyRelayAdapter adapter = new LegacyRelayAdapter(failingBoard, 1);

        DoorControlException thrown = assertThrows(
                DoorControlException.class,
                () -> adapter.unlockDoor("USER_01", 3)
        );

        assertNotNull(thrown.getCause());
        assertEquals(SerialPortCommunicationException.class, thrown.getCause().getClass());
    }

    private static class TestableLegacyBoard extends LegacyRS485RelayBoard {
        int lastChannelIndex;
        String lastHexCommand;
        long lastPulseTimeMs;
        boolean shouldThrowException = false;

        @Override
        public void sendRelayCommand(int channelIndex, String hexCommand, long pulseTimeMs)
                throws SerialPortCommunicationException {
            if (shouldThrowException) {
                throw new SerialPortCommunicationException("Port disconnected");
            }
            this.lastChannelIndex = channelIndex;
            this.lastHexCommand = hexCommand;
            this.lastPulseTimeMs = pulseTimeMs;
        }
    }
}