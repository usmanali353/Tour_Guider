package fyp.tourguider.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import fyp.tourguider.BookingDetailPage;
import fyp.tourguider.Model.Booking;
import fyp.tourguider.Model.User;
import fyp.tourguider.R;

public class orders_list_adapter extends RecyclerView.Adapter<orders_list_adapter.orders_list_viewholder> {
    ArrayList<Booking> bookings;
    Context context;
    ArrayList<String> bookingsId;
    SharedPreferences prefs;
    User u;
    public orders_list_adapter(ArrayList<Booking> bookings, Context context, ArrayList<String> orders_id) {
        this.bookings = bookings;
        this.context = context;
        this.bookingsId=orders_id;
        prefs= PreferenceManager.getDefaultSharedPreferences(context);
        u=new Gson().fromJson(prefs.getString("user_info",null),User.class);
    }

    @NonNull
    @Override
    public orders_list_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new orders_list_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull orders_list_viewholder holder, final int position) {
         holder.order_date.setText(bookings.get(position).getDate());
         holder.order_id.setText(bookings.get(position).getSelectedDestination());
         holder.order_price.setText("Rs "+bookings.get(position).getTotal());
         if(bookings.get(position).getStatus().equals("New Request")||bookings.get(position).getStatus().equals("Rejected")){
             holder.order_statue.setTextColor(Color.parseColor("#FF0000"));
         }else{
             holder.order_statue.setTextColor(Color.parseColor("#008000"));
         }
         holder.order_statue.setText(bookings.get(position).getStatus());
         holder.order_details.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 context.startActivity(new Intent(context, BookingDetailPage.class).putExtra("booking_id",bookingsId.get(position)).putExtra("booking_data",new Gson().toJson(bookings.get(position))));
             }
         });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    class orders_list_viewholder extends RecyclerView.ViewHolder{
        TextView order_date,order_id,order_price,order_details,order_statue;
        public orders_list_viewholder(@NonNull View itemView) {
            super(itemView);
            order_date=itemView.findViewById(R.id.order_date);
            order_id=itemView.findViewById(R.id.order_id);
            order_price=itemView.findViewById(R.id.order_price);
            order_details=itemView.findViewById(R.id.order_detail);
            order_statue=itemView.findViewById(R.id.order_status);
        }
    }
}
