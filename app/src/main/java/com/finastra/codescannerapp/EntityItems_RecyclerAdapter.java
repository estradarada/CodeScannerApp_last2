package com.finastra.codescannerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EntityItems_RecyclerAdapter extends RecyclerView.Adapter<EntityItems_RecyclerAdapter.MyViewHolder> {

    Context context;
    List<EntityItems> entityItemsList;


    public EntityItems_RecyclerAdapter(Context context, List<EntityItems> entityItemsList) {
        this.context = context;
        this.entityItemsList = entityItemsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.popup_recycler_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        TypeConverter typeConverter = new TypeConverter();
       Long lll = entityItemsList.get(position).getDate();
        Date date = typeConverter.toDate(lll);

        DateFormat dateFormat =
               new SimpleDateFormat("HH:mm, dd.MM.yyyy");

        String formattedTime = dateFormat.format(date);


        holder.img_user
                .setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        holder.container
                .setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));

        holder.name.setText(entityItemsList.get(position).getObjectName());
        holder.worker08.setText(entityItemsList.get(position).getWorker08());
        holder.date.setText(formattedTime);
        holder.img_user.setImageResource(R.drawable.el);




    }

    @Override
    public int getItemCount() {
        return entityItemsList.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, worker08, date;
        ImageView img_user;
        RelativeLayout container;



        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.objectName);
            worker08 = itemView.findViewById(R.id.worker);
            date = itemView.findViewById(R.id.popup_entity_date);
            img_user = itemView.findViewById(R.id.img_user);
            container = itemView.findViewById(R.id.container);

        }



    }




}
