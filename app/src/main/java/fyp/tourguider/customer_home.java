package fyp.tourguider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import fyp.tourguider.Firebase_Operations.Firebase_Operations;

public class customer_home extends AppCompatActivity {
RecyclerView citiesList;
SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        citiesList=findViewById(R.id.cities);
        citiesList.setLayoutManager(new LinearLayoutManager(this));
        Firebase_Operations.getCities(customer_home.this,citiesList,"Add");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.signOut){
            FirebaseAuth.getInstance().signOut();
            prefs.edit().remove("user_info").apply();
            startActivity(new Intent(customer_home.this,Login.class));
            finish();
        }else if(item.getItemId()==R.id.bookings){
            startActivity(new Intent(this,customer_booking_page.class));
        }else if(item.getItemId()==R.id.chatbot){
            startActivity(new Intent(this,chatbot.class).putExtra("url","https://console.dialogflow.com/api-client/demo/embedded/e41f3ffd-187b-43a5-9890-afc4cc85359d"));
        }
        return true;
    }
}
