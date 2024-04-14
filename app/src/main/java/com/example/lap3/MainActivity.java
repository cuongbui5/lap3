package com.example.lap3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.lap3.activity.AddActivity;
import com.example.lap3.adapter.ThiSinhAdapter;
import com.example.lap3.db.MyDB;
import com.example.lap3.model.ThiSinh;
import com.example.lap3.receiver.MyBroadcastReceiver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyDB myDB;
    private RecyclerView rcvThiSinh;
    private FloatingActionButton btnAdd;
    private ActivityResultLauncher<Intent> activityLauncher;
    private ThiSinhAdapter thiSinhAdapter;
    private Toolbar toolbar;
    private List<ThiSinh> thiSinhs=new ArrayList<>();
    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.tang){
            thiSinhAdapter.sapXepTangTheoTongDiem();
        }else {
            thiSinhAdapter.sapXepGiamTheoTongDiem();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {
            ThiSinh thiSinh=thiSinhAdapter.getThiSinh(item.getGroupId());
            myDB.deleteThiSinh(thiSinh.getSbd());
            thiSinhs=myDB.getAll();
            thiSinhAdapter.setThiSinhs(thiSinhs);
            thiSinhAdapter.notifyDataSetChanged();



        } else if (id == R.id.edit) {
            ThiSinh thiSinh=thiSinhAdapter.getThiSinh(item.getGroupId());
            Intent intent=new Intent(this,AddActivity.class);
            intent.putExtra("data",thiSinh);
            activityLauncher.launch(intent);

        }

        return super.onContextItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rcvThiSinh=findViewById(R.id.rcvThiSinh);
        rcvThiSinh.setLayoutManager(new LinearLayoutManager(this));
        rcvThiSinh.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        myDB = new MyDB(this, "MyDB", null, 1);
        myDB.initData();
        thiSinhs=myDB.getAll();
        thiSinhAdapter=new ThiSinhAdapter(thiSinhs);
        rcvThiSinh.setAdapter(thiSinhAdapter);
        btnAdd=findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddActivity.class);
                activityLauncher.launch(intent);
            }
        });
        activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        thiSinhs=myDB.getAll();
                        thiSinhAdapter.setThiSinhs(thiSinhs);
                        thiSinhAdapter.notifyDataSetChanged();

                    }
                });

        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myBroadcastReceiver, filter);





    }
}