package ramymichealcenter.patient;

/**
 * Created by Mostafa on 9/4/2017.
 */

public class Constants {
    public static final String BASE_URL = "http://sohba-ngo.com/v1/";
    public static final String BASE_API_URL = BASE_URL + "index.php/";

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";

    public static final float sourceLatitude = 26.560414f;
    public static final float sourceLongitude = 31.696990f;
}
