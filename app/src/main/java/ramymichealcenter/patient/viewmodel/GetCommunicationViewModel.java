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
import ramymichealcenter.patient.model.CommunicationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mostafa on 9/16/2017.
 */

public class GetCommunicationViewModel {

    public static final String TAG = GetCommunicationViewModel.class.getSimpleName();

    private static GetCommunicationViewModel instance;
    private Updatable updatable;
    private Context mContext;

    @SerializedName("error")
    private Boolean error;
    @SerializedName("communication")
    private ArrayList<CommunicationModel> communicationModels = null;

    /*--------------------------------------------*/
    public GetCommunicationViewModel() {
    }

    public static GetCommunicationViewModel getInstance() {
        if (instance == null)
            instance = new GetCommunicationViewModel();
        return instance;
    }
    /*--------------------------------------------*/

    public void getCommunication(Context context, Updatable updatable) {
        this.updatable = updatable;
        mContext = context;

        MichealAppController application = MichealAppController.get(context);
        MichealServices michealServices = application.getMichealServices();

        Utils.getInstance(context).showProgress();

        if (NetworkUtils.isNetworkAvailable(context)) {


            Call<GetCommunicationViewModel> call = michealServices.getCommunication();

            Log.e(TAG, "call -> " + call.request().url().toString());

            call.enqueue(new Callback<GetCommunicationViewModel>() {
                @Override
                public void onResponse(Call<GetCommunicationViewModel> call, Response<GetCommunicationViewModel> response) {
                    instance = response.body();
                    onSuccess();
                    Log.e(TAG, "onResponse -> " + response.body().toString());
                }

                @Override
                public void onFailure(Call<GetCommunicationViewModel> call, Throwable t) {
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
            updatable.update(WebServices.GET_COMMUNICATION);
        }

    }

    /*--------------------------------------------*/

    @Override
    public String toString() {
        return "{" +
                "error=" + error +
                ", workingTime=" + communicationModels +
                '}';
    }

    public ArrayList<CommunicationModel> getCommunicationModels() {
        return communicationModels;
    }
}

