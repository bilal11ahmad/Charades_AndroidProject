package fyp.com.charades.Views.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import fyp.com.charades.R;
import fyp.com.charades.Views.Initials.MainActivity;
import fyp.com.charades.Views.SignToText.SignToTextPage;
import fyp.com.charades.Views.TextToSign.TextToSign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardPage extends AppCompatActivity {
    private static final String FILE_NAME ="Charades";
    private static final String USER_LOGGED_KEY ="USER_LOGGED";

    Button signToTextBtn, textToSignBtn, learnToSignBtn, logOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_page);

        initializeComponents();
        onListeners();
    }

    private void initializeComponents(){
        signToTextBtn = findViewById(R.id.sign_To_Text_Btn);
        textToSignBtn = findViewById(R.id.text_To_Sign_Btn);
        learnToSignBtn = findViewById(R.id.learn_To_Sign_Btn);
        logOutBtn = findViewById(R.id.logout_Btn);
    }

    private void onListeners(){
        signToTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardPage.this, SignToTextPage.class);
                startActivity(intent);
            }
        });
        textToSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
            }
        });
        learnToSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
            }
        });
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
                int logged = 0;
                editor.putInt(USER_LOGGED_KEY,logged);
                editor.apply();
                Intent intent = new Intent(DashboardPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
        textToSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardPage.this, TextToSign.class);
                startActivity(intent);
            }
        });
    }
}
