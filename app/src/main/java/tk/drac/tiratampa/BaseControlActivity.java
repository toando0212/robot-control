package tk.drac.tiratampa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_control);

        // Lấy tham chiếu đến các nút
        Button btnUp = findViewById(R.id.btn_up);
        Button btnDown = findViewById(R.id.btn_down);
        Button btnLeft = findViewById(R.id.btn_left);
        Button btnRight = findViewById(R.id.btn_right);

        // Gán sự kiện khi nhấn vào các nút
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleControl("UP");
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleControl("DOWN");
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleControl("LEFT");
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleControl("RIGHT");
            }
        });
    }

    // Phương thức để xử lý điều khiển
    private void handleControl(String direction) {
        // Thay thế bằng hành động điều khiển robot thực sự
        Toast.makeText(this, "Direction: " + direction, Toast.LENGTH_SHORT).show();
    }
}



