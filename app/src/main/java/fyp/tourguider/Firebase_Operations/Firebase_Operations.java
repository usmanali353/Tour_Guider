package fyp.tourguider.Firebase_Operations;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;
import fyp.tourguider.Adapters.cities_list_adapter;
import fyp.tourguider.Adapters.guides_list;
import fyp.tourguider.Adapters.hotel_list_adapter;
import fyp.tourguider.Adapters.orders_list_adapter;
import fyp.tourguider.Adapters.tourist_destination_list_adapter;
import fyp.tourguider.Adapters.vehicle_list_adapter;
import fyp.tourguider.Model.Booking;
import fyp.tourguider.Model.City;
import fyp.tourguider.Model.Hotel;
import fyp.tourguider.Model.TouristDestination;
import fyp.tourguider.Model.User;
import fyp.tourguider.Model.Vehicle;
import fyp.tourguider.R;
import fyp.tourguider.Utils.utils;
import fyp.tourguider.customer_home;
import fyp.tourguider.tour_guider_home;

public class Firebase_Operations {
    public static void addCities(Context context, String name){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Adding City....");
        pd.show();
        FirebaseFirestore.getInstance().collection("City").document().set(new City(name)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"City Added successfully",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public static void getCities(Context context, RecyclerView citiesList,String page){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("getting Cities....");
        pd.show();
        ArrayList<City> cities=new ArrayList<>();
        ArrayList<String>  cityIds=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("City").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        cities.add(queryDocumentSnapshots.getDocuments().get(i).toObject(City.class));
                        cityIds.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    citiesList.setAdapter(new cities_list_adapter(cities,cityIds,context,page));
                }else{
                    Toast.makeText(context,"No Cities Found",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void register(Context context,String userId,String name,String role,String email,int hourlyRate,String city,String phone){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Registering User....");
        pd.show();
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);
        FirebaseFirestore.getInstance().collection("Users").document(userId).set(new User(name,role,email,hourlyRate,city,phone)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"User Registered successfully",Toast.LENGTH_LONG).show();
                    if(role.equals("Customer")){
                        prefs.edit().putString("user_info",new Gson().toJson(new User(name,role,email,hourlyRate,city,phone))).apply();
                        context.startActivity(new Intent(context, customer_home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        ((AppCompatActivity)context).finish();
                    }else if(role.equals("Tour Guider")){
                        prefs.edit().putString("user_info",new Gson().toJson(new User(name,role,email,hourlyRate,city,phone))).apply();
                        context.startActivity(new Intent(context, tour_guider_home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        ((AppCompatActivity)context).finish();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void checkUserAlreadyExist(Context context,String userId,String phone){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Checking if Already Exists....");
        pd.show();
        ArrayList<City> cities=new ArrayList<>();
        ArrayList<String>  cityNames=new ArrayList<>();
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        FirebaseFirestore.getInstance().collection("Users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
             if(!documentSnapshot.exists()){
                 FirebaseFirestore.getInstance().collection("City").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                     @Override
                     public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                         pd.dismiss();
                         if(queryDocumentSnapshots.getDocuments().size()>0){
                             for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                                 cities.add(queryDocumentSnapshots.getDocuments().get(i).toObject(City.class));
                                 cityNames.add(queryDocumentSnapshots.getDocuments().get(i).toObject(City.class).getCityName());
                             }
                             View v= LayoutInflater.from(context).inflate(R.layout.layout_register,null);
                             MaterialEditText name=v.findViewById(R.id.nametxt);
                             MaterialEditText email=v.findViewById(R.id.emailtxt);
                             MaterialSpinner city=v.findViewById(R.id.city);
                             MaterialSpinner role=v.findViewById(R.id.role);
                             MaterialEditText hourlyRate=v.findViewById(R.id.rate_txt);
                             ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, cityNames);
                             adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                             city.setAdapter(adapter);
                             hourlyRate.setVisibility(View.GONE);
                             role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                 @Override
                                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                     if(position==1){
                                         hourlyRate.setVisibility(View.VISIBLE);
                                         city.setVisibility(View.VISIBLE);
                                     }else{
                                         hourlyRate.setVisibility(View.GONE);
                                         city.setVisibility(View.GONE);
                                     }
                                 }

                                 @Override
                                 public void onNothingSelected(AdapterView<?> parent) {

                                 }
                             });
                             AlertDialog registerUserDialog =new AlertDialog.Builder(context)
                                     .setTitle("Register User")
                                     .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {


                                         }
                                     }).setView(v).create();
                             registerUserDialog.show();
                             registerUserDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     if(name.getText().toString().isEmpty()){
                                         name.setError("Enter Name");
                                     }else if(email.getText().toString().isEmpty()){
                                         email.setError("Enter Email");
                                     }else if(!utils.isEmailValid(email.getText().toString())){
                                         email.setError("Email is Invalid");
                                     }else if(role.getSelectedItem()==null){
                                         role.setError("Please Select Role");
                                     }else if(hourlyRate.getVisibility()==View.VISIBLE&&hourlyRate.getText().toString().isEmpty()){
                                         hourlyRate.setError("Enter Hourly Rate");
                                     }else if(city.getVisibility()==View.VISIBLE&&city.getSelectedItem()==null){
                                         city.setError("Please Select City");
                                     }else {
                                         if(hourlyRate.getVisibility()==View.VISIBLE&&city.getVisibility()==View.VISIBLE) {
                                             register(context, userId, name.getText().toString(), role.getSelectedItem().toString(), email.getText().toString(), Integer.parseInt(hourlyRate.getText().toString()),city.getSelectedItem().toString(),phone);
                                         }else{
                                             register(context, userId, name.getText().toString(), role.getSelectedItem().toString(), email.getText().toString(),0,null,phone);
                                         }
                                     }
                                 }
                             });

                         }else{
                             Toast.makeText(context,"No Cities Found",Toast.LENGTH_LONG).show();
                         }

                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         pd.dismiss();
                         Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                     }
                 });

             }else{
                 User u=documentSnapshot.toObject(User.class);
                 prefs.edit().putString("user_info",new Gson().toJson(u)).apply();
                 if(u.getRole().equals("Customer")){
                     context.startActivity(new Intent(context,customer_home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                     ((AppCompatActivity)context).finish();
                 }else if(u.getRole().equals("Tour Guider")){
                     context.startActivity(new Intent(context,tour_guider_home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                     ((AppCompatActivity)context).finish();
                 }
             }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void addTouristSpot(Context context, String name, String city, Blob image,String cityId){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Adding Tourist Destinations....");
        pd.show();
        FirebaseFirestore.getInstance().collection("City").document(cityId).collection("tourist_spots").document().set(new TouristDestination(name,image,city)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"Tourism Spot Added successfully",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getTouristSpots(Context context,String cityId,RecyclerView spotsList,String city){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Fetching Tourist Spots....");
        pd.show();
        ArrayList<TouristDestination> touristDestinations=new ArrayList<>();
        ArrayList<String>  touristDestinationId=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("City").document(cityId).collection("tourist_spots").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        touristDestinations.add(queryDocumentSnapshots.getDocuments().get(i).toObject(TouristDestination.class));
                        touristDestinationId.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    spotsList.setAdapter(new tourist_destination_list_adapter(touristDestinations,context,touristDestinationId,city,cityId));
                }else{
                    Toast.makeText(context,"No Tourist Places Found",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getGuides(Context context,String city,RecyclerView GuidesList,String cityId){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Fetching Tourist Guides....");
        pd.show();
        ArrayList<User> tourGuide=new ArrayList<>();
        ArrayList<String>  tourGuideId=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Users").whereEqualTo("city",city).whereEqualTo("role","Tour Guider").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        tourGuide.add(queryDocumentSnapshots.getDocuments().get(i).toObject(User.class));
                        tourGuideId.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    GuidesList.setAdapter(new guides_list(tourGuide,tourGuideId,context,cityId));
                }else{
                    Toast.makeText(context,"No Tourist Guiders Found",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void addHotel(Context context,String hotelName,Blob hotelImage,String cityId,int perNightRent){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Adding Hotel in City....");
        pd.show();
        FirebaseFirestore.getInstance().collection("City").document(cityId).collection("hotels").document().set(new Hotel(hotelName,hotelImage,perNightRent)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"Hotel Added Successfully",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getHotel(Context context,String cityId,RecyclerView hotelList){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Fetching Hotels....");
        pd.show();
        ArrayList<Hotel> hotels=new ArrayList<>();
        ArrayList<String>  hotelId=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("City").document(cityId).collection("hotels").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        hotels.add(queryDocumentSnapshots.getDocuments().get(i).toObject(Hotel.class));
                        hotelId.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    hotelList.setAdapter(new hotel_list_adapter(hotels,hotelId,context));
                }else{
                    Toast.makeText(context,"No Hotels Found",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void addVehicle(Context context,String vehicleName,int perDayRent,Blob Image){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Adding Vehicle....");
        pd.show();
        FirebaseFirestore.getInstance().collection("Vehicles").document().set(new Vehicle(vehicleName,perDayRent,Image)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"Vehicle Added Successfully",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getVehicles(Context context,RecyclerView vehicleList){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Fetching Vehicles....");
        pd.show();
        ArrayList<Vehicle> vehicles=new ArrayList<>();
        ArrayList<String>  vehicleId=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Vehicles").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        vehicles.add(queryDocumentSnapshots.getDocuments().get(i).toObject(Vehicle.class));
                        vehicleId.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    vehicleList.setAdapter(new vehicle_list_adapter(vehicles,vehicleId,context));
                }else{
                    Toast.makeText(context,"No Vehicles Found",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void BookTour(Context context, Booking booking){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Booking Tour....");
        pd.show();
        FirebaseFirestore.getInstance().collection("Booking").document().set(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"Request is sent",Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context,customer_home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    ((AppCompatActivity)context).finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getRequestGuider(Context context,String guiderId,RecyclerView bookingList){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("getting Requests....");
        pd.show();
        ArrayList<Booking> bookings=new ArrayList<>();
        ArrayList<String>  bookingIds=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Booking").whereEqualTo("selectedGuider",guiderId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        bookings.add(queryDocumentSnapshots.getDocuments().get(i).toObject(Booking.class));
                        bookingIds.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    bookingList.setAdapter(new orders_list_adapter(bookings,context,bookingIds));
                }else{
                    Toast.makeText(context,"No Bookings Found",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getRequestCustomer(Context context,String UserId,RecyclerView bookingList){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("getting Requests....");
        pd.show();
        ArrayList<Booking> bookings=new ArrayList<>();
        ArrayList<String>  bookingIds=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Booking").whereEqualTo("customerId",UserId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        bookings.add(queryDocumentSnapshots.getDocuments().get(i).toObject(Booking.class));
                        bookingIds.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    bookingList.setAdapter(new orders_list_adapter(bookings,context,bookingIds));
                }else{
                    Toast.makeText(context,"No Bookings Found",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void changeStatus(Context context,String status,String BookingId,String Role){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Changing Status....");
        pd.show();
        Map<String,Object> updateStatus=new HashMap<>();
        updateStatus.put("status",status);
        FirebaseFirestore.getInstance().collection("Booking").document(BookingId).update(updateStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    if(Role.equals("Customer")) {
                        Toast.makeText(context, "Status Updated", Toast.LENGTH_LONG).show();
                        context.startActivity(new Intent(context,customer_home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        ((AppCompatActivity)context).finish();
                    }else{
                        Toast.makeText(context, "Status Updated", Toast.LENGTH_LONG).show();
                        context.startActivity(new Intent(context,tour_guider_home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        ((AppCompatActivity)context).finish();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
             pd.dismiss();
             Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
