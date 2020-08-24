package fyp.tourguider;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fyp.tourguider.Firebase_Operations.Firebase_Operations;
import fyp.tourguider.Model.Booking;
import fyp.tourguider.Model.Hotel;
import fyp.tourguider.Model.TouristDestination;
import fyp.tourguider.Model.User;
import fyp.tourguider.Model.Vehicle;
import fyp.tourguider.Utils.utils;

public class Book_Tour extends AppCompatActivity {
TextView date,days,hotel,vehicle,hotelRent,vehicleRent,destination,guide,guideRent;
SharedPreferences prefs;
String hotelName,guideId;
Button btn;
int hRent,vRent;
String v;
User u,u2;
String t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__tour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        date=findViewById(R.id.date);
        days=findViewById(R.id.end_date);
        hotel=findViewById(R.id.hotel);
        vehicle=findViewById(R.id.vehicle);
        hotelRent=findViewById(R.id.hotel_rent);
        vehicleRent=findViewById(R.id.vehicle_rent);
        destination=findViewById(R.id.destination);
        guide=findViewById(R.id.guider);
        btn=findViewById(R.id.btn);
        guideRent=findViewById(R.id.guider_rate);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        hotelName=prefs.getString("selected_hotel",null);
        hRent=prefs.getInt("selected_hotel_rent",0);
        u = new Gson().fromJson(prefs.getString("selected_guide", null), User.class);
        u2= new Gson().fromJson(prefs.getString("user_info", null), User.class);
        t = prefs.getString("selected_destination", null);
        guideId=prefs.getString("selected_guide_id",null);
        v=prefs.getString("selected_vehicle",null);
        vRent=prefs.getInt("selected_vehicle_rent",0);
        //Setting Values
        date.setText(prefs.getString("start_date",""));
        days.setText(prefs.getString("days",""));
        hotel.setText(hotelName);
        vehicle.setText(v);
        destination.setText(t);
        guide.setText(u.getName());
        hotelRent.setText(String.valueOf(Integer.parseInt(prefs.getString("days",""))*prefs.getInt("selected_hotel_rent",0)));
        vehicleRent.setText(String.valueOf(Integer.parseInt(prefs.getString("days",""))*prefs.getInt("selected_vehicle_rent",0)));
        guideRent.setText(String.valueOf(Integer.parseInt(prefs.getString("days",""))*u.getHourlyRate()));
         int total=Integer.parseInt(hotelRent.getText().toString())+Integer.parseInt(vehicleRent.getText().toString())+Integer.parseInt(guideRent.getText().toString());
         Booking b=new Booking(date.getText().toString(), utils.getCurrentDate(), FirebaseAuth.getInstance().getCurrentUser().getUid(),u2.getName(),u2.getPhone(),destination.getText().toString(),hotel.getText().toString(),vehicle.getText().toString(),prefs.getString("selected_guide_id",null),Integer.parseInt(prefs.getString("days","")),Integer.parseInt(hotelRent.getText().toString()),Integer.parseInt(vehicleRent.getText().toString()),Integer.parseInt(guideRent.getText().toString()),total,"New Request");
         btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Firebase_Operations.BookTour(Book_Tour.this,b);
                 prefs.edit().remove("selected_hotel").apply();
                 prefs.edit().remove("selected_destination").apply();
                 prefs.edit().remove("days").apply();
                 prefs.edit().remove("start_date").apply();
                 prefs.edit().remove("selected_hotel_rent").apply();
                 prefs.edit().remove("selected_vehicle_rent").apply();
                 prefs.edit().remove("selected_vehicle").apply();
                 prefs.edit().remove("selected_guide").apply();
                 prefs.edit().remove("selected_guide_id").apply();
             }
         });
    }

    }


