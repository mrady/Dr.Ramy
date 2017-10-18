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
import ramymichealcenter.patient.model.SliderModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mostafa on 10/17/2017.
 */

public class GetAllSlidersViewModel {

    public static final String TAG = GetAllSlidersViewModel.class.getSimpleName();

    private static GetAllSlidersViewModel instance;
    private Updatable updatable;
    private Context mContext;

    @SerializedName("error")
    private Boolean error;
    @SerializedName("Sliders")
    private ArrayList<SliderModel> slidersModels = null;

    /*--------------------------------------------*/
    public GetAllSlidersViewModel() {
    }

    public static GetAllSlidersViewModel getInstance() {
        if (instance == null)
            instance = new GetAllSlidersViewModel();
        return instance;
    }
    /*--------------------------------------------*/

    public void getAllSlider(Context context, Updatable updatable) {
        this.updatable = updatable;
        mContext = context;

        MichealAppController application = MichealAppController.get(context);
        MichealServices michealServices = application.getMichealServices();

        Utils.getInstance(context).showProgress();

        if (NetworkUtils.isNetworkAvailable(context)) {


            Call<GetAllSlidersViewModel> call = michealServices.getAllSliders();

            Log.e(TAG, "call -> " + call.request().url().toString());

            call.enqueue(new Callback<GetAllSlidersViewModel>() {
                @Override
                public void onResponse(Call<GetAllSlidersViewModel> call, Response<GetAllSlidersViewModel> response) {
                    instance = response.body();
                    onSuccess();
                    Log.e(TAG, "onResponse -> " + response.body().toString());
                }

                @Override
                public void onFailure(Call<GetAllSlidersViewModel> call, Throwable t) {
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
            updatable.update(WebServices.GET_All_SLIDERS);
        }

    }

    /*--------------------------------------------*/

    @Override
    public String toString() {
        return "GetAllSlidersViewModel{" +
                "error=" + error +
                ", slidersModels=" + slidersModels +
                '}';
    }

    public ArrayList<SliderModel> getSlidersModels() {
        return slidersModels;
    }
}
