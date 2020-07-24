package com.nta.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.nta.app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.nta.app.activity.Detail;
import com.nta.app.model.Phone;

public class PhoneAdapter extends BaseAdapter {
    Activity context;
    int resource;
    List<Phone> data;

    public PhoneAdapter(Activity context, int resource, List<Phone> data) {
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = this.context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.phone_item, null);
            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.tvManufacturer = convertView.findViewById(R.id.tvManufacturer);
            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            viewHolder.tvPrice = convertView.findViewById(R.id.tvPrice);
            viewHolder.item = convertView.findViewById(R.id.item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Phone Phone = this.data.get(position);
        viewHolder.tvManufacturer.setText(Phone.getManufacturer());
        viewHolder.tvName.setText(Phone.getName());
//        viewHolder.tvPrice.setText(String.valueOf(Phone.getPrice())+"đ");
        viewHolder.tvPrice.setText(Phone.getPrice()+"đ");
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail.class);
                intent.putExtra("id", data.get(position).get_id());
                context.startActivity(intent);
            }
        });

        try {
            Picasso.with(context).load("http://192.168.1.13:3000/" + Phone.getImage()).into(viewHolder.image);
        } catch (Exception e) {

        }

        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        TextView tvManufacturer, tvName, tvPrice;
        CardView item;
    }
}
