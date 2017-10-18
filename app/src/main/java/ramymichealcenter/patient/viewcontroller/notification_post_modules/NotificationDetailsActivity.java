package ramymichealcenter.patient.viewcontroller.notification_post_modules;

import android.app.Notification;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import ramymichealcenter.patient.Constants;
import ramymichealcenter.patient.R;
import ramymichealcenter.patient.helpers.adapter.PostRecyclerAdapter;
import ramymichealcenter.patient.helpers.retrofit.WebServices;
import ramymichealcenter.patient.interfaces.Updatable;
import ramymichealcenter.patient.model.PostModel;
import ramymichealcenter.patient.viewmodel.GetAllPostsViewModel;
import ramymichealcenter.patient.viewmodel.GetPostViewModel;

public class NotificationDetailsActivity extends AppCompatActivity implements Updatable {

    public static final String TAG = NotificationDetailsActivity.class.getSimpleName();
    //------------------//
    ImageView iv_post;
    TextView tv_header, tv_body;
    /*------------------*/
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initUI();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String param1 = bundle.getString("param1");
            callAPI(Integer.parseInt(param1));
            Log.e(TAG, "onCreate: param1 => " + param1.toString());
        }


//        if (getIntent().getExtras() != null) {
//            for (String key : getIntent().getExtras().keySet()) {
//                String value = getIntent().getExtras().getString(key);
//                Log.e(TAG, "Key: " + key + " Value: " + value);
//                Toast.makeText(this, "Key: " + key + " Value: " + value, Toast.LENGTH_LONG);
//            }
//        }

    }

    /*--------------------------------------------*/

    private void initUI() {
        iv_post = (ImageView) findViewById(R.id.iv_post);

        tv_header = (TextView) findViewById(R.id.tv_header);
        tv_body = (TextView) findViewById(R.id.tv_body);

    }

    private void callAPI(int postId) {
        GetPostViewModel.getInstance().getPost(postId, this, this);
    }

    private void fillNonEditableData() {

        if (GetPostViewModel.getInstance().getPostsModels() != null) {
            PostModel postModel = GetPostViewModel.getInstance().getPostsModels();

            tv_header.setText(postModel.getTitle());
            tv_body.setText(postModel.getBody());

            if (null != postModel.getPhotoUrl() && !postModel.getPhotoUrl().isEmpty()) {

                Glide.with(this)
                        .load(Constants.BASE_URL + postModel.getPhotoUrl())
                        .error(R.mipmap.ic_launcher)
                        .crossFade()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_post);
            }

        }

    }

    /*--------------------------------------------*/

    @Override
    public void update(WebServices apiName) {

        switch (apiName) {
            case GET_POST:
                fillNonEditableData();
                break;
        }

    }

}
