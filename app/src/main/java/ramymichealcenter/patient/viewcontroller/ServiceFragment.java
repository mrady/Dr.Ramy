package ramymichealcenter.patient.viewcontroller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ramymichealcenter.patient.R;
import ramymichealcenter.patient.helpers.adapter.RecipeAdapter;
import ramymichealcenter.patient.helpers.retrofit.WebServices;
import ramymichealcenter.patient.interfaces.Updatable;
import ramymichealcenter.patient.model.ServiceModel;
import ramymichealcenter.patient.viewmodel.GetServicesViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment implements Updatable {

    RecipeAdapter adapter;
    private ArrayList<ServiceModel> gridArrayLst = new ArrayList<>();
    private ListView mListView;

    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.activity_services);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        initUI(view);

        callAPI();

        return view;
    }

    /*--------------------------------------------*/
    private void initUI(View view) {
        mListView = (ListView) view.findViewById(R.id.recipe_list_view);
        adapter = new RecipeAdapter(getActivity(), gridArrayLst);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(serviceItemClickListener);
    }

    private void callAPI() {
        GetServicesViewModel.getInstance().getAllDoctorServices(getActivity(), this);
    }

    AdapterView.OnItemClickListener serviceItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Bundle bundle = new Bundle();
            bundle.putInt("service_position", i);
            bundle.putString("previous_activity", ServiceFragment.class.getSimpleName() );

            Fragment fragment = new ServiceDetailsFragment();
            fragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.content_main, fragment, ServiceDetailsFragment.class.getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();
        }
    };

    /*--------------------------------------------*/
    @Override
    public void update(WebServices apiName) {
        switch (apiName) {
            case GET_ALL_SERVICES:
                gridArrayLst.clear();
                gridArrayLst.addAll(GetServicesViewModel.getInstance().getServices());
                adapter.notifyDataSetChanged();
                break;
            default:
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
