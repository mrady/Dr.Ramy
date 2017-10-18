package ramymichealcenter.patient.helpers.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ramymichealcenter.patient.model.ServiceModel;

/**
 * Created by Mostafa on 9/26/2017.
 */

public class SpinnerServicesAdapter extends ArrayAdapter<ServiceModel> {

    private Context context;
    private ArrayList<ServiceModel> allServicesArrayList;

    public SpinnerServicesAdapter(Context context, int textViewResourceId, ArrayList<ServiceModel> allServicesArrayList) {
        super(context, textViewResourceId, allServicesArrayList);
        this.context = context;
        this.allServicesArrayList = allServicesArrayList;

    }

    public int getCount() {
        return allServicesArrayList.size();
    }

    public ServiceModel getItem(int position) {
        return allServicesArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

        @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }


    //    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        TextView label = new TextView(context);
//        label.setTextColor(Color.BLACK);
//        label.setText(allServicesArrayList.get(position).getServiceName());
//        label.setTextSize(20);
//        //label.setText(values.toArray(new Object[values.size()])[position].toString());
//        return label;
//    }
//
//    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        TextView label = new TextView(context);
//        label.setTextColor(Color.BLACK);
//        label.setText(allServicesArrayList.get(position).getServiceName());
//        label.setTextSize(20);
//        return label;
//    }

}