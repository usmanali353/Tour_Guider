package fyp.tourguider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import fyp.tourguider.Firebase_Operations.Firebase_Operations;

public class Admin_Home extends AppCompatActivity {
    int images[]={R.drawable.add_city_icon,R.drawable.tourism,R.drawable.add_hotel,R.drawable.car};
    String texts[]={"Add City","Add Tourist Destination","Add Hotel","Add Vehicle"};
    SharedPreferences prefs;
    Bitmap bitmap=null;
    Uri image_uri=null;
    ImageView vehicleImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if(index==0){
                                View v= LayoutInflater.from(Admin_Home.this).inflate(R.layout.add_city,null);
                                MaterialEditText cityName=v.findViewById(R.id.cityName);
                                AlertDialog addCityDialog=new AlertDialog.Builder(Admin_Home.this)
                                        .setTitle("Add City")
                                        .setMessage("Enter Valid Name of the City")
                                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).setView(v).create();
                                      addCityDialog.show();
                                      addCityDialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if(cityName.getText().toString().isEmpty()){
                                                  cityName.setError("Enter City Name");
                                              }else{
                                                  Firebase_Operations.addCities(Admin_Home.this,cityName.getText().toString());
                                              }
                                          }
                                      });
                            }else if(index==1){
                              startActivity(new Intent(Admin_Home.this,cities_for_destination.class));
                            }else if(index==2){
                                startActivity(new Intent(Admin_Home.this,cities_for_destination.class));
                            }else if(index==3){
                                View v=LayoutInflater.from(Admin_Home.this).inflate(R.layout.add_vehicle,null);
                                MaterialEditText name=v.findViewById(R.id.vehicleName);
                                MaterialEditText perDayRent=v.findViewById(R.id.perDayRent);
                                 vehicleImage=v.findViewById(R.id.image);
                                vehicleImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                        photoPickerIntent.setType("image/*");
                                        startActivityForResult(photoPickerIntent, 1000);
                                    }
                                });
                              AlertDialog addVehicleDialog=new AlertDialog.Builder(Admin_Home.this)
                                      .setTitle("Add Vehicle")
                                      .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {

                                          }
                                      }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {
                                              dialog.dismiss();
                                          }
                                      }).setView(v).create();
                                      addVehicleDialog.show();
                                      addVehicleDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if(name.getText().toString().isEmpty()){
                                                  name.setError("Enter Vehicle Name");
                                              }else if(perDayRent.getText().toString().isEmpty()){
                                                  perDayRent.setError("Enter Per Day Rent");
                                              }else if(Integer.parseInt(perDayRent.getText().toString())==0||Integer.parseInt(perDayRent.getText().toString())<1000){
                                                  perDayRent.setError("Rent too Low");
                                              }else if(bitmap==null){
                                                  Toast.makeText(Admin_Home.this,"Select Vehicle Image",Toast.LENGTH_LONG).show();
                                              }else{
                                                  ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                  bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                  byte[] byteArray = stream.toByteArray();
                                                  bitmap.recycle();
                                                  Firebase_Operations.addVehicle(Admin_Home.this,name.getText().toString(),Integer.parseInt(perDayRent.getText().toString()), Blob.fromBytes(byteArray));
                                              }
                                          }
                                      });
                            }
                        }
                    })
                    .normalImageRes(images[i])
                    .normalText(texts[i])
                    .shadowEffect(true)
                    .containsSubText(false);

            bmb.addBuilder(builder);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.signOut){
            FirebaseAuth.getInstance().signOut();
            prefs.edit().remove("user_role").apply();
            startActivity(new Intent(Admin_Home.this,Login.class));
            finish();
        }
        return true;
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                image_uri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(image_uri);
                bitmap = BitmapFactory.decodeStream(imageStream);
                vehicleImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

}
