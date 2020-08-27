package fyp.tourguider.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import fyp.tourguider.Model.Hotel;
import fyp.tourguider.Model.User;
import fyp.tourguider.R;
import fyp.tourguider.Utils.utils;
import fyp.tourguider.vehicle_list;

public class hotel_list_adapter extends RecyclerView.Adapter<hotel_list_adapter.hotel_list_viewholder> {
  ArrayList<Hotel> hotels;
  ArrayList<String> hotelIds;
  Context context;
    SharedPreferences prefs;
    User u;
    boolean isAdmin=false;
    public hotel_list_adapter(ArrayList<Hotel> hotels, ArrayList<String> hotelIds, Context context) {
        this.hotels = hotels;
        this.hotelIds = hotelIds;
        this.context = context;
        prefs= PreferenceManager.getDefaultSharedPreferences(context);
        u=new Gson().fromJson(prefs.getString("user_info",null), User.class);
        isAdmin=prefs.getString("user_role",null)!=null;
    }

    @NonNull
    @Override
    public hotel_list_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new hotel_list_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_for_booking_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull hotel_list_viewholder holder, int position) {
       holder.hotelName.setText(hotels.get(position).getName());
       holder.hotelPerNightRent.setText("Rs "+hotels.get(position).getPerNightRent());
       holder.hotelImage.setImageBitmap(utils.getBitmapFromBytes(hotels.get(position).getImage().toBytes()));
       holder.card.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!isAdmin){
                   prefs.edit().putString("selected_hotel",hotels.get(position).getName()).apply();
                   prefs.edit().putInt("selected_hotel_rent",hotels.get(position).getPerNightRent()).apply();
                   context.startActivity(new Intent(context, vehicle_list.class));
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    class hotel_list_viewholder extends RecyclerView.ViewHolder{
         TextView hotelName,hotelPerNightRent;
         ImageView hotelImage;
         CardView card;
        public hotel_list_viewholder(@NonNull View itemView) {
            super(itemView);
            hotelName=itemView.findViewById(R.id.name);
            hotelPerNightRent=itemView.findViewById(R.id.price);
            hotelImage=itemView.findViewById(R.id.hotel_image);
            card=itemView.findViewById(R.id.card);
        }
    }
}
