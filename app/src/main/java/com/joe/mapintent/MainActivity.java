package com.joe.mapintent;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "Map Intent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText mapSearchBox = (EditText) findViewById(R.id.map_search_box);
        Button mapSearchButton = (Button) findViewById(R.id.map_search_button);

        mapSearchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String mapSearchString = mapSearchBox.getText().toString();

                if (mapSearchString.length() == 0) {
                    Snackbar.make(findViewById(android.R.id.content), "Enter a Location", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                Geocoder geocoder = new Geocoder(MainActivity.this);

                try{
                    List<Address> addressList = geocoder.getFromLocationName(mapSearchString, 1);

                    if (addressList.size() == 1){

                        Address firstAddress = addressList.get(0);

                        Log.d(TAG, "First Address is " + firstAddress);

                        String geoUriString = String.format("geo:%f,%f", firstAddress.getLatitude(), firstAddress.getLongitude());
                        Log.d(TAG, "Geo URI string is " + geoUriString);

                        Uri geoUri = Uri.parse(geoUriString);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);


                        Toast.makeText(MainActivity.this, "Launching Map", Toast.LENGTH_LONG);
                        startActivity(mapIntent);
                    }
                    else {

                        Snackbar.make(findViewById(android.R.id.content), "No results found for that location", Snackbar.LENGTH_LONG).show();
                    }
                } catch (IOException ioe) {

                    Log.e(TAG, "Error during geocoding", ioe);
                    Snackbar.make(findViewById(android.R.id.content), "Sorry an error occurred", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
