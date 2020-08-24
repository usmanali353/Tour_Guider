package fyp.tourguider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.Blob;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import fyp.tourguider.Firebase_Operations.Firebase_Operations;

public class Add_Hotel extends AppCompatActivity {
  MaterialEditText hotelName,perNightRent;
  ImageView hotelImage;
  Button btn;
    Bitmap bitmap=null;
    Uri image_uri=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__hotel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn=findViewById(R.id.add_hotel);
        hotelName=findViewById(R.id.hotelName);
        perNightRent=findViewById(R.id.perNightRent);
        hotelImage=findViewById(R.id.image);
        hotelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1000);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hotelName.getText().toString().isEmpty()){
                    hotelName.setError("Enter Hotel Name");
                }else if(perNightRent.getText().toString().isEmpty()){
                    perNightRent.setError("Enter Per night Rent");
                }else if(Integer.parseInt(perNightRent.getText().toString())==0||Integer.parseInt(perNightRent.getText().toString())<1000){
                    perNightRent.setError("Per Night Rent too Low");
                }else if(bitmap==null){
                    Toast.makeText(Add_Hotel.this,"Please Select Image",Toast.LENGTH_LONG).show();
                }else{
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    bitmap.recycle();
                    Firebase_Operations.addHotel(Add_Hotel.this,hotelName.getText().toString(), Blob.fromBytes(byteArray),getIntent().getStringExtra("CityId"),Integer.parseInt(perNightRent.getText().toString()));
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                image_uri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(image_uri);
                bitmap = BitmapFactory.decodeStream(imageStream);
                hotelImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
