package ramymichealcenter.patient.viewcontroller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ramymichealcenter.patient.R;
import ramymichealcenter.patient.helpers.adapter.PostRecyclerAdapter;
import ramymichealcenter.patient.helpers.adapter.RecipeAdapter;
import ramymichealcenter.patient.helpers.retrofit.WebServices;
import ramymichealcenter.patient.interfaces.ItemClickListener;
import ramymichealcenter.patient.interfaces.Updatable;
import ramymichealcenter.patient.viewcontroller.notification_post_modules.DetailPost_Fragment;
import ramymichealcenter.patient.viewmodel.GetAllPostsViewModel;
import ramymichealcenter.patient.viewmodel.GetServicesViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment implements Updatable, ItemClickListener {

    private static final String TAG = PostFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private PostRecyclerAdapter adapter;

    /*--------------------------------------------*/

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.activity_post);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        initUI(view);

        callAPI();

        return view;
    }

    /*--------------------------------------------*/

    private void initUI(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void callAPI() {
        GetAllPostsViewModel.getInstance().getAllPosts(getActivity(), this);
    }

    /*--------------------------------------------*/
    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.setDrawerState(true);
        }

    }

    @Override
    public void update(WebServices apiName) {
        switch (apiName) {
            case GET_All_POSTS:
                adapter = new PostRecyclerAdapter(getActivity(), GetAllPostsViewModel.getInstance().getPostsModels());
                recyclerView.setAdapter(adapter);
                adapter.setClickListener(this);
                break;
        }

    }

    @Override
    public void onClick(View view, int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("postPosition", position);
        bundle.putString("previous_activity", PostFragment.class.getSimpleName());

        Fragment fragment = new DetailPost_Fragment();
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.content_main, fragment, DetailPost_Fragment.class.getSimpleName());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onDeleteClick(View view, int position) {

    }

    @Override
    public void onUpdateClick(View view, int position) {

    }
}
