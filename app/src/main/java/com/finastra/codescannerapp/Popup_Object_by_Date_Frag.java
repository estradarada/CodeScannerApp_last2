package com.finastra.codescannerapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Popup_Object_by_Date_Frag extends BottomSheetDialogFragment {

    public final static String TAG = "Popup_ObjectByDate_Frag";
    public final static String KEY = "id";

    EditText edTxt;
    EditText edTxt2;
    Button btn_find;

    Date dateBefore;
    Date dateAfter;

    String ttt;

    Context context;
    RoomDataBase entityItemsRoomDataBase;
    List<EntityItems> entityItemsList;
    RecyclerView recyclerView;
    EntityItems_RecyclerAdapter entityItems_recyclerAdapter;


    public Popup_Object_by_Date_Frag(Context context, List<EntityItems> entityItemsList) {
        this.context = context;
        this.entityItemsList = entityItemsList;
    }


    public Popup_Object_by_Date_Frag() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        entityItemsRoomDataBase = Room.databaseBuilder(getContext(), RoomDataBase.class, "itemsDB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();


        final DatePickerDialog.OnDateSetListener startListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String currentDateString = DateFormat.getDateInstance().format(c.getTime());

                edTxt.setText(currentDateString);

                TypeConverter typeConverter = new TypeConverter();
                dateBefore = typeConverter.calendarToDate(c);

            }
        };


        final DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar c_2 = Calendar.getInstance();
                c_2.set(Calendar.YEAR, year);
                c_2.set(Calendar.MONTH, month);
                c_2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String currentDateString = DateFormat.getDateInstance().format(c_2.getTime());

                edTxt2.setText(currentDateString);

                TypeConverter typeConverter = new TypeConverter();
                dateAfter = typeConverter.calendarToDate(c_2);

            }
        };


        View v;
        v = inflater.inflate(R.layout.popup_object_frag, container, false);

        edTxt = v.findViewById(R.id.edTxt_PopUpObject_by_date_Frag);
        edTxt2 = v.findViewById(R.id.edTxt_2_PopUpObject_by_date_Frag);
        btn_find = v.findViewById(R.id.btn_find_PopUpObject_by_date_Frag);
        btn_find.setTextColor((getResources().getColor(R.color.title_text_color)));

        // ttt = getArguments().getString("id");

        entityItemsList = new ArrayList<>();
        entityItemsList = entityItemsRoomDataBase.itemsDao()
                .getAllEntityItems();

        entityItems_recyclerAdapter = new EntityItems_RecyclerAdapter(getContext(), entityItemsList);

        recyclerView = v.findViewById(R.id.object_by_date_popup_frag_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(entityItems_recyclerAdapter);


        edTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerFragment dpf = new DatePickerFragment();
                FragmentManager fmg = getFragmentManager();
                fmg.beginTransaction().add(dpf, "Date_Picker_Fragment").commit();

                dpf.setListener(startListener);


            }

        });


        edTxt2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                DatePickerFragment_2 dpf_2 = new DatePickerFragment_2();
                FragmentManager fmg = getFragmentManager();
                fmg.beginTransaction().add(dpf_2, "Date_Picker_Fragment_2").commit();

                dpf_2.setListener(endListener);


            }
        });


        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                entityItemsList = new ArrayList<>();
                entityItemsList = entityItemsRoomDataBase.itemsDao()
                        .getDateEntityItem(dateBefore, dateAfter);
                Log.d(TAG, "btn_find" + " " + entityItemsList.size());


                entityItems_recyclerAdapter =
                        new EntityItems_RecyclerAdapter(getContext(), entityItemsList);
                recyclerView.setAdapter(entityItems_recyclerAdapter);


                if (entityItemsList.isEmpty()) {

                    Toast.makeText(getContext(), "Объектов за данный период нет",
                            Toast.LENGTH_LONG).show();

                }
            }
        });


        return v;


    }


    public void sendData(String data) {
        if (data != null)
            ttt = data;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}






