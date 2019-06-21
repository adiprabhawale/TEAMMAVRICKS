package anonymous.teammavricks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebasAuth;

    TextView mProfileTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");


        firebasAuth=FirebaseAuth.getInstance();


        //init views
        mProfileTv=findViewById(R.id.profileTv);

    }

    private void checkuserStatus(){
        FirebaseUser user= firebasAuth.getCurrentUser();
        if (user!=null)
        {
            //user sign in asla tar etha asel
            //set email of logged in user
            mProfileTv.setText(user.getEmail());

        }
        else{
            //user sign in nasel tar etha
            startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        checkuserStatus();
        super.onStart();
    }

    /*menu options*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflaiting menu
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*menu item click handle*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id= item.getItemId();
        if(id== R.id.action_logut){
            firebasAuth.signOut();
            checkuserStatus();
        }

        return super.onOptionsItemSelected(item);
    }
}
