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

public class tour_guiders_list extends AppCompatActivity {
  RecyclerView guiders_list;
  SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guiders_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        guiders_list=findViewById(R.id.guiders_list);
        guiders_list.setLayoutManager(new LinearLayoutManager(this));
        Firebase_Operations.getGuides(this,getIntent().getStringExtra("city"),guiders_list,getIntent().getStringExtra("CityId"));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.edit().remove("selected_guide").apply();
    }
}
