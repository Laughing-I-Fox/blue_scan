package com.fox.blue_scan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fox.blue_scan.adapter.BtConst;

public class MainActivity extends AppCompatActivity {

    private MenuItem menuItem;
    private BluetoothAdapter btAdapter;
    private final int ENABLE_REQUEST = 15;
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuItem = menu.findItem(R.id.id_bt_button);
        setBtIcon();
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("MissingPermission")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.id_bt_button){
           if(!btAdapter.isEnabled()){
               enableBt();
           } else {
               btAdapter.disable();
               menuItem.setIcon(R.drawable.ic_bt_enable);
           }
        } else if(item.getItemId() == R.id.id_menu){
            if(btAdapter.isEnabled()){

            Intent i = new Intent(MainActivity.this, BtListActivity.class);
            startActivity(i);

            } else {
                Toast.makeText(this, "Bluetooth is disabled", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ENABLE_REQUEST){

            if(resultCode == RESULT_OK){
                setBtIcon();

            }
        }
    }

    private void setBtIcon(){
        if(btAdapter.isEnabled()){
            menuItem.setIcon(R.drawable.ic_bt_disable);
        } else {
            menuItem.setIcon(R.drawable.ic_bt_enable);
        }
    }

    private void init(){

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pref = getSharedPreferences(BtConst.MY_PREF, MODE_PRIVATE);
    }
    @SuppressLint("MissingPermission")
    private void enableBt(){

        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(i, ENABLE_REQUEST);
    }
}