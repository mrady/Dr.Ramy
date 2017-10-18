package ramymichealcenter.patient.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ramymichealcenter.patient.MichealAppController;
import ramymichealcenter.patient.helpers.NetworkUtils;
import ramymichealcenter.patient.helpers.Utils;
import ramymichealcenter.patient.helpers.retrofit.MichealServices;
import ramymichealcenter.patient.helpers.retrofit.WebServices;
import ramymichealcenter.patient.interfaces.Updatable;
import ramymichealcenter.patient.model.PostModel;
import ramymichealcenter.patient.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mostafa on 10/14/2017.
 */

public class GetAllPostsViewModel {

    public static final String TAG = GetAllPostsViewModel.class.getSimpleName();

    private static GetAllPostsViewModel instance;
    private Updatable updatable;
    private Context mContext;

    @SerializedName("error")
    private Boolean error;

    @SerializedName("posts")
    private ArrayList<PostModel> postsModels = null;

    /*--------------------------------------------*/
    public GetAllPostsViewModel() {
    }

    public static GetAllPostsViewModel getInstance() {
        if (instance == null)
            instance = new GetAllPostsViewModel();
        return instance;
    }
    /*--------------------------------------------*/

    public void getAllPosts(Context context, Updatable updatable) {
        this.updatable = updatable;
        mContext = context;

        MichealAppController application = MichealAppController.get(context);
        MichealServices michealServices = application.getMichealServices();

        Utils.getInstance(context).showProgress();

        if (NetworkUtils.isNetworkAvailable(context)) {


            Call<GetAllPostsViewModel> call = michealServices.getAllPosts();

            Log.e(TAG, "call -> " + call.request().url().toString());

            call.enqueue(new Callback<GetAllPostsViewModel>() {
                @Override
                public void onResponse(Call<GetAllPostsViewModel> call, Response<GetAllPostsViewModel> response) {
                    instance = response.body();
                    onSuccess();
                    Log.e(TAG, "onResponse -> " + response.body().toString());
                }

                @Override
                public void onFailure(Call<GetAllPostsViewModel> call, Throwable t) {
                    onError();
                    Log.e(TAG, "onFailure -> " + t.toString());
                }
            });


        } else {
            Utils.getInstance(mContext).dismissProgress();

            Toast.makeText(context, context.getResources().getString(R.string.error_network_connection), Toast.LENGTH_LONG).show();
        }
    }

    /*--------------------------------------------*/

    private void onError() {
        Utils.getInstance(mContext).dismissProgress();
    }

    private void onSuccess() {
        Utils.getInstance(mContext).dismissProgress();

        if (!instance.error) {
            updatable.update(WebServices.GET_All_POSTS);
        }

    }

    /*--------------------------------------------*/

    @Override
    public String toString() {
        return "GetAllPostsViewModel{" +
                "error=" + error +
                ", postsModels=" + postsModels +
                '}';
    }

    public ArrayList<PostModel> getPostsModels() {
        return postsModels;
    }
}
