package ramymichealcenter.patient;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import ramymichealcenter.patient.helpers.retrofit.MichealServices;
import ramymichealcenter.patient.helpers.retrofit.RetrofitClient;


/**
 * Created by Mostafa on 9/4/2017.
 */

public class MichealAppController extends Application {

    public static final String TAG = MichealAppController.class.getSimpleName();
    private static Context context;
    private MichealServices michealServices;

    /*--------------------------------------------*/
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = this;
    }

    public MichealServices getMichealServices() {
        if (michealServices == null) {
            michealServices = RetrofitClient.create();
        }

        return michealServices;
    }

    public static Context getContext() {
        return context;
    }

    public static MichealAppController get(Context context) {
        return (MichealAppController) context.getApplicationContext();
    }

    public static MichealAppController create(Context context) {
        return MichealAppController.get(context);
    }

}
