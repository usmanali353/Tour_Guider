package fyp.tourguider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

public class vehicle_list extends AppCompatActivity {
    RecyclerView vehicles;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        vehicles = findViewById(R.id.vehicles);
        vehicles.setLayoutManager(new LinearLayoutManager(this));
        Firebase_Operations.getVehicles(this, vehicles);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.edit().remove("selected_vehicle").apply();
    }
}