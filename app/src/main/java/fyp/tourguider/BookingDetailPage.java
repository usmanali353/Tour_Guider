package fyp.tourguider;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fyp.tourguider.Firebase_Operations.Firebase_Operations;
import fyp.tourguider.Model.Booking;
import fyp.tourguider.Model.User;

public class BookingDetailPage extends AppCompatActivity {
    TextView date,days,hotel,vehicle,hotelRent,vehicleRent,destination,guide,guideRent;
    Button btn;
    Booking booking;
    SharedPreferences prefs;
    User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        u=new Gson().fromJson(prefs.getString("user_info",null),User.class);
        booking=new Gson().fromJson(getIntent().getStringExtra("booking_data"),Booking.class);
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
        //setting Values
        if(booking.getStatus().equals("Canceled")){
            btn.setVisibility(View.GONE);
        }
        if(booking.getStatus().equals("Approved")){
            btn.setVisibility(View.GONE);
        }
        if(booking.getStatus().equals("Rejected")){
            btn.setVisibility(View.GONE);
        }
        btn.setText("Change Status");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(u.getRole().equals("Tour Guider")&&!booking.getStatus().equals("Canceled")){
                    AlertDialog.Builder changeStatus=new AlertDialog.Builder(BookingDetailPage.this);
                    changeStatus.setTitle("Change Status");
                    changeStatus.setMessage("do you want to approve this request or Reject");
                    changeStatus.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Firebase_Operations.changeStatus(BookingDetailPage.this,"Approved",getIntent().getStringExtra("booking_id"),u.getRole());
                        }
                    }).setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Firebase_Operations.changeStatus(BookingDetailPage.this,"Rejected",getIntent().getStringExtra("booking_id"),u.getRole());
                        }
                    }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                        }
                    }).show();

                }else if(u.getRole().equals("Customer")&&booking.getStatus().equals("New Request")){
                    AlertDialog.Builder changeStatus=new AlertDialog.Builder(BookingDetailPage.this);
                    changeStatus.setTitle("Change Status");
                    changeStatus.setMessage("do you want Cancel this Request");
                    changeStatus.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Firebase_Operations.changeStatus(BookingDetailPage.this,"Canceled",getIntent().getStringExtra("booking_id"),u.getRole());
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            }
        });
        date.setText(booking.getTourStartDate());
        days.setText(String.valueOf(booking.getDays()));
        hotel.setText(booking.getSelectedHotel());
        vehicle.setText(booking.getSelectedVehicle());
        hotelRent.setText(String.valueOf(booking.getHotelRent()));
        vehicleRent.setText(String.valueOf(booking.getVehicleRent()));
        destination.setText(booking.getSelectedDestination());
        guide.setText(booking.getSelectedGuider());
        guideRent.setText(String.valueOf(booking.getGuiderRent()));
    }

}
