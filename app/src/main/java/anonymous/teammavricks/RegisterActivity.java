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

 public class RegisterActivity extends AppCompatActivity {

    EditText memailEt, mpasswordEt;

    Button mRegisterBtn;
    TextView mHaveAccountTv;

    ProgressDialog progressDialog;

     private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Accout");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        memailEt=findViewById(R.id.emailEt);
        mpasswordEt=findViewById(R.id.passwordEt);
        mRegisterBtn=findViewById(R.id.registerBtn);
        mHaveAccountTv=findViewById(R.id.have_accountTv);


        mAuth = FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");



        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =memailEt.getText().toString().trim();
                String password =mpasswordEt.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    memailEt.setError("ha Email kay tujhya companych aahe kay");
                    memailEt.setFocusable(true);
                }
                else if(mpasswordEt.length()<6){
                    mpasswordEt.setError("abe 6 digit ch password tak");
                    mpasswordEt.setFocusable(true);
                }
                else{
                    registerUser(email,password);
                }
            }
        });
        //handle login textview click listener
        mHaveAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

    }

     private void registerUser(String email, String password) {

        progressDialog.show();

         mAuth.createUserWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             // Sign in success, dismiss dialog and start register activity
                             FirebaseUser user = mAuth.getCurrentUser();
                             Toast.makeText(RegisterActivity.this, "Registered...\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(RegisterActivity.this,ProfileActivity.class));
                             finish();
                         } else {
                             // If sign in fails, display a message to the user.
                             progressDialog.dismiss();
                          Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                         }

                     }
                 }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {

                 progressDialog.dismiss();
                 Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

             }
         });
     }

     @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }
}
