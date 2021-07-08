package sg.edu.rp.c346.id19036308.locatingaplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Button btnNorth, btnCentral, btnEast;
    private GoogleMap map;
    LatLng poi_north, poi_central, poi_east, poi_sg;
    Spinner spinnerDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEast = findViewById(R.id.btnEast);
        btnCentral = findViewById(R.id.btnCentral);
        btnNorth = findViewById(R.id.btnNorth);

        poi_north = new LatLng(1.45317, 103.82504);
        poi_central = new LatLng(1.304833, 103.831833);
        poi_east = new LatLng(1.3493751, 103.936593633113);
        poi_sg = new LatLng(1.3521, 103.8198);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        spinnerDirection = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.direction, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerDirection.setAdapter(adapter);

        spinnerDirection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = spinnerDirection.getSelectedItem().toString();
                if (selected.equals("HQ - North")) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north, 15));
                } else if (selected.equals("Central")) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central, 15));
                } else if (selected.equals("East")) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east, 15));
                } else {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_sg, 10));
                }


                Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                UiSettings ui = map.getUiSettings();
                ui.setZoomControlsEnabled(true);
                ui.setCompassEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED){
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
                Marker north = map.addMarker(new MarkerOptions().position(poi_north).title("HQ - North").snippet("Block 333, Admiralty Ave 3, 765654 Operating hours: 10am-5pm" +
                        "Tel:65433456").icon(BitmapDescriptorFactory.fromResource(android.R.drawable.btn_star_big_on)));
                Marker central = map.addMarker(new MarkerOptions().position(poi_central).title("Central").snippet("Block 3A, Orchard Ave 3, 134542 \n" +
                        "Operating hours: 11am-8pm" +
                        "Tel:67788652").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                Marker east = map.addMarker(new MarkerOptions().position(poi_east).title("East").snippet("Block 555, Tampines Ave 3, 287788 \n" +
                        "Operating hours: 9am-5pm" +
                        "Tel:66776677").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_sg, 10));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(MainActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });


            }
        });



        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                poi_north = new LatLng(1.436065, 103.786263);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north,15));
                Toast.makeText(MainActivity.this, "HQ - North", Toast.LENGTH_SHORT).show();

            }
        });

        btnCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                poi_central = new LatLng(1.436065, 103.786263);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central, 15));
                Toast.makeText(MainActivity.this, "Central", Toast.LENGTH_SHORT).show();
            }
        });

        btnEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                poi_east = new LatLng(1.436065, 103.786263);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east, 15));
                Toast.makeText(MainActivity.this, "East", Toast.LENGTH_SHORT).show();
            }
        });
    }
}