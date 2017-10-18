package ramymichealcenter.patient.helpers;

import android.app.Activity;
import android.view.Gravity;
import android.widget.EditText;

import com.tapadoo.alerter.Alerter;

import ramymichealcenter.patient.R;


/**
 * Created by Mostafa on 9/12/2017.
 */

public class Validation {

    public static boolean hasText(EditText editText, String message, Activity activity) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(message);

            Alerter.create(activity)
                    .setText("برجـاء مـلء جميـع البيـانات المـطلوبة")
                    .setBackgroundColor(R.color.colorButtonGray)
                    .setDuration(3000)
                    .setContentGravity(Gravity.CENTER)
                    .show();

            return false;
        }

        return true;
    }

}
