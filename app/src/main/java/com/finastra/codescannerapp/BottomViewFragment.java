package com.finastra.codescannerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BottomViewFragment extends BottomSheetDialogFragment {

    RoomDataBase entityItemsRoomDataBase;
    EntityItems entityItems = new EntityItems();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = db.collection("Items");

    public interface TextClicked {
        void sendText(String text);
    }

    TextClicked mCallback;


    Adapter adapter;
    RecyclerView recyclerView;
    String name;


    public BottomViewFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v;

        v = inflater.inflate(R.layout.bottom_view_fragment, container, false);

        Query query = reference.orderBy("objectName", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Items> options = new FirestoreRecyclerOptions.Builder<Items>()
                .setQuery(query, Items.class)
                .build();

        adapter = new Adapter(options);

        recyclerView = v.findViewById(R.id.scan_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setCheckListener(getContext(), new Adapter.CheckListener() {
            @Override
            public void CheckClick(DocumentSnapshot documentSnapshot, int position) {

                Items items = documentSnapshot.toObject(Items.class);
                String docID = documentSnapshot.getId();
                name = items.getObjectName();
                String worker08 = items.getWorker08();


                mCallback.sendText(name);

                entityItems.setObjectName(name);
                entityItems.setItem_id(docID);
                entityItems.setWorker08(worker08);


                entityItemsRoomDataBase.itemsDao().insert(entityItems);
                Log.d(TAG, "insertLastEntityItem" + " " + name);

                Intent intent = new Intent(getContext(), MainActivity.class);
// set the new task and clear flags
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        entityItemsRoomDataBase = Room.databaseBuilder(getContext(), RoomDataBase.class, "itemsDB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();

    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try {
            mCallback = (TextClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TextClicked");
        }

    }

    @Override
    public void onDetach() {
        mCallback = null; // => avoid leaking, thanks @Deepscorn
        super.onDetach();
    }


}
