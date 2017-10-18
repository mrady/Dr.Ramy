package ramymichealcenter.patient.helpers.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import ramymichealcenter.patient.Constants;
import ramymichealcenter.patient.R;
import ramymichealcenter.patient.model.PhotoModel;

/**
 * Created by Mostafa on 9/22/2017.
 */

public class PhotoListDataAdapter extends RecyclerView.Adapter<PhotoListDataAdapter.SingleItemRowHolder> {

    private static final String TAG = PhotoListDataAdapter.class.getSimpleName();

    private ArrayList<PhotoModel> itemsList;
    private Context mContext;

    public PhotoListDataAdapter(Context context, ArrayList<PhotoModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_service_photo, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        PhotoModel singleItem = itemsList.get(i);

        
//        Glide.with(mContext)
//                .load(singleItem.getPhotoUrl())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
//                .error(R.drawable.ic_home)
//                .into(holder.itemImage);


        Glide.with(mContext)
                .load(Constants.BASE_URL + singleItem.getPhotoUrl())
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected ImageView itemImage;


        public SingleItemRowHolder(View view) {
            super(view);

            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: ");
                }
            });


        }

    }

}