package fyp.tourguider.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import fyp.tourguider.Add_Hotel;
import fyp.tourguider.Model.City;
import fyp.tourguider.Model.TouristDestination;
import fyp.tourguider.Model.User;
import fyp.tourguider.R;
import fyp.tourguider.TouristDestinations;
import fyp.tourguider.add_tourist_spot;

public class cities_list_adapter extends RecyclerView.Adapter<cities_list_adapter.cities_list_viewholder> {
    ArrayList<City> cities;
    ArrayList<String> citiesId;
    Context context;
    SharedPreferences prefs;
    User u;
    boolean isAdmin=false;
    public cities_list_adapter(ArrayList<City> cities, ArrayList<String> citiesId, Context context) {
        this.cities = cities;
        this.citiesId = citiesId;
        this.context = context;
        prefs= PreferenceManager.getDefaultSharedPreferences(context);
         u=new Gson().fromJson(prefs.getString("user_info",null),User.class);
         isAdmin=prefs.getString("user_role",null)!=null;
    }

    @NonNull
    @Override
    public cities_list_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new cities_list_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull cities_list_viewholder holder, int position) {
            holder.cityName.setText(cities.get(position).getCityName());
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color1 = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(cities.get(position).getCityName().substring(0,1), color1);
        holder.firstLetter.setImageDrawable(drawable);
       holder.card.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              if(isAdmin){
                  PopupMenu popup=new PopupMenu(context,v);
                  popup.getMenuInflater().inflate(R.menu.admin_popup,popup.getMenu());
                  popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                      @Override
                      public boolean onMenuItemClick(MenuItem item) {
                          if(item.getItemId()==R.id.add_tourist_spot){
                              context.startActivity(new Intent(context, add_tourist_spot.class).putExtra("city",cities.get(position).getCityName()).putExtra("CityId",citiesId.get(position)));
                          }else if(item.getItemId()==R.id.add_hotel){
                              context.startActivity(new Intent(context, Add_Hotel.class).putExtra("city",cities.get(position).getCityName()).putExtra("CityId",citiesId.get(position)));
                          }
                          return true;
                      }
                  });
                  popup.show();
              }else if(u.getRole().equals("Customer")){
                  context.startActivity(new Intent(context, TouristDestinations.class).putExtra("city",cities.get(position).getCityName()).putExtra("CityId",citiesId.get(position)));
              }
           }
       });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class cities_list_viewholder extends RecyclerView.ViewHolder{
    TextView cityName;
    ImageView firstLetter;
    CardView card;
        public cities_list_viewholder(@NonNull View itemView) {
            super(itemView);
            cityName=itemView.findViewById(R.id.cityName);
            firstLetter=itemView.findViewById(R.id.first_letter);
            card=itemView.findViewById(R.id.card);
        }
    }
}
