package ramymichealcenter.patient.viewcontroller;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ramymichealcenter.patient.R;
import ramymichealcenter.patient.helpers.adapter.RecipeAdapter;
import ramymichealcenter.patient.helpers.adapter.ReservationGridViewAdapter;
import ramymichealcenter.patient.helpers.adapter.ReservationRecyclerViewAdapter;
import ramymichealcenter.patient.helpers.adapter.SpinnerServicesAdapter;
import ramymichealcenter.patient.helpers.retrofit.WebServices;
import ramymichealcenter.patient.interfaces.Updatable;
import ramymichealcenter.patient.model.ReservationModel;
import ramymichealcenter.patient.model.ServiceModel;
import ramymichealcenter.patient.viewmodel.GetAllReservationViewModel;
import ramymichealcenter.patient.viewmodel.GetServicesViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment implements Updatable {

    private static final String TAG = ReservationFragment.class.getSimpleName();

    //------------------//
    Spinner sp_services_list;
    SpinnerServicesAdapter adapter;
    //------------------//
    TextView tv_current_date, tv_hint;
    //------------------//
    GridView gridView;
    ReservationGridViewAdapter reservationGridViewAdapter;
    //------------------//
    ArrayList<ServiceModel> allServicesArrayList;

    int service_position = 0;
    String previous_activity;


    public ReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.activity_reservation);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            service_position = bundle.getInt("service_position", 0);
            previous_activity = bundle.getString("previous_activity", HomeFragment.class.getSimpleName());
        }

        initUI(view);

        callAPI();

        fillNonEditableData();

        return view;
    }

    /*--------------------------------------------*/

    private void initUI(View view) {
        sp_services_list = (Spinner) view.findViewById(R.id.sp_services_list);
        tv_current_date = (TextView) view.findViewById(R.id.tv_current_date);
        tv_hint = (TextView) view.findViewById(R.id.tv_hint);

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
        GetServicesViewModel.getInstance().getAllDoctorServices(getActivity(), this);
    }

    private void CallAPISpinner(Integer serviceId) {
        GetAllReservationViewModel.getInstance().getAllReservation(getActivity(), this, serviceId);
    }

    private void fillNonEditableData() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        //String dayName = (String) android.text.format.DateFormat.format("EEEE", c.getTime());

        Locale locale = new Locale("ar");
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", locale);
        String formatteddayName = sdf.format(c.getTime());


        tv_current_date.setText(formatteddayName + "\t" + formattedDate);
    }

    private void updateSpinnerServices() {

        allServicesArrayList = new ArrayList<>();
        allServicesArrayList.add(new ServiceModel(0, getResources().getString(R.string.tv_reservation_fragment__select_service)));
        allServicesArrayList.addAll(GetServicesViewModel.getInstance().getServices());

        adapter = new SpinnerServicesAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, allServicesArrayList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        sp_services_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        sp_services_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position > 0) {
                    ServiceModel serviceModel = adapter.getItem(position);
                    CallAPISpinner(serviceModel.getServiceId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
    }

    /*--------------------------------------------*/

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
        bundle.putInt("service_position", sp_services_list.getSelectedItemPosition() -1);
        bundle.putInt("reservation_position", reservation_position);
        bundle.putString("previous_activity", ReservationFragment.class.getSimpleName());


        Fragment fragment = new ReservationDialogFragment();
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.content_main, fragment, ReservationDialogFragment.class.getSimpleName());
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void loadEmptyView(String msg) {
        gridView.setVisibility(View.GONE);
        tv_hint.setVisibility(View.VISIBLE);
        tv_hint.setText(msg);
    }

    /*--------------------------------------------*/
    @Override
    public void update(WebServices apiName) {
        switch (apiName) {
            case GET_ALL_SERVICES:
                updateSpinnerServices();
                break;
            case GET_ALL_RESERVATION:
                if (!GetAllReservationViewModel.getInstance().getReservation().isEmpty()) {
                    gridView.setVisibility(View.VISIBLE);
                    tv_hint.setVisibility(View.GONE);
                    gridView.setNumColumns(4);
                    reservationGridViewAdapter = new ReservationGridViewAdapter(getActivity(), R.layout.row_reservation_item_gridview, GetAllReservationViewModel.getInstance().getReservation());
                    gridView.setAdapter(reservationGridViewAdapter);
                } else
                    loadEmptyView(getResources().getString(R.string.tv_service_details_fragment__available_appointment));

                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.setDrawerState(true);
        }

    }

}
