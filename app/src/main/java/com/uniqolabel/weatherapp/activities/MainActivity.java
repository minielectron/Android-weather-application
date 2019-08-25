package com.uniqolabel.weatherapp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uniqolabel.weatherapp.R;
import com.uniqolabel.weatherapp.model.CurrentWeatherResponse;
import com.uniqolabel.weatherapp.services.GPSTracker;
import com.uniqolabel.weatherapp.viewmodel.WeatherViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int LOCATIO_REQUEST_CODE = 111;
    private static final String CELSIUS = "metric";
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.reload_button)
    Button reloadButton;
    @BindView(R.id.mask_frame)
    FrameLayout maskFrame;
    @BindView(R.id.temp_tv)
    TextView tempTv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.temp_range_tv)
    TextView tempRangeTv;
    @BindView(R.id.weather_description)
    TextView weatherDescription;
    private WeatherViewModel weatherViewModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        ;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        // Hi, All the very best :-) same to you :-)

        toolbarText.setText(getResources().getString(R.string.test_city_name));
        if (isNetworkAvailable()) {
            permissionRequest();
        } else {
            Toast.makeText(this, "Please turn on the internet !", Toast.LENGTH_SHORT).show();
        }

    }

    private void permissionRequest() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATIO_REQUEST_CODE);
        } else {
            getWeatherReport();
        }
    }

    private void getWeatherReport() {
        maskFrame.setVisibility(View.GONE);
        GPSTracker gpsTracker = new GPSTracker(this);
        Location location = gpsTracker.getLocation();
        if (location != null) {
            progressDialog.show();
            weatherViewModel.getCurrentWeatherInfoNetworkCall(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), CELSIUS, getResources().getString(R.string.appid)).observe(this, new Observer<CurrentWeatherResponse>() {
                @Override
                public void onChanged(@Nullable CurrentWeatherResponse weatherSuccessResponse) {
                    progressDialog.hide();
                    if (weatherSuccessResponse != null) {
                        tempTv.setText(String.format(Locale.ENGLISH,"%.2f",weatherSuccessResponse.getMain().getTemp()));
                        tempRangeTv.setText(String.format(Locale.ENGLISH, "%.0f~%.0f degree", weatherSuccessResponse.getMain().getTempMin(), weatherSuccessResponse.getMain().getTempMax()));
                        weatherDescription.setText(String.valueOf(weatherSuccessResponse.getWeather().get(0).getDescription()));
                        toolbarText.setText(weatherSuccessResponse.getName());
                    } else
                        Log.d(TAG, "onChanged: response is null");
                        Toast.makeText(MainActivity.this, "Response is null", Toast.LENGTH_SHORT).show();
                }
            });
            Log.d(TAG, "permissionRequest: long: " + location.getLongitude() + "\nLat: " + location.getLatitude());
        } else giveReloadOption("Turn on the GPS-Location service");
    }

    public void giveReloadOption(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        maskFrame.setVisibility(View.VISIBLE);
        progressDialog.hide();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATIO_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeatherReport();
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    showRationaleDialog();
                } else {
                    showRationaleDialogSecond();

                }
            }
        }
    }

    private void showRationaleDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Allow Location Access to use the application")
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Deny", null)
                .create();
        dialog.show();
    }

    private void showRationaleDialogSecond() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Location Access Permission is required for usage of this application.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);

                    }
                })
                .create();
        dialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.reload_button)
    public void onViewClicked() {
        permissionRequest();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
