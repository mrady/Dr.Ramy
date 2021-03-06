package ramymichealcenter.patient.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ramymichealcenter.patient.MichealAppController;
import ramymichealcenter.patient.R;
import ramymichealcenter.patient.helpers.NetworkUtils;
import ramymichealcenter.patient.helpers.Utils;
import ramymichealcenter.patient.helpers.retrofit.MichealServices;
import ramymichealcenter.patient.helpers.retrofit.WebServices;
import ramymichealcenter.patient.interfaces.Updatable;
import ramymichealcenter.patient.model.PostModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mostafa on 10/14/2017.
 */

public class GetPostViewModel {

    public static final String TAG = GetPostViewModel.class.getSimpleName();

    private static GetPostViewModel instance;
    private Updatable updatable;
    private Context mContext;

    @SerializedName("error")
    private Boolean error;

    @SerializedName("post")
    private PostModel postsModels = null;

    /*--------------------------------------------*/
    public GetPostViewModel() {
    }

    public static GetPostViewModel getInstance() {
        if (instance == null)
            instance = new GetPostViewModel();
        return instance;
    }
    /*--------------------------------------------*/

    public void getPost(int postId, Context context, Updatable updatable) {
        this.updatable = updatable;
        mContext = context;

        MichealAppController application = MichealAppController.get(context);
        MichealServices michealServices = application.getMichealServices();

        Utils.getInstance(context).showProgress();

        if (NetworkUtils.isNetworkAvailable(context)) {


            Call<GetPostViewModel> call = michealServices.getPost(postId);

            Log.e(TAG, "call -> " + call.request().url().toString());

            call.enqueue(new Callback<GetPostViewModel>() {
                @Override
                public void onResponse(Call<GetPostViewModel> call, Response<GetPostViewModel> response) {
                    instance = response.body();
                    onSuccess();
                    Log.e(TAG, "onResponse -> " + response.body().toString());
                }

                @Override
                public void onFailure(Call<GetPostViewModel> call, Throwable t) {
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
            updatable.update(WebServices.GET_POST);
        }

    }

    /*--------------------------------------------*/

    @Override
    public String toString() {
        return "GetPostViewModel{" +
                "error=" + error +
                ", postsModels=" + postsModels +
                '}';
    }

    public PostModel getPostsModels() {
        return postsModels;
    }
}
