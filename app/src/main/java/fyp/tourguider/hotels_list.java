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

public class hotels_list extends AppCompatActivity {
 RecyclerView hotels;
 SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        hotels=findViewById(R.id.hotels);
        hotels.setLayoutManager(new LinearLayoutManager(this));
        Firebase_Operations.getHotel(this,getIntent().getStringExtra("CityId"),hotels);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.skip_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.skip){
            startActivity(new Intent(this,vehicle_list.class));
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.edit().remove("selected_hotel").apply();
    }
}
