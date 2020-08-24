package fyp.tourguider;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import fyp.tourguider.Firebase_Operations.Firebase_Operations;

public class TouristDestinations extends AppCompatActivity {
RecyclerView list;
SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_destinations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        list=findViewById(R.id.destinationList);
        list.setLayoutManager(new LinearLayoutManager(this));
        Firebase_Operations.getTouristSpots(this,getIntent().getStringExtra("CityId"),list,getIntent().getStringExtra("city"));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.edit().remove("selected_destination").apply();
    }
}
