package ramymichealcenter.patient.viewcontroller.notification_post_modules;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ramymichealcenter.patient.Constants;
import ramymichealcenter.patient.R;
import ramymichealcenter.patient.model.PostModel;
import ramymichealcenter.patient.viewcontroller.HomeFragment;
import ramymichealcenter.patient.viewcontroller.MainActivity;
import ramymichealcenter.patient.viewcontroller.PostFragment;
import ramymichealcenter.patient.viewcontroller.ServiceDetailsFragment;
import ramymichealcenter.patient.viewmodel.GetAllPostsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPost_Fragment extends Fragment {

    public static final String TAG = DetailPost_Fragment.class.getSimpleName();
    //------------------//
    int postPosition = 0;
    String previous_activity;
    //------------------//
    ImageView iv_post;
    TextView tv_header, tv_body;
    /*------------------*/


    public DetailPost_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.activity_post);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_post, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            postPosition = bundle.getInt("postPosition", 0);
            previous_activity = bundle.getString("previous_activity", HomeFragment.class.getSimpleName());
        }

        initUI(view);

        fillNonEditableData();

        return view;
    }

    private void fillNonEditableData() {

        if (GetAllPostsViewModel.getInstance().getPostsModels().get(postPosition) != null) {
            PostModel postModel = GetAllPostsViewModel.getInstance().getPostsModels().get(postPosition);

            tv_header.setText(postModel.getTitle());
            tv_body.setText(postModel.getBody());

            if (null != postModel.getPhotoUrl() && !postModel.getPhotoUrl().isEmpty()) {

                Glide.with(getActivity())
                        .load(Constants.BASE_URL + postModel.getPhotoUrl())
                        .error(R.mipmap.ic_launcher)
                        .crossFade()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_post);
            }

        }

    }

    private void initUI(View view) {

        iv_post = (ImageView) view.findViewById(R.id.iv_post);

        tv_header = (TextView) view.findViewById(R.id.tv_header);
        tv_body = (TextView) view.findViewById(R.id.tv_body);

    }

    /*--------------------------------------------*/

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.setDrawerState(false);
        }

    }

}
