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

import fyp.tourguider.Book_Tour;
import fyp.tourguider.Model.User;
import fyp.tourguider.Model.Vehicle;
import fyp.tourguider.R;
import fyp.tourguider.Utils.utils;

public class vehicle_list_adapter extends RecyclerView.Adapter<vehicle_list_adapter.vehicle_list_viewholder> {
    ArrayList<Vehicle> vehicles;
    ArrayList<String> vehicleId;
    Context context;
    SharedPreferences prefs;
    boolean isAdmin;
    User u;
    public vehicle_list_adapter(ArrayList<Vehicle> vehicles, ArrayList<String> vehicleId, Context context) {
        this.vehicles = vehicles;
        this.vehicleId = vehicleId;
        this.context = context;
        prefs= PreferenceManager.getDefaultSharedPreferences(context);
        u=new Gson().fromJson(prefs.getString("user_info",null), User.class);
        isAdmin=prefs.getString("user_role",null)!=null;
    }

    @NonNull
    @Override
    public vehicle_list_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new vehicle_list_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull vehicle_list_viewholder holder, int position) {
        holder.name.setText(vehicles.get(position).getName());
        holder.perDayPrice.setText("Per Day Rs "+String.valueOf(vehicles.get(position).getPerDayRent()));
        holder.vehicleImage.setImageBitmap(utils.getBitmapFromBytes(vehicles.get(position).getImage().toBytes()));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAdmin) {
                    prefs.edit().putString("selected_vehicle", vehicles.get(position).getName()).apply();
                    prefs.edit().putInt("selected_vehicle_rent", vehicles.get(position).getPerDayRent()).apply();
                    context.startActivity(new Intent(context, Book_Tour.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    class vehicle_list_viewholder extends RecyclerView.ViewHolder{
        TextView name,perDayPrice;
        ImageView vehicleImage;
        CardView card;
        public vehicle_list_viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.vehicleName);
            perDayPrice=itemView.findViewById(R.id.perDayRent);
            vehicleImage=itemView.findViewById(R.id.icon);
            card=itemView.findViewById(R.id.card);
        }
    }
}
