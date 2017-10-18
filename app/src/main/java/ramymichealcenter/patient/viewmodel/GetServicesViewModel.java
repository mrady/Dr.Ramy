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
import ramymichealcenter.patient.model.ServiceModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mostafa on 9/16/2017.
 */

public class GetServicesViewModel {

    public static final String TAG = GetServicesViewModel.class.getSimpleName();

    private static GetServicesViewModel instance;
    private Updatable updatable;
    private Context mContext;

    @SerializedName("error")
    private Boolean error;

    @SerializedName("services")
    private ArrayList<ServiceModel> services = null;


    /*--------------------------------------------*/

    public GetServicesViewModel() {
    }

    public static GetServicesViewModel getInstance() {
        if (instance == null)
            instance = new GetServicesViewModel();
        return instance;
    }

    /*--------------------------------------------*/

    public void getAllDoctorServices(Context context, Updatable updatable) {
        this.updatable = updatable;
        mContext = context;

        MichealAppController application = MichealAppController.get(context);
        MichealServices michealServices = application.getMichealServices();

        Utils.getInstance(context).showProgress();

        if (NetworkUtils.isNetworkAvailable(context)) {


            Call<GetServicesViewModel> call = michealServices.getDoctorServices();

            Log.e(TAG, "call -> " + call.request().url().toString());

            call.enqueue(new Callback<GetServicesViewModel>() {
                @Override
                public void onResponse(Call<GetServicesViewModel> call, Response<GetServicesViewModel> response) {
                    instance = response.body();
                    onSuccess();
                    Log.e(TAG, "onResponse -> " + response.body().toString());
                }

                @Override
                public void onFailure(Call<GetServicesViewModel> call, Throwable t) {
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
            updatable.update(WebServices.GET_ALL_SERVICES);
        }

    }

    /*--------------------------------------------*/

    public ArrayList<ServiceModel> getServices() {
        return services;
    }

    @Override
    public String toString() {
        return "GetServicesViewModel{" +
                "services=" + services +
                ", error=" + error +
                '}';
    }

}
