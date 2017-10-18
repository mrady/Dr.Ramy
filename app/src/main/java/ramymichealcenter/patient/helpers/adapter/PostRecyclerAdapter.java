package ramymichealcenter.patient.helpers.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import ramymichealcenter.patient.Constants;
import ramymichealcenter.patient.R;
import ramymichealcenter.patient.interfaces.ItemClickListener;
import ramymichealcenter.patient.model.PostModel;

/**
 * Created by Mostafa on 10/14/2017.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {


    private ArrayList<PostModel> postModelArrayList;
    private Activity activity;
    private ItemClickListener clickListener;

    public PostRecyclerAdapter(Activity activity, ArrayList<PostModel> postModelArrayList) {
        this.postModelArrayList = postModelArrayList;
        this.activity = activity;
    }

    @Override
    public PostRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.list_item_post, viewGroup, false);
        PostRecyclerAdapter.ViewHolder viewHolder = new PostRecyclerAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostRecyclerAdapter.ViewHolder viewHolder, final int position) {

        //setting data to view holder elements
        viewHolder.tv_header.setText(postModelArrayList.get(position).getTitle());
        viewHolder.tv_body.setText(postModelArrayList.get(position).getBody());

        if (null != postModelArrayList.get(position).getPhotoUrl() && !postModelArrayList.get(position).getPhotoUrl().isEmpty()) {

            Glide.with(activity)
                    .load(Constants.BASE_URL + postModelArrayList.get(position).getPhotoUrl())
                    .error(R.mipmap.ic_launcher)
                    .crossFade()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.iv_post);
        }

    }

    @Override
    public int getItemCount() {
        return (null != postModelArrayList ? postModelArrayList.size() : 0);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView card_view;
        private ImageView iv_post;
        private TextView tv_header;
        private TextView tv_body;

        public ViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            iv_post = (ImageView) itemView.findViewById(R.id.iv_post);

            tv_header = (TextView) itemView.findViewById(R.id.tv_header);
            tv_body = (TextView) itemView.findViewById(R.id.tv_body);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view == itemView)
                if (clickListener != null) clickListener.onClick(view, getAdapterPosition());


        }
    }

}
