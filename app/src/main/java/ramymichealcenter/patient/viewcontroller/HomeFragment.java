package ramymichealcenter.patient.viewcontroller;


import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import mehdi.sakout.fancybuttons.FancyButton;
import ramymichealcenter.patient.Constants;
import ramymichealcenter.patient.R;
import ramymichealcenter.patient.helpers.adapter.ServicesGridViewAdapter;
import ramymichealcenter.patient.helpers.retrofit.WebServices;
import ramymichealcenter.patient.interfaces.Updatable;
import ramymichealcenter.patient.model.SliderModel;
import ramymichealcenter.patient.viewmodel.GetAllSlidersViewModel;
import ramymichealcenter.patient.viewmodel.GetServicesViewModel;
import ramymichealcenter.patient.viewpager.ScreenSlidePagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements Updatable {

    private GridView grid_services;
    ServicesGridViewAdapter servicesGridViewAdapter;
    FancyButton btn_book_now;
    //----------------------//
    private ViewPager pager;
    private CircleIndicator indicator;
    private static ArrayList<String> adsImages;
    //----------------------//

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.activity_home);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        initUI(view);

        callAPI();


        adsImages = new ArrayList<>();
//        adsImages.add(Constants.BASE_URL + "uploads/2017100134IMG-20170610-WA0022.jpg");
//        adsImages.add(Constants.BASE_URL + "uploads/2017100158FB_IMG_1501142739319.jpg");
//        adsImages.add(Constants.BASE_URL + "uploads/2017100215IMG-20170511-WA0043.jpg");
//        adsImages.add(Constants.BASE_URL + "uploads/2017100255received_10155372674185255.png");
//        adsImages.add(Constants.BASE_URL + "uploads/2017100356FB_IMG_1493723217707.jpg");
//
//        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
//        pagerAdapter.addAll(adsImages);
//        pager.setAdapter(pagerAdapter);
//        indicator.setViewPager(pager);


        return view;
    }

    /*--------------------------------------------*/

    private void initUI(View view) {

        pager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);

        grid_services = (GridView) view.findViewById(R.id.grid_services);
        grid_services.setOnItemClickListener(serviceItemClickListener);

        btn_book_now = (FancyButton) view.findViewById(R.id.btn_book_now);

        btn_book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.openBookAppointmentScreeen();
                }
            }
        });
    }

    private void callAPI() {
        GetAllSlidersViewModel.getInstance().getAllSlider(getActivity(), this);

        GetServicesViewModel.getInstance().getAllDoctorServices(getActivity(), this);
    }

    AdapterView.OnItemClickListener serviceItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Bundle bundle = new Bundle();
            bundle.putInt("service_position", i);
            bundle.putString("previous_activity", HomeFragment.class.getSimpleName());

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
                grid_services.setNumColumns(3);
                servicesGridViewAdapter = new ServicesGridViewAdapter(getActivity(), R.layout.row_service_item_gridview, GetServicesViewModel.getInstance().getServices());
                grid_services.setAdapter(servicesGridViewAdapter);
                break;

            case GET_All_SLIDERS:

                adsImages.clear();

                for (SliderModel slider : GetAllSlidersViewModel.getInstance().getSlidersModels())
                    adsImages.add(slider.getPhotoUrl());

                ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
                pagerAdapter.addAll(adsImages);
                pager.setAdapter(pagerAdapter);
                indicator.setViewPager(pager);
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
