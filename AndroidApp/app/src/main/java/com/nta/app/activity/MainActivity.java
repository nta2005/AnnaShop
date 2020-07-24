package com.nta.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.nta.app.R;

import java.util.ArrayList;
import java.util.List;

import com.nta.app.adapter.PhoneAdapter;
import com.nta.app.api.API;
import com.nta.app.api.RetrofitClient;
import com.nta.app.model.Phone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static List<Phone> phoneList = new ArrayList<>();
    List<Phone> searchlist;
    ListView lvPhones;
    PhoneAdapter phoneAdapter;

    EditText edtSearch;

    RetrofitClient retrofit = new RetrofitClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //anh xa
        edtSearch = findViewById(R.id.edtSearch);
        lvPhones = (ListView)findViewById(R.id.lvPhones);
        phoneAdapter = new PhoneAdapter(MainActivity.this,R.layout.phone_item,phoneList);
        lvPhones.setAdapter(phoneAdapter);

        getData();

        //event
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchlist = new ArrayList<>();
                if (s.length() == 0) {
                    searchlist = phoneList;
                } else {
                    for (Phone item : phoneList) {
                        if (item.getName().toLowerCase().contains(s.toString().toLowerCase())
                                || item.getManufacturer().toLowerCase().contains(s.toString().toLowerCase())) {
                            searchlist.add(item);
                        }
                    }
                }

                phoneAdapter = new PhoneAdapter(MainActivity.this,R.layout.phone_item, searchlist);
                lvPhones.setAdapter(phoneAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void getData() {
        //Creating an object of our api interface
        API api = retrofit.getClient().create(API.class);

        //Defining the method

        api.getPhones().enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                phoneList = response.body();

                if (response.isSuccessful()) {
                    phoneAdapter = new PhoneAdapter(MainActivity.this,R.layout.phone_item, phoneList);
                    lvPhones.setAdapter(phoneAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {

            }
        });
    }
}