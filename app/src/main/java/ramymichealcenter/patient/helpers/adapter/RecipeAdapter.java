package ramymichealcenter.patient.helpers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

public class RecipeAdapter extends BaseAdapter {

    private static final String TAG = RecipeAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ServiceModel> mDataSource;


    public RecipeAdapter(Context context, ArrayList<ServiceModel> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public ServiceModel getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ServiceModel serviceModel = getItem(position);
        RecipeAdapter.ViewHolder viewHolder;


        if (convertView == null) {

            viewHolder = new RecipeAdapter.ViewHolder();

            convertView = mInflater.inflate(R.layout.row_item_recipe, parent, false);

            viewHolder.tv_row_service_name = (TextView) convertView.findViewById(R.id.tv_row_service_name);
            viewHolder.tv_row_service_desc = (TextView) convertView.findViewById(R.id.tv_row_service_desc);
            viewHolder.iv_row_service_img = (ImageView) convertView.findViewById(R.id.iv_row_service_img);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RecipeAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.tv_row_service_name.setText(serviceModel.getServiceName());
        viewHolder.tv_row_service_desc.setText(serviceModel.getServiceDesc());

        if (null != serviceModel.getPhoto())
            Glide.with(mContext)
                    .load(Constants.BASE_URL + serviceModel.getPhoto().get(0).getPhotoUrl())
                    .error(R.mipmap.ic_launcher)
                    .crossFade()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.iv_row_service_img);
        else
            viewHolder.iv_row_service_img.setBackgroundResource(R.mipmap.ic_launcher);

        return convertView;
    }

    private static class ViewHolder {
        private TextView tv_row_service_name;
        private TextView tv_row_service_desc;
        private ImageView iv_row_service_img;
    }

}
