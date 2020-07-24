package com.nta.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nta.app.R;
import com.squareup.picasso.Picasso;

import com.nta.app.api.API;
import com.nta.app.api.RetrofitClient;
import com.nta.app.model.Phone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail extends AppCompatActivity {

    ImageView image, btnBack;
    TextView tvManufacturer, tvName, tvPrice, tvDisplay, tvSystem, tvCamera, tvCPU, tvDisk1, tvDisk2, tvSim, tvPin;
    Button btnEdit, btnDelete;
    Phone phone = new Phone();
    RetrofitClient retrofit = new RetrofitClient();
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        final String idPhone = intent.getStringExtra("id");
        //Toast.makeText(this, idWatch, Toast.LENGTH_SHORT).show();

        //Ánh xạ:
        image = findViewById(R.id.image);
        btnBack = findViewById(R.id.btnBack);
        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvManufacturer = findViewById(R.id.tvManufacturer);
        tvDisplay = findViewById(R.id.tvDisplay);
        tvSystem = findViewById(R.id.tvSystem);
        tvCamera = findViewById(R.id.tvCamera);
        tvCPU = findViewById(R.id.tvCPU);
        tvDisk1 = findViewById(R.id.tvDisk1);
        tvDisk2 = findViewById(R.id.tvDisk2);
        tvSim = findViewById(R.id.tvSim);
        tvPin = findViewById(R.id.tvPin);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        api = retrofit.getClient().create(API.class);

        for (Phone item : MainActivity.phoneList) {
            if (item.get_id().equals(idPhone)) {
                Picasso.with(Detail.this).load("http://192.168.1.13:3000/" + item.getImage()).into(image);
                tvManufacturer.setText(item.getManufacturer());
                tvName.setText(item.getName());
//                tvPrice.setText(String.valueOf(item.getPrice()) + "đ");
                tvPrice.setText(item.getPrice()+"đ");
                tvDisplay.setText(item.getDisplay().trim());
                tvSystem.setText(item.getSystem().trim());
                tvCamera.setText(item.getCamera().trim());
                tvCPU.setText(item.getCpu().trim());
                tvDisk1.setText(item.getDisk1().trim());
                tvDisk2.setText(item.getDisk2().trim());
                tvSim.setText(item.getSim().trim());
                tvPin.setText(item.getPin().trim());

//                phone.setPrice(item.getPrice());
                phone.setImage(item.getImage());
            }
        }

        //event
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //edit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(Detail.this);
                dialog.setContentView(R.layout.edit_dialog);
                dialog.setTitle("Title...");

                // set the custom dialog components - text, image and button
                final EditText edtPrice = dialog.findViewById(R.id.edtPrice);
                ImageView image = dialog.findViewById(R.id.image);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnOK = dialog.findViewById(R.id.btnOK);

                Picasso.with(Detail.this).load("http://192.168.1.13:3000/" + phone.getImage()).into(image);
                dialog.show();
//                edtPrice.setText(String.valueOf(phone.getPrice()));
                edtPrice.setText(phone.getPrice());

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Phone item = new Phone();
//                        item.setPrice(Integer.parseInt(edtPrice.getText().toString()));
                        item.setPrice(edtPrice.getText());

                        api.editPhone(idPhone, item).enqueue(new Callback<Phone>() {
                            @Override
                            public void onResponse(Call<Phone> call, Response<Phone> response) {
                                if (response.isSuccessful()) {
                                    dialog.cancel();
                                    Intent i = new Intent(Detail.this, MainActivity.class);
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onFailure(Call<Phone> call, Throwable t) {

                            }
                        });
                    }
                });

            }
        });

        //delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
                builder.setMessage("Bạn muốn xoá sản phẩm này?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int id) {
                                api.deletePhone(idPhone).enqueue(new Callback<Phone>() {
                                    @Override
                                    public void onResponse(Call<Phone> call, Response<Phone> response) {
                                        if (response.isSuccessful()) {
                                            dialog.cancel();
                                            Intent i = new Intent(Detail.this, MainActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Phone> call, Throwable t) {

                                    }
                                });
                            }
                        });

                builder.setNegativeButton(
                        "Đóng",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder.create();
                alert11.show();

            }
        });
    }
}
