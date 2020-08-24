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

public class add_tourist_spot extends AppCompatActivity {
  MaterialEditText TouristSpotName;
  ImageView image;
    Bitmap bitmap=null;
    Uri image_uri=null;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tourist_spot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TouristSpotName=findViewById(R.id.spotName);
        image=findViewById(R.id.spot_picture);
        btn=findViewById(R.id.add_spot);
        image.setOnClickListener(new View.OnClickListener() {
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
                if(TouristSpotName.getText().toString().isEmpty()){
                    TouristSpotName.setError("Enter Name");
                }else if(bitmap==null){
                    Toast.makeText(add_tourist_spot.this,"Select Image",Toast.LENGTH_LONG).show();
                }else{
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    bitmap.recycle();
                    Firebase_Operations.addTouristSpot(add_tourist_spot.this,TouristSpotName.getText().toString(),getIntent().getStringExtra("city"), Blob.fromBytes(byteArray),getIntent().getStringExtra("CityId"));
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
                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
