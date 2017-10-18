package ramymichealcenter.patient.firebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import ramymichealcenter.patient.Constants;

/**
 * Created by Mostafa on 10/3/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        /*--------------------------------*/
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "refreshedToken: ==> " + refreshedToken);
        /*--------------------------------*/
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);
        Log.e(TAG, "subscribeToTopic: ==> " + Constants.TOPIC_GLOBAL);
        /*--------------------------------*/

    }
}