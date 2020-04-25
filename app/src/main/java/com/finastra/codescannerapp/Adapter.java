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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class Adapter extends FirestoreRecyclerAdapter<Items,
        Adapter.ViewHolder> {

    public CheckListener mCheckListener;

    Context mContext;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Adapter(@NonNull FirestoreRecyclerOptions<Items> options) {
        super(options);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_layout,
                parent, false);
        mContext = parent.getContext();

        return new ViewHolder(v, mCheckListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Items model) {


        holder.img_user
                .setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        holder.staff_container
                .setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));


        holder.staff_name.setText(model.getObjectName());
        holder.img_user.setImageResource(R.drawable.el);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CheckListener mCheckListener;

        TextView staff_name;
        ImageView img_user;
        RelativeLayout staff_container;


        public ViewHolder(@NonNull View itemView, final CheckListener checkListener) {
            super(itemView);
            this.mCheckListener = checkListener;


            staff_name = itemView.findViewById(R.id.name);
            img_user = itemView.findViewById(R.id.img_user);
            staff_container = itemView.findViewById(R.id.container_rel);


            staff_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    mCheckListener.CheckClick(getSnapshots().getSnapshot(position), position);


                }
            });


        }
    }


    public interface CheckListener {

        void CheckClick(DocumentSnapshot documentSnapshot, int position);

    }


    public void setCheckListener(Context context, CheckListener checkListener) {

        this.mCheckListener = checkListener;
        this.mContext = context;

    }


}
