package tk.drac.tiratampa;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaseControlActivity extends AppCompatActivity {

    public static String TAG = "BaseControlActivity";
    private Button btnUp, btnDown, btnLeft, btnRight, btnForward, btnBack;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_control);

        // Reference buttons
        btnUp = findViewById(R.id.btn_up);
        btnDown = findViewById(R.id.btn_down);
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);
        btnForward = findViewById(R.id.btn_forward);
        btnBack = findViewById(R.id.btn_back);

        // Set up touch listeners for buttons
        setButtonTouchListener(btnUp, "U");
        setButtonTouchListener(btnDown, "I");
        setButtonTouchListener(btnLeft, "A");
        setButtonTouchListener(btnRight, "D");
        setButtonTouchListener(btnForward, "W");
        setButtonTouchListener(btnBack, "S");
    }

    private void setButtonTouchListener(Button button, String direction) {
        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Send control command when button is pressed
                    new SendPostRequestTask().execute(direction);
                    Log.i(TAG, "Direction: " + direction);
                    return true;
                case MotionEvent.ACTION_UP:
                    // Send stop command when button is released
                    new SendPostRequestTask().execute("STOP");
                    Log.i(TAG, "Stop control");
                    return true;
            }
            return false;
        });
    }

    private class SendPostRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String message = params[0];
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://192.168.4.1/post"); // Replace with actual server IP
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "text/plain");
                urlConnection.setDoOutput(true);

                // Send POST request
                OutputStream os = urlConnection.getOutputStream();
                os.write(message.getBytes());
                os.flush();
                os.close();

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Message sent successfully: " + message;
                } else {
                    return "Failed to send message. Response code: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(BaseControlActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }
}