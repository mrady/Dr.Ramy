package ramymichealcenter.patient.helpers.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import ramymichealcenter.patient.Constants;
import ramymichealcenter.patient.R;
import ramymichealcenter.patient.model.ServiceModel;

/**
 * Created by Mostafa on 9/25/2017.
 */

public class ServicesGridViewAdapter extends ArrayAdapter<ServiceModel> {

    private static final String TAG = ServicesGridViewAdapter.class.getCanonicalName();
    private Context context;
    private int layoutResourceId;
    private ArrayList<ServiceModel> serviceModelArrayList = new ArrayList<ServiceModel>();

    public ServicesGridViewAdapter(Context context, int layoutResourceId, ArrayList<ServiceModel> serviceModelArrayList) {
        super(context, layoutResourceId, serviceModelArrayList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.serviceModelArrayList = serviceModelArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ServicesGridViewAdapter.RecordHolder holder = null;

        if (row == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ServicesGridViewAdapter.RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
            holder.item_image = (ImageView) row.findViewById(R.id.item_image);

            row.setTag(holder);
        } else {
            holder = (ServicesGridViewAdapter.RecordHolder) row.getTag();
        }

        ServiceModel serviceModel = serviceModelArrayList.get(position);

        holder.txtTitle.setText(serviceModel.getServiceName());

        if (null != serviceModel.getPhoto())
            Glide.with(context)
                    .load(Constants.BASE_URL + serviceModel.getPhoto().get(0).getPhotoUrl())
                    .error(R.mipmap.ic_launcher)
                    .crossFade()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.item_image);
        else
            holder.item_image.setBackgroundResource(R.mipmap.ic_launcher);

        return row;
    }


    static class RecordHolder {
        TextView txtTitle;
        ImageView item_image;
    }

}