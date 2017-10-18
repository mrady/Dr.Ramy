package ramymichealcenter.patient.helpers.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ramymichealcenter.patient.R;
import ramymichealcenter.patient.model.ReservationModel;
import ramymichealcenter.patient.viewcontroller.ReservationDialogFragment;

/**
 * Created by Mostafa on 9/23/2017.
 */

public class ReservationGridViewAdapter extends BaseAdapter {

    private static final String TAG = ReservationGridViewAdapter.class.getCanonicalName();
    private Context context;
    private int layoutResourceId;
    private ArrayList<ReservationModel> reservationModelArrayList = new ArrayList<ReservationModel>();

    public ReservationGridViewAdapter(Context context, int layoutResourceId, ArrayList<ReservationModel> reservationModelArrayList) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.reservationModelArrayList = reservationModelArrayList;
    }

    @Override
    public int getCount() {
        return reservationModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return reservationModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final MyViewHolder grid;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        if (convertView == null) {
            grid = new MyViewHolder();
            convertView = inflater.inflate(layoutResourceId, null);

            grid._linear = (LinearLayout) convertView.findViewById(R.id._linear);
            grid.txtTitle = (TextView) convertView.findViewById(R.id.item_text);

            convertView.setTag(grid);   // <<-- H E R E
        } else {
            grid = (MyViewHolder) convertView.getTag();   // <<-- H E R E
        }


        grid.txtTitle.setText(reservationModelArrayList.get(position).getReservationTime());

        if (reservationModelArrayList.get(position).getReserved()) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorDate_bg));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorDate_bg_unselected));
        }


//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (reservationModelArrayList.get(position).getReserved()) {
//                    displayPopupReserved(reservationModelArrayList.get(position), grid.txtTitle);
//                } else {
//                    displayPopupConfirmReservation(reservationModelArrayList.get(position), grid.txtTitle);
//                }
//            }
//        });
//

        return convertView;
    }


//    private void displayPopupConfirmReservation(ReservationModel reservationModel, final TextView txtTitle) {
//
//
//        txtTitle.setBackgroundColor(context.getResources().getColor(R.color.colorDate_bg));
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Are you sure");
//        builder.setMessage(reservationModel.getReservationTime() + " You want to book this time");
//        builder.setNegativeButton("NO",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        txtTitle.setBackgroundColor(context.getResources().getColor(R.color.colorDate_bg_unselected));
//                    }
//                });
//        builder.setPositiveButton("YES",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//        builder.show();
//
//    }
//
//    private void displayPopupReserved(ReservationModel reservationModel, TextView txtTitle) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Error");
//        builder.setMessage(reservationModel.getReservationTime() + " This time has been booked.");
//        builder.setNegativeButton("NO",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//        builder.show();
//
//    }

    private static class MyViewHolder {
        TextView txtTitle;
        LinearLayout _linear;
    }

}