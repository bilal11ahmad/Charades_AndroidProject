package fyp.com.charades.Views.Initials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import fyp.com.charades.R;
import fyp.com.charades.Views.Dashboard.DashboardPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressWarnings("ConstantConditions")
public class LoginPage extends AppCompatActivity {

    private static final String FILE_NAME ="Charades";
    private static final String USER_LOGGED_KEY ="USER_LOGGED";

    EditText usernameText, passwordText;
    Button loginBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!usernameText.getText().toString().equals("")) && (!passwordText.getText().toString().equals(""))) {
                    mAuth.signInWithEmailAndPassword(usernameText.getText().toString(), passwordText.getText().toString())
                            .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("SignIn Status", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("SignIn Status", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginPage.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }
                else{
                    Toast.makeText(LoginPage.this, "Kindly fill login details.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user)
    {
        if(user != null){
            SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
            int logged = 1;
            editor.putInt(USER_LOGGED_KEY,logged);
            editor.apply();
            Intent intent = new Intent(LoginPage.this, DashboardPage.class);
            startActivity(intent);
        }
    }
}