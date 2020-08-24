package fyp.tourguider;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import fyp.tourguider.Firebase_Operations.Firebase_Operations;

public class customer_booking_page extends AppCompatActivity {
 RecyclerView bookings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_booking_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bookings=findViewById(R.id.bookings);
        bookings.setLayoutManager(new LinearLayoutManager(this));
        Firebase_Operations.getRequestCustomer(this, FirebaseAuth.getInstance().getCurrentUser().getUid(),bookings);
    }

}
