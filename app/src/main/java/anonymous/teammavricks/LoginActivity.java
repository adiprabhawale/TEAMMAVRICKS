package anonymous.teammavricks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText mEmailEt, mpasswordEt;
    TextView notHaveAccntTv;
    Button mLoginBtn;

    private FirebaseAuth mAuth;

    //progress dialog
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        mEmailEt=findViewById(R.id.emailEt);
        mpasswordEt=findViewById(R.id.passwordEt);
        notHaveAccntTv=findViewById(R.id.nothave_accountTv);
        mLoginBtn=findViewById(R.id.loginBtn);


        //login button cha vishay
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input data
                String email=mEmailEt.getText().toString();
                String password= mpasswordEt.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //invalid email cha vishay
                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);
                }
                else {
                    //valid email pattern
                    loginUser(email,password);
                }
            }
        });
        //don't have acount text view click
        notHaveAccntTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });


        //init progress dialog
        pd=new ProgressDialog(this);
        pd.setMessage("Logging In...");
    }

    private void loginUser(String email, String password) {
        //show progress dialog
        pd.show();
        mAuth.signInWithEmailAndPassword(email, password )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //dismiss progress dialog
                            pd.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //user login jhalai etha
                            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                            finish();

                        } else {
                            //dismiss progress dialog
                            pd.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //dismiss progress dialog
                pd.dismiss();
                //error,get and show error
                Toast.makeText(LoginActivity.this, "failed"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }
}
