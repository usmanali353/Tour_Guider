package fyp.tourguider;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import fyp.tourguider.Firebase_Operations.Firebase_Operations;

public class CitiesAdmin extends AppCompatActivity {
RecyclerView citiesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        citiesList=findViewById(R.id.cities);
        citiesList.setLayoutManager(new LinearLayoutManager(this));
        Firebase_Operations.getCities(CitiesAdmin.this,citiesList,"View");

    }
}