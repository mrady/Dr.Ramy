package ramymichealcenter.patient.interfaces;

import android.view.View;

/**
 * Created by Mostafa on 9/10/2017.
 */

public interface ItemClickListener {

    void onClick(View view, int position);

    void onDeleteClick(View view, int position);

    void onUpdateClick(View view, int position);

}
