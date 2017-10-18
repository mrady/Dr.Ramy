package ramymichealcenter.patient.viewcontroller;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ramymichealcenter.patient.R;
import ramymichealcenter.patient.helpers.adapter.PhotoListDataAdapter;
import ramymichealcenter.patient.helpers.adapter.RecipeAdapter;
import ramymichealcenter.patient.helpers.adapter.ReservationGridViewAdapter;
import ramymichealcenter.patient.helpers.adapter.ReservationRecyclerViewAdapter;
import ramymichealcenter.patient.helpers.retrofit.WebServices;
import ramymichealcenter.patient.interfaces.Updatable;
import ramymichealcenter.patient.model.ReservationModel;
import ramymichealcenter.patient.model.ServiceModel;
import ramymichealcenter.patient.viewmodel.GetAllReservationViewModel;
import ramymichealcenter.patient.viewmodel.GetServicesViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceDetailsFragment extends Fragment implements Updatable {

    public static final String TAG = ServiceDetailsFragment.class.getSimpleName();
    //------------------//
    int service_position = 0;
    String previous_activity;
    //------------------//
    TextView tv_service_name, tv_service_desc, tv_current_date, tv_hint;
    CheckBox cb_sa, cb_su, cb_mo, cb_tu, cb_we, cb_th, cb_fr;
    //------------------//
    RecyclerView recycler_view_list;
    GridView gridView;
    //------------------//
    public static final int DIALOG_FRAGMENT = 1;

    ReservationGridViewAdapter reservationGridViewAdapter;


    public ServiceDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.activity_service_details);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_details, container, false);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            service_position = bundle.getInt("service_position", 0);
            previous_activity = bundle.getString("previous_activity", HomeFragment.class.getSimpleName());
        }

        initUI(view);

        if (GetServicesViewModel.getInstance().getServices().get(service_position).getAllowBooking() == 1)
            callAPI();
        else
            loadEmptyView(getResources().getString(R.string.tv_service_details_fragment__allow_booking));

        fillNonEditableData();

        return view;
    }

    private void loadEmptyView(String msg) {
        gridView.setVisibility(View.GONE);
        tv_hint.setVisibility(View.VISIBLE);
        tv_hint.setText(msg);
    }

    /*--------------------------------------------*/

    @SuppressLint("SetTextI18n")
    private void fillNonEditableData() {

        ServiceModel serviceModel = GetServicesViewModel.getInstance().getServices().get(service_position);

        tv_service_name.setText(serviceModel.getServiceName());
        tv_service_desc.setText(serviceModel.getServiceDesc());


        if (!serviceModel.getWorkingDay().isEmpty()) {
            for (int i = 0; i < serviceModel.getWorkingDay().size(); i++) {

                if (serviceModel.getWorkingDay().get(i).getWorkingDay().equals("Saturday")) {
                    cb_sa.setChecked(true);
                }

                if (serviceModel.getWorkingDay().get(i).getWorkingDay().equals("Sunday")) {
                    cb_su.setChecked(true);
                }

                if (serviceModel.getWorkingDay().get(i).getWorkingDay().equals("Monday")) {
                    cb_mo.setChecked(true);
                }

                if (serviceModel.getWorkingDay().get(i).getWorkingDay().equals("Tuesday")) {
                    cb_tu.setChecked(true);
                }

                if (serviceModel.getWorkingDay().get(i).getWorkingDay().equals("Wednesday")) {
                    cb_we.setChecked(true);
                }

                if (serviceModel.getWorkingDay().get(i).getWorkingDay().equals("Thursday")) {
                    cb_th.setChecked(true);
                }

                if (serviceModel.getWorkingDay().get(i).getWorkingDay().equals("Friday")) {
                    cb_fr.setChecked(true);
                }

            }
        }

        PhotoListDataAdapter photoListDataAdapter = new PhotoListDataAdapter(getActivity(), serviceModel.getPhoto());
        recycler_view_list.setAdapter(photoListDataAdapter);// set adapter on recyclerview
        photoListDataAdapter.notifyDataSetChanged();// Notify the adapter


        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        //String dayName = (String) android.text.format.DateFormat.format("EEEE", c.getTime());

        Locale locale = new Locale("ar");
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", locale);
        String formatteddayName = sdf.format(c.getTime());

        tv_current_date.setText(formatteddayName + "\t" + formattedDate);

    }
    /*--------------------------------------------*/


    private void initUI(View view) {
        tv_service_name = (TextView) view.findViewById(R.id.tv_service_name);
        tv_service_desc = (TextView) view.findViewById(R.id.tv_service_desc);
        tv_current_date = (TextView) view.findViewById(R.id.tv_current_date);
        tv_hint = (TextView) view.findViewById(R.id.tv_hint);

        cb_sa = (CheckBox) view.findViewById(R.id.cb_sa);
        cb_su = (CheckBox) view.findViewById(R.id.cb_su);
        cb_mo = (CheckBox) view.findViewById(R.id.cb_mo);
        cb_tu = (CheckBox) view.findViewById(R.id.cb_tu);
        cb_we = (CheckBox) view.findViewById(R.id.cb_we);
        cb_th = (CheckBox) view.findViewById(R.id.cb_th);
        cb_fr = (CheckBox) view.findViewById(R.id.cb_fr);

        recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
        recycler_view_list.setHasFixedSize(true);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                TextView currentLetter = (TextView) view.findViewById(R.id.item_text);

                ReservationModel reservationModel = GetAllReservationViewModel.getInstance().getReservation().get(position);

                if (reservationModel.getReserved()) {
                    displayPopupReserved(reservationModel, currentLetter);
                } else {
                    displayPopupConfirmReservation(position, currentLetter);
                }

                Log.e(TAG, "onItemClick: " + GetAllReservationViewModel.getInstance().getReservation().get(position).toString());
            }
        });


    }

    private void callAPI() {

        GetAllReservationViewModel.getInstance().getAllReservation(getActivity(), this, GetServicesViewModel.getInstance().getServices().get(service_position).getServiceId());
    }

    private void displayPopupReserved(ReservationModel reservationModel, TextView txtTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Error");
        builder.setMessage(reservationModel.getReservationTime() + " This time has been booked.");
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();

    }

    private void displayPopupConfirmReservation(int reservation_position, final TextView txtTitle) {

        txtTitle.setBackgroundColor(getResources().getColor(R.color.colorDate_bg));

        Bundle bundle = new Bundle();
        bundle.putInt("service_position", service_position);
        bundle.putInt("reservation_position", reservation_position);
        bundle.putString("previous_activity", previous_activity);

        Fragment fragment = new ReservationDialogFragment();
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.content_main, fragment, ReservationDialogFragment.class.getSimpleName());
        fragmentTransaction.commitAllowingStateLoss();
    }


    /*--------------------------------------------*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();
            if (bundle != null) {

//                String input_username = bundle.getString("input_username");
//                String input_address = bundle.getString("input_phone");
//                int reservation_position = bundle.getInt("reservation_position");

//                Log.e(TAG, "onActivityResult: input_username ->" + input_username);
//                Log.e(TAG, "onActivityResult: input_address ->" + input_address);
//                Log.e(TAG, "onActivityResult: reservation_time ->" + GetAllReservationViewModel.getInstance().getReservation().get(reservation_position));

            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e(TAG, "onOptionsItemSelected: ");
        return super.onOptionsItemSelected(item);

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
            case GET_ALL_RESERVATION:
                if (!GetAllReservationViewModel.getInstance().getReservation().isEmpty()) {
                    gridView.setNumColumns(4);
                    reservationGridViewAdapter = new ReservationGridViewAdapter(getActivity(), R.layout.row_reservation_item_gridview, GetAllReservationViewModel.getInstance().getReservation());
                    gridView.setAdapter(reservationGridViewAdapter);
                } else
                    loadEmptyView(getResources().getString(R.string.tv_service_details_fragment__available_appointment));
                break;
        }

    }
}
