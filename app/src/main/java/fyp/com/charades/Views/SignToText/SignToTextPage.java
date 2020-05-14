package fyp.com.charades.Views.SignToText;

import androidx.appcompat.app.AppCompatActivity;
import fyp.com.charades.Views.Dashboard.DashboardPage;
import fyp.com.charades.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class SignToTextPage extends AppCompatActivity {
    private static int VIDEO_REQUEST = 101;
    private Uri videoUri = null;

    Button backToDashboardBtn, convertToTextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_to_text_page);

        initializeComponents();
        onListeners();
    }

    private void initializeComponents()
    {
        backToDashboardBtn = findViewById(R.id.back_Btn);
        convertToTextBtn = findViewById(R.id.conversion_Btn);
    }

    private void onListeners()
    {
        backToDashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignToTextPage.this, DashboardPage.class);
                        startActivity(intent);
            }
        });

        convertToTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignToTextPage.this, SignToText_Text.class);
                startActivity(intent);
            }
        });

    }

    public void captureVideo(View view)
    {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        Intent intent = new Intent(this, SignToTextPage.class);
        startActivity(intent);

        if(videoIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(videoIntent,VIDEO_REQUEST);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK) {
            videoUri = data.getData();
        }
    }
}