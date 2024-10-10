package tk.drac.tiratampa.api;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;

public class BluetoothControl implements ControlInterface {
    protected BluetoothSocket bsocket;
    protected double left;
    protected double right;
    public BluetoothControl(BluetoothSocket socket) {
        this.bsocket = socket;
    }

    @Override
    public long idealSleepTimeMillis() {
        // TODO: Natan falou que precisa de refresh.
        return 25;
    }

    @Override
    public void refresh(final ResultCallback<Void, Exception> cback) {
        sendMotorControlMessage(this.left, this.right, new ResultCallback<Double[], Exception>() {
            @Override
            public void onSuccess(Double[] result) {
                cback.onSuccess(null);
            }

            @Override
            public void onError(Exception error) {
                cback.onError(error);
            }
        });
    }

    @Override
    public void sendLedControlMessage(
            final boolean on,
            final ResultCallback<Boolean, Exception> cback) {

        cback.onError(new UnsupportedOperationException());
    }

    @Override
    public void sendMotorControlMessage(
            final double left,
            final double right,
            final ResultCallback<Double[], Exception> cback) {

        this.left  = left;
        this.right = right;

        int sl = (int)Math.round(Math.abs(left)  * 5.0);
        int sr = (int)Math.round(Math.abs(right) * 5.0);
        sl = sl > 5 ? 5 : sl;
        sr = sr > 5 ? 5 : sr;
        int dl = (int) Math.signum(left);
        int dr = (int) Math.signum(right);

        /* Convert to the insane format actually used by the micro-controller. */
        if(dl < 0 && sl > 0) sl += 5;
        if(dr < 0 && sr > 0) sr += 5;
        sl &= 0xf;
        sr &= 0xf;

        try {
            this.bsocket.getOutputStream().write(new byte[] { (byte)(sl << 4 | sr) });
            cback.onSuccess(new Double[] { left, right });
        } catch (IOException e) {
            cback.onError(e);
        }
    }
}
