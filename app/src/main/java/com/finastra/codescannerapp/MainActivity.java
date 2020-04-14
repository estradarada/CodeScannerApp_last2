package com.finastra.codescannerapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BottomViewFragment.TextClicked {

    RoomDataBase entityItemsRoomDataBase;
    EntityItems entityItems = new EntityItems();
    List<EntityItems> entityItemsList;


    public final static String TO = "MainActivity_Scanner";
    public final static String KEY = "id";

    private static final String TAG = "java";
    Button btn_send;
    Button btn_scan;
    TextView object_name;
    String send;
    String sent_name;
    String sent_id = "0d6LuS37ftzHIY3GQk6N";
    String timeTextSnap;


    public static TextView result_text;
    ImageView scan_btn;
    private static final int REQUEST_CAMERA = 1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = db.collection("Items");
    Adapter adapter;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied! F*** u", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo == null) {

            Toast.makeText(this, "Проверьте интерент соединение", Toast.LENGTH_LONG).show();
        }


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        entityItemsRoomDataBase = Room.databaseBuilder(this, RoomDataBase.class, "itemsDB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();

        entityItemsList = new ArrayList<>();
        entityItemsList = entityItemsRoomDataBase.itemsDao().getLastEntityItem();


        for (EntityItems entityItem : entityItemsList
        ) {

            sent_name = entityItem.getObjectName();
            sent_id = entityItem.getItem_id();
            Log.d(TAG, "getLastEntityItem" + " " + sent_name + " " + entityItemsList.size());


        }


        Query query = reference.orderBy("objectName", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Items> options = new FirestoreRecyclerOptions.Builder<Items>()
                .setQuery(query, Items.class)
                .build();

        adapter = new Adapter(options);


        result_text = findViewById(R.id.result_text);
        object_name = findViewById(R.id.object_name);
        btn_scan = findViewById(R.id.btn_scan);
        btn_send = findViewById(R.id.btn_send);

        object_name.setText(sent_name);


        final DocumentReference documentReference = reference.document(sent_id);
/*
        documentReference.get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String objectName = documentSnapshot.get("objectName").toString();
                object_name.setText(objectName);

            }
        });



 */


        //If the permission is not  already granted then ask,if it is already granted then just do nothing for the permission section
        if (!(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
            //After this function call,it will ask for permission and whether it granted or not,this response is handle in onRequestPermissionsResult() which we overrided.
        }

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                }

            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result_text.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(),
                            "Отсканируйте", Toast.LENGTH_LONG).show();
                    return;

                } else {

                    Date currentDateSnap = new Date();
                    DateFormat timeFormatSnap = new SimpleDateFormat("HH:mm, dd.MM.YYYY", Locale.getDefault());
                    timeTextSnap = timeFormatSnap.format(currentDateSnap.getTime());
                    send = result_text.getText().toString();

                    documentReference.update("worker08", send, "worker15", timeTextSnap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(getApplicationContext(),
                                            "Сообщение отправлено!", Toast.LENGTH_LONG).show();
                                    result_text.setText("");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(),
                                    "Ошибка" + e, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:

                BottomViewFragment bottomViewFragment = new BottomViewFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(bottomViewFragment, "bottomViewFragment").commit();
                getSupportFragmentManager().popBackStack();


                return true;
            default:
                return super.onOptionsItemSelected(item);


        }


    }


    @Override
    public void sendText(String text) {
        // object_name.setText(text);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }


}
