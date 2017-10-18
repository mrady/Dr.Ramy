package ramymichealcenter.patient.viewcontroller;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ramymichealcenter.patient.R;
import ramymichealcenter.patient.helpers.Validation;
import ramymichealcenter.patient.helpers.retrofit.WebServices;
import ramymichealcenter.patient.interfaces.Updatable;
import ramymichealcenter.patient.viewmodel.BookReservationViewModel;
import ramymichealcenter.patient.viewmodel.GetAllReservationViewModel;
import ramymichealcenter.patient.viewmodel.GetServicesViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationDialogFragment extends Fragment implements Updatable {

    public static final String TAG = ReservationDialogFragment.class.getSimpleName();
    int reservation_position = 0, service_position = 0, service_id = 0;

    Button btn_book_reservation;
    EditText input_phone, input_username;
    String previous_activity;

    public ReservationDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.activity_book_now);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reservation_dialog, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            reservation_position = bundle.getInt("reservation_position", 0);
            service_position = bundle.getInt("service_position", 0);
            previous_activity = bundle.getString("previous_activity",HomeFragment.class.getSimpleName());
            service_id = GetServicesViewModel.getInstance().getServices().get(service_position).getServiceId();
        }

        initUI(rootView);

        return rootView;

    }

    private void initUI(View view) {

        btn_book_reservation = view.findViewById(R.id.btn_book_reservation);
        input_username = view.findViewById(R.id.input_username);
        input_phone = view.findViewById(R.id.input_phone);

        btn_book_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookReservation();

            }
        });
    }

    private void bookReservation() {
        if (checkValidation()) {

            Calendar c = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String formattedDate = df.format(c.getTime());


            DateFormat readFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
            DateFormat writeFormat = new SimpleDateFormat("HH:mm", Locale.US);
            Date date = null;
            try {
                date = readFormat.parse(GetAllReservationViewModel.getInstance().getReservation().get(reservation_position).getReservationTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String formattedReservationDate = "";
            if (date != null) {
                formattedReservationDate = writeFormat.format(date);
            }

            Log.e(TAG, "onClick: input_username --> " + input_username.getText().toString());
            Log.e(TAG, "onClick: input_phone --> " + input_phone.getText().toString());
            Log.e(TAG, "onClick: reservation_position --> " + reservation_position);
            Log.e(TAG, "onClick: reservation_time --> " + GetAllReservationViewModel.getInstance().getReservation().get(reservation_position).getReservationTime());
            Log.e(TAG, "onClick: reservation_date --> " + formattedDate);
            Log.e(TAG, "onClick: reservation_date --> " + formattedReservationDate);

            BookReservationViewModel
                    .getInstance().bookReservation(String.valueOf(service_id), input_username.getText().toString(), input_phone.getText().toString(),
                    formattedReservationDate,
                    formattedDate, getActivity(), this);


        }
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(input_username, "username required", getActivity()))
            ret = false;
        if (!Validation.hasText(input_phone, "phone required", getActivity()))
            ret = false;

        return ret;

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.setDrawerState(false);
        }

    }

    @Override
    public void update(WebServices apiName) {

        switch (apiName) {
            case BOOK_RESERVATION:
                Log.e(TAG, "update: ");
                Toast.makeText(getActivity(), BookReservationViewModel.getInstance().getMessage(),Toast.LENGTH_LONG).show();
                break;
        }
    }
}
