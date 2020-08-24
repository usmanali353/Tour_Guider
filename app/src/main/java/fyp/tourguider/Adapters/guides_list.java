package fyp.tourguider.Adapters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Calendar;

import fyp.tourguider.Model.User;
import fyp.tourguider.R;
import fyp.tourguider.hotels_list;
import fyp.tourguider.vehicle_list;

public class guides_list extends RecyclerView.Adapter<guides_list.guides_list_viewholder> {
 ArrayList<User> users;
 ArrayList<String> userId;
 Context context;
 String cityId;
 SharedPreferences prefs;
    public guides_list(ArrayList<User> users, ArrayList<String> userId, Context context,String cityId) {
        this.users = users;
        this.userId = userId;
        this.context = context;
        this.cityId=cityId;
        prefs= PreferenceManager.getDefaultSharedPreferences(context);
    }

    @NonNull
    @Override
    public guides_list_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new guides_list_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.users_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull guides_list_viewholder holder, int position) {
       holder.email.setText(users.get(position).getEmail());
       holder.name.setText(users.get(position).getName());
       holder.phone.setText(users.get(position).getPhone());
       holder.role.setText("Rate: "+String.valueOf("Rs "+users.get(position).getHourlyRate()));
       holder.card.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DatePickerDialog datePickerDialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       View v=LayoutInflater.from(context).inflate(R.layout.add_days_of_tour,null);
                       MaterialEditText days=v.findViewById(R.id.days);
                       AlertDialog addDays=new AlertDialog.Builder(context)
                               .setTitle("Add Days")
                               .setMessage("How Many Days Tour Will Continue")
                               .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                   }
                               }).setView(v).create();
                       addDays.show();
                       addDays.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               if(days.getText().toString().isEmpty()){
                                   days.setError("Enter Number of Days");
                               }else if(Integer.parseInt(days.getText().toString())==0){
                                   days.setError("days too low");
                               }else{
                                   prefs.edit().putString("days",days.getText().toString()).apply();
                                   prefs.edit().putString("selected_guide",new Gson().toJson(users.get(position))).apply();
                                   prefs.edit().putString("selected_guide_id",userId.get(position)).apply();
                                   context.startActivity(new Intent(context, hotels_list.class).putExtra("CityId",cityId));
                               }
                           }
                       });
                       Log.e("start_date",String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                       prefs.edit().putString("start_date",String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year)).apply();
                   }
               }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
               datePickerDialog.setTitle("Select Event Date");
               datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-3000);
               datePickerDialog.show();

           }
       });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class guides_list_viewholder extends RecyclerView.ViewHolder{
        TextView email,name,phone,role;
        CardView card;
        public guides_list_viewholder(@NonNull View itemView) {
            super(itemView);
            email=itemView.findViewById(R.id.email);
            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            role=itemView.findViewById(R.id.role);
            card=itemView.findViewById(R.id.card);
        }
    }

}
