public class LegacyRS485RelayBoard {
    private boolean ioErrorFlag = false;

    public void setIoErrorFlag(boolean ioErrorFlag) {
        this.ioErrorFlag = ioErrorFlag;
    }

    public void sendRelayCommand(int channelIndex, String hexCommand, long pulseTimeMs)
            throws SerialPortCommunicationException {

        if (ioErrorFlag) {
            throw new SerialPortCommunicationException("Hardware IO_ERROR on RS485 bus!");
        }

        System.out.println("[LegacyRS485RelayBoard] Sending RS485 command " + hexCommand
                + " to channel " + channelIndex + " for " + pulseTimeMs + " ms.");
    }
}