package ramymichealcenter.patient.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MultipartBody;
import ramymichealcenter.patient.MichealAppController;
import ramymichealcenter.patient.R;
import ramymichealcenter.patient.helpers.NetworkUtils;
import ramymichealcenter.patient.helpers.Utils;
import ramymichealcenter.patient.helpers.retrofit.MichealServices;
import ramymichealcenter.patient.helpers.retrofit.WebServices;
import ramymichealcenter.patient.interfaces.Updatable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mostafa on 9/23/2017.
 */

public class BookReservationViewModel {

    private static final String TAG = BookReservationViewModel.class.getSimpleName();
    private static BookReservationViewModel instance;

    private Updatable updatable;
    public Context mContext;

    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    /*--------------------------------------------*/

    public BookReservationViewModel() {
    }

    public static BookReservationViewModel getInstance() {
        if (instance == null)
            instance = new BookReservationViewModel();
        return instance;
    }

    /*--------------------------------------------*/

    public void bookReservation(String service_id, String patient_name, String patient_phone,
                                String reservation_time, String reservation_date,
                                Context context, Updatable updatable) {

        this.updatable = updatable;
        this.mContext = context;


        MichealAppController application = MichealAppController.get(context);
        MichealServices michealServices = application.getMichealServices();

        Utils.getInstance(context).showProgress();

        if (NetworkUtils.isNetworkAvailable(context)) {

            ArrayList<MultipartBody.Part> parts = new ArrayList<>();


            // MultipartBody.Part workingDatePart = MultipartBody.Part.createFormData("working_date", workingDate);
            MultipartBody.Part serviceIdPart = MultipartBody.Part.createFormData("service_id", service_id);
            MultipartBody.Part patientNamePart = MultipartBody.Part.createFormData("patient_name", patient_name);
            MultipartBody.Part patientPhonePart = MultipartBody.Part.createFormData("patient_phone", patient_phone);
            MultipartBody.Part reservationTimePart = MultipartBody.Part.createFormData("reservation_time", reservation_time);
            MultipartBody.Part reservationDatePart = MultipartBody.Part.createFormData("reservation_date", reservation_date);

            parts.add(serviceIdPart);
            parts.add(patientNamePart);
            parts.add(patientPhonePart);
            parts.add(reservationTimePart);
            parts.add(reservationDatePart);


            Call<BookReservationViewModel> call = michealServices.bookReservation(parts);
            call.enqueue(new Callback<BookReservationViewModel>() {
                @Override
                public void onResponse(Call<BookReservationViewModel> call, Response<BookReservationViewModel> response) {

                    Log.e(TAG, "onResponse: " + response.body());

                    Crashlytics.setString("onResponse ", response.body() + "");

                    Answers.getInstance().logContentView(new ContentViewEvent().putCustomAttribute("Screen onResponse", response.body() + ""));

                    instance = response.body();
                    onSuccess();
                }

                @Override
                public void onFailure(Call<BookReservationViewModel> call, Throwable t) {
                    onError();
                }
            });


        } else {
            Utils.getInstance(context).dismissProgress();
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
            updatable.update(WebServices.BOOK_RESERVATION);
        } else {
            Toast.makeText(mContext, instance.message, Toast.LENGTH_LONG).show();
        }
    }

    /*--------------------------------------------*/

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{" +
                "error=" + error +
                ", message='" + message + '\'' +
                '}';
    }

}
