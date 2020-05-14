package fyp.com.charades.Views.SignToText;

import androidx.appcompat.app.AppCompatActivity;
import fyp.com.charades.Views.Dashboard.DashboardPage;
import fyp.com.charades.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignToText_Text extends AppCompatActivity {

    Button translateAnotherBtn, backToDashboardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_to_text__text);

        initializeComponents();
        onListeners();
    }

    private void initializeComponents()
    {
        translateAnotherBtn = findViewById(R.id.translateAnotherBtn);
        backToDashboardBtn = findViewById(R.id.backToDashboardBtn);
    }

    private void onListeners()
    {
        translateAnotherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignToText_Text.this, SignToTextPage.class);
                startActivity(intent);
            }
        });

        backToDashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignToText_Text.this, DashboardPage.class);
                startActivity(intent);
            }
        });
    }
}