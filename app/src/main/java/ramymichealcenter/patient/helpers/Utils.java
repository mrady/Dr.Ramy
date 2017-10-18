package ramymichealcenter.patient.helpers;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Mostafa on 9/4/2017.
 */

public class Utils {

    private ProgressDialog progressDialog;
    public static Utils instance;
    Context context;

    public static Utils getInstance(Context context) {
        if (instance == null) {
            instance = new Utils(context);
        }
        return instance;
    }

    public Utils(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("تحميل ...");
    }


    public void showProgress() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void dismissProgress() {
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            instance = null;
        }
    }

}
