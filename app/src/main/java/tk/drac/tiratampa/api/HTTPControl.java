package tk.drac.tiratampa.api;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HTTPControl implements ControlInterface {
    public static final String USER_AGENT = "tk.drac.tiratampa/1.0";

    protected OkHttpClient client;
    protected String address;
    protected double left;
    protected double right;
    public HTTPControl(OkHttpClient client, String address){
        this.address = address;
        this.client = client;
        this.left  = 0;
        this.right = 0;
    }

    protected String genUrl(final String str) {
        return "http://" + this.address + str;
    }

    @Override
    public long idealSleepTimeMillis() {
        return 500;
    }

    @Override
    public void refresh(final ResultCallback<Void, Exception> cback) {
        /* Resending the last message keeps the motor going. */
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
        String request = on ? "/ligarled" : "/desligarled";

        client.newCall(new Request.Builder()
                .header("User-Agent", USER_AGENT)
                .url(genUrl(request))
                .get()
                .build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                cback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() != 200) {
                    cback.onError(new TTCOIsDeadException("Bad Code: " + response.code()));
                    response.close();
                    return;
                }
                cback.onSuccess(on);
                response.close();
            }
        });
    }

    @Override
    public void sendMotorControlMessage(
            final double left,
            final double right,
            final ResultCallback<Double[], Exception> cback) {

        this.left  = left;
        this.right = right;

        int sl = (int)Math.round(Math.abs(left)  * 1024.0);
        int sr = (int)Math.round(Math.abs(right) * 1024.0);
        sl = sl > 1023 ? 1023 : sl;
        sr = sr > 1023 ? 1023 : sr;

        // TODO: Consider movement direction in the HTTPControl.
        int dl = (int) Math.signum(left);
        int dr = (int) Math.signum(right);

        String query = String.format("/motor?speed1=%d&speed2=%d", sl, sr);
        client.newCall(new Request.Builder()
                .header("User-Agent", USER_AGENT)
                .url(genUrl(query))
                .get()
                .build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                cback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() != 200) {
                    cback.onError(new TTCOIsDeadException("Bad Code: " + response.code()));
                    response.close();
                    return;
                }
                cback.onSuccess(new Double[] { left, right });
                response.close();
            }
        });
    }
}
