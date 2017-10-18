package ramymichealcenter.patient.helpers.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import ramymichealcenter.patient.Constants;
import ramymichealcenter.patient.R;
import ramymichealcenter.patient.model.ServiceModel;

/**
 * Created by Mostafa on 9/16/2017.
 */

public class CustomGridViewAdapter extends ArrayAdapter<ServiceModel> {

    private static final String TAG = CustomGridViewAdapter.class.getCanonicalName();
    private Context context;
    private int layoutResourceId;
    private ArrayList<ServiceModel> data = new ArrayList<ServiceModel>();

    public CustomGridViewAdapter(Context context, int layoutResourceId, ArrayList<ServiceModel> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);


            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.tv_row_item_service);
            holder.circleImageView = (ImageView) row.findViewById(R.id.iv_row_item);
            holder.singleItem = (RelativeLayout) row.findViewById(R.id.singleItem);


            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            int width = display.getWidth(); // ((display.getWidth()*20)/100)
            int height = display.getHeight();// ((display.getHeight()*30)/100)
            LinearLayout.LayoutParams parms;

            switch (data.size()) {
//                case 1:
//                    parms = new LinearLayout.LayoutParams(width / 2, height / 3);
//                    holder.singleItem.setLayoutParams(parms);
//                    break;
//                case 2:
//                case 4:
//                case 5:
//                    parms = new LinearLayout.LayoutParams(width / 3, height / 4);
//                    holder.singleItem.setLayoutParams(parms);
//                    break;
                default:
                    parms = new LinearLayout.LayoutParams(width / 4, width / 4);
                    holder.singleItem.setLayoutParams(parms);
            }


            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        final ServiceModel serviceModel = data.get(position);

        holder.txtTitle.setText(serviceModel.getServiceName());

        if (null != serviceModel.getPhoto())
            Glide.with(context)
                    .load(Constants.BASE_URL + serviceModel.getPhoto().get(0).getPhotoUrl())
                    .error(R.mipmap.ic_launcher)
                    .crossFade()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.circleImageView);
        else
            holder.circleImageView.setBackgroundResource(R.mipmap.ic_launcher);


        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView circleImageView;
        RelativeLayout singleItem;
    }

}