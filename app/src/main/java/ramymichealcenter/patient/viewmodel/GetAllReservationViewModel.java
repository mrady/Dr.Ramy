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
import ramymichealcenter.patient.model.ReservationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mostafa on 9/23/2017.
 */

public class GetAllReservationViewModel {

    public static final String TAG = GetAllReservationViewModel.class.getSimpleName();

    private static GetAllReservationViewModel instance;
    private Updatable updatable;
    private Context mContext;

    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("reservation")
    private ArrayList<ReservationModel> reservation = new ArrayList<>();


    /*--------------------------------------------*/
    public GetAllReservationViewModel() {
    }

    public static GetAllReservationViewModel getInstance() {
        if (instance == null)
            instance = new GetAllReservationViewModel();
        return instance;
    }
    /*--------------------------------------------*/

    public void getAllReservation(Context context, Updatable updatable, int serviceId) {
        this.updatable = updatable;
        mContext = context;

        MichealAppController application = MichealAppController.get(context);
        MichealServices michealServices = application.getMichealServices();

        Utils.getInstance(context).showProgress();

        if (NetworkUtils.isNetworkAvailable(context)) {


            Call<GetAllReservationViewModel> call = michealServices.getAllReservation(serviceId);

            Log.e(TAG, "call -> " + call.request().url().toString());

            call.enqueue(new Callback<GetAllReservationViewModel>() {
                @Override
                public void onResponse(Call<GetAllReservationViewModel> call, Response<GetAllReservationViewModel> response) {
                    instance = response.body();
                    onSuccess();
                    Log.e(TAG, "onResponse -> " + response.body().toString());
                }

                @Override
                public void onFailure(Call<GetAllReservationViewModel> call, Throwable t) {
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
            updatable.update(WebServices.GET_ALL_RESERVATION);
        } else {
            updatable.update(WebServices.GET_ALL_RESERVATION);
        }


    }

    /*--------------------------------------------*/

    @Override
    public String toString() {
        return "GetAllReservationViewModel{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", reservation=" + reservation +
                '}';
    }

    public String getMessage() {
        return message;
    }


    public ArrayList<ReservationModel> getReservation() {
        return reservation;
    }
}
