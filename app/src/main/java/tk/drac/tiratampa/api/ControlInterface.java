package tk.drac.tiratampa.api;

import java.io.IOException;

public interface ControlInterface {
    long idealSleepTimeMillis();
    void refresh(ResultCallback<Void, Exception> cback);
    void sendLedControlMessage(boolean on, ResultCallback<Boolean, Exception> cback);
    void sendMotorControlMessage(double left, double right, ResultCallback<Double[], Exception> cback);
}
