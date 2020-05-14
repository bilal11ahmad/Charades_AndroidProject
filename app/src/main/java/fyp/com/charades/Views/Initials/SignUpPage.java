package fyp.com.charades.Views.Initials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import fyp.com.charades.R;
import fyp.com.charades.Views.Dashboard.DashboardPage;

import android.content.Intent;
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

public class SignUpPage extends AppCompatActivity {

    EditText emailText, passwordText, confirmPasswordText;
    Button signUpBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        initializeComponents();
        onListeners();
    }

    private void initializeComponents(){
        mAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        confirmPasswordText = findViewById(R.id.confirmPasswordText);
        signUpBtn = findViewById(R.id.signupBtn);
    }

    private void onListeners(){
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String confirmPassword = confirmPasswordText.getText().toString();

                if(!email.equals("") && !password.equals("") && !confirmPassword.equals("")){
                    if(password.equals(confirmPassword)){
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("SignUp", "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                                            startActivity(intent);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("SignUp", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(SignUpPage.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                        // ...
                                    }
                                });
                    }
                    else{
                        Toast.makeText(SignUpPage.this, "Kindly match password in both fields.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void updateUI(FirebaseUser user){
        Intent intent = new Intent(SignUpPage.this, DashboardPage.class);
        startActivity(intent);
    }
}