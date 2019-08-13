package com.endra.weatherapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.endra.weatherapi.remote.dto.ResponseWeather;
import com.endra.weatherapi.remote.retrofit.APIService;
import com.endra.weatherapi.remote.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txtWeatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtWeatherData=findViewById(R.id.txtWeatherData);

        APIService service= RetrofitClientInstance.getRetrofitInstance().create(APIService.class);

        Call<ResponseWeather> call=service.getCurrentWeatherData("0a62eb0850mshca3e4268d40ed16p10c9fcjsn440a76e8f997",
                "\"metric\"or\"imperial\"",
                "xml,html",
                "Dawei,MM");

        call.enqueue(new Callback<ResponseWeather>() {
            @Override
            public void onResponse(@NonNull Call<ResponseWeather> call, @NonNull Response<ResponseWeather> response) {
                if (response.isSuccessful()) {
                    ResponseWeather responseWeather = response.body();
                    assert responseWeather != null;

                    Log.d("MainActivity", "weather main:" + responseWeather.weatherList.get(0).main);
                    Log.d("MainActivity", "weather description:" + responseWeather.weatherList.get(0).description);
                    Log.d("MainActivity", "weather temp:" + responseWeather.main.temp);
                    Log.d("MainActivity", "weather temp min:" + responseWeather.main.tempMin);
                    Log.d("MainActivity", "weather temp max:" + responseWeather.main.tempMax);

                    txtWeatherData.setText("weather main:" + responseWeather.weatherList.get(0).main + "\n"
                            + "weather description:" + responseWeather.weatherList.get(0).description + "\n"
                            + "Temp:" + responseWeather.main.temp + "\n"
                            + "Temp min:" + responseWeather.main.tempMin + "\n"
                            + "Temp max:" + responseWeather.main.tempMax + "\n");

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseWeather> call, @NonNull Throwable t) {
                Log.d("MainActivity", String.format("Error: %s", t.getMessage()));
            }
        });
    }
}



