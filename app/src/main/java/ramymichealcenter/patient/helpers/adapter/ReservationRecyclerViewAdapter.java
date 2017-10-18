package ramymichealcenter.patient.helpers.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ramymichealcenter.patient.R;
import ramymichealcenter.patient.model.ReservationModel;

/**
 * Created by Mostafa on 10/1/2017.
 */

public class ReservationRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private ArrayList<ReservationModel> reservationModelArrayList;
    private Context context;

    public ReservationRecyclerViewAdapter(Context context, ArrayList<ReservationModel> reservationModelArrayList) {
        this.reservationModelArrayList = reservationModelArrayList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reservation_item_gridview, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        ReservationModel reservationModel = reservationModelArrayList.get(position);

        holder.txtTitle.setText(reservationModel.getReservationTime());
        if (reservationModel.getReserved())
            holder.txtTitle.setBackgroundColor(context.getResources().getColor(R.color.colorDate_bg));

    }

    @Override
    public int getItemCount() {
        return this.reservationModelArrayList.size();
    }

}