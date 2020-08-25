package fyp.tourguider.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;
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

import com.google.firebase.firestore.Blob;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import fyp.tourguider.Model.TouristDestination;
import fyp.tourguider.R;
import fyp.tourguider.Utils.utils;
import fyp.tourguider.tour_guiders_list;

public class tourist_destination_list_adapter extends RecyclerView.Adapter<tourist_destination_list_adapter.tourist_destination_list_viewholder> {
 ArrayList<TouristDestination> touristDestinations;
 Context context;
 ArrayList<String> touristDestinationId;
 String city,cityId;
 SharedPreferences prefs;
    public tourist_destination_list_adapter(ArrayList<TouristDestination> touristDestinations, Context context, ArrayList<String> touristDestinationId,String city,String cityId) {
        this.touristDestinations = touristDestinations;
        this.context = context;
        this.touristDestinationId = touristDestinationId;
        this.city=city;
        this.cityId=cityId;
        prefs= PreferenceManager.getDefaultSharedPreferences(context);
    }

    @NonNull
    @Override
    public tourist_destination_list_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new tourist_destination_list_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull tourist_destination_list_viewholder holder, int position) {
      holder.destinationName.setText(touristDestinations.get(position).getName());
      holder.destinationImage.setImageBitmap(utils.getBitmapFromBytes(touristDestinations.get(position).getImage().toBytes()));
      holder.card.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              PopupMenu popup=new PopupMenu(context,v);
              popup.getMenuInflater().inflate(R.menu.desitination_popup,popup.getMenu());
              popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                  @Override
                  public boolean onMenuItemClick(MenuItem item) {
                      if(item.getItemId()==R.id.book_guides){
                          prefs.edit().putString("selected_destination",touristDestinations.get(position).getName()).apply();
                          context.startActivity(new Intent(context, tour_guiders_list.class).putExtra("city",city).putExtra("CityId",cityId).putExtra("destinationName",touristDestinations.get(position).getName()));
                      }else if(item.getItemId()==R.id.get_directions){
                          try{
                              Geocoder g=new Geocoder(context);
                              List<Address> addresses=g.getFromLocationName(touristDestinations.get(position).getName(),1);
                              Log.e("location",String.valueOf(addresses.get(0).getLatitude()+","+addresses.get(0).getLatitude()+","+addresses.get(0).getLongitude()));
                              Uri gmmIntentUri = Uri.parse("google.navigation:q="+addresses.get(0).getLatitude()+","+addresses.get(0).getLongitude());
                              Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                              mapIntent.setPackage("com.google.android.apps.maps");
                              context.startActivity(mapIntent);
                          }catch (Exception e){
                              e.printStackTrace();
                          }
                      }
                      return true;
                  }
              });
                popup.show();
          }
      });
    }

    @Override
    public int getItemCount() {
        return touristDestinations.size();
    }

    class tourist_destination_list_viewholder extends RecyclerView.ViewHolder{
      TextView destinationName;
      ImageView destinationImage;
      CardView card;
        public tourist_destination_list_viewholder(@NonNull View itemView) {
            super(itemView);
            destinationName=itemView.findViewById(R.id.name);
            destinationImage=itemView.findViewById(R.id.hotel_image);
            card=itemView.findViewById(R.id.card);
        }
    }
}
