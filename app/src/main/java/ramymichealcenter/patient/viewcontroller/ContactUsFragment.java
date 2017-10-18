package ramymichealcenter.patient.viewcontroller;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ramymichealcenter.patient.Constants;
import ramymichealcenter.patient.R;
import ramymichealcenter.patient.helpers.retrofit.WebServices;
import ramymichealcenter.patient.interfaces.Updatable;
import ramymichealcenter.patient.model.CommunicationModel;
import ramymichealcenter.patient.viewmodel.GetCommunicationViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment implements Updatable {

    TextView et_address, et_phone, et_facebook_1, et_facebook_2, et_facebook_3, et_twitter, et_instagram;
    ImageView iv_address, iv_phone, iv_facebook_1, iv_facebook_2, iv_facebook_3, iv_twitter, iv_instagram;

    LinearLayout layout_phone, layout_address, layout_facebook, layout_twitter, layout_instagram;

    RelativeLayout rl_display_facebook_1, rl_display_facebook_2, rl_display_facebook_3;
    /*-----------------------*/

    public ContactUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.activity_contact_us);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        initUI(view);

        callAPI();

        return view;
    }

    /*--------------------------------------------*/

    private void initUI(View view) {

        et_address = (TextView) view.findViewById(R.id.et_address);
        et_phone = (TextView) view.findViewById(R.id.et_phone);
        et_facebook_1 = (TextView) view.findViewById(R.id.et_facebook_1);
        et_facebook_2 = (TextView) view.findViewById(R.id.et_facebook_2);
        et_facebook_3 = (TextView) view.findViewById(R.id.et_facebook_3);
        et_twitter = (TextView) view.findViewById(R.id.et_twitter);
        et_instagram = (TextView) view.findViewById(R.id.et_instagram);

        iv_address = (ImageView) view.findViewById(R.id.iv_address);
        iv_phone = (ImageView) view.findViewById(R.id.iv_phone);
        iv_facebook_1 = (ImageView) view.findViewById(R.id.iv_facebook_1);
        iv_facebook_2 = (ImageView) view.findViewById(R.id.iv_facebook_2);
        iv_facebook_3 = (ImageView) view.findViewById(R.id.iv_facebook_3);
        iv_twitter = (ImageView) view.findViewById(R.id.iv_twitter);
        iv_instagram = (ImageView) view.findViewById(R.id.iv_instagram);

        layout_phone = (LinearLayout) view.findViewById(R.id.layout_phone);
        layout_address = (LinearLayout) view.findViewById(R.id.layout_address);
        layout_facebook = (LinearLayout) view.findViewById(R.id.layout_facebook);
        layout_twitter = (LinearLayout) view.findViewById(R.id.layout_twitter);
        layout_instagram = (LinearLayout) view.findViewById(R.id.layout_instagram);

        rl_display_facebook_1 = (RelativeLayout) view.findViewById(R.id.rl_display_facebook_1);
        rl_display_facebook_2 = (RelativeLayout) view.findViewById(R.id.rl_display_facebook_2);
        rl_display_facebook_3 = (RelativeLayout) view.findViewById(R.id.rl_display_facebook_3);

        /*-----------------------------------------------*/
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                if (et_phone.getText() != null)
                    if (!et_phone.getText().equals("") && !et_phone.getText().equals("NULL"))
                        intent.setData(Uri.parse("tel:" + et_phone.getText()));
                startActivity(intent);
            }
        });
        /*-----------------------------------------------*/
        iv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?daddr=" + Constants.sourceLatitude + "," + Constants.sourceLongitude + " (" + "Where the party is at" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    try {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(unrestrictedIntent);
                    } catch (ActivityNotFoundException innerEx) {
                        Toast.makeText(getActivity(), "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        /*-----------------------------------------------*/
        iv_facebook_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = et_facebook_1.getText().toString();
                openBrowser(url);
            }
        });
        /*-----------------------------------------------*/
        iv_facebook_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = et_facebook_2.getText().toString();
                openBrowser(url);
            }
        });
        /*-----------------------------------------------*/
        iv_facebook_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = et_facebook_3.getText().toString();
                openBrowser(url);
            }
        });
        /*-----------------------------------------------*/
        iv_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = et_twitter.getText().toString();
                openBrowser(url);
            }
        });
        /*-----------------------------------------------*/
        iv_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = et_instagram.getText().toString();
                openBrowser(url);
            }
        });
    }

    private void openBrowser(String url) {

        if (!url.equals("") && !url.equals("NULL")) {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        } else
            Toast.makeText(getActivity(), "Invalid URL", Toast.LENGTH_LONG).show();

    }

    private void callAPI() {
        GetCommunicationViewModel.getInstance().getCommunication(getActivity(), ContactUsFragment.this);
    }

    private void updateDisplay() {

        CommunicationModel communicationModel = GetCommunicationViewModel.getInstance().getCommunicationModels().get(0);
        /*------------------------------------------------------------------*/
        if (communicationModel.getAddress() != null)
            if (!communicationModel.getAddress().equals("NULL"))
                et_address.setText(communicationModel.getAddress());
            else
                layout_address.setVisibility(View.GONE);
        else
            layout_address.setVisibility(View.GONE);
        /*----------------------*/
        if (communicationModel.getPhone() != null)
            if (!communicationModel.getPhone().equals("NULL"))
                et_phone.setText(communicationModel.getPhone());
            else
                layout_phone.setVisibility(View.GONE);
        else
            layout_phone.setVisibility(View.GONE);
        /*----------------------*/
        if (communicationModel.getFacebook_1() == null && communicationModel.getFacebook_2() == null && communicationModel.getFacebook_3() == null)
            layout_facebook.setVisibility(View.GONE);
        else {
            if (communicationModel.getFacebook_1().equals("NULL") && communicationModel.getFacebook_2().equals("NULL") && communicationModel.getFacebook_3().equals("NULL")) {
                layout_facebook.setVisibility(View.GONE);
            } else {

                if (communicationModel.getFacebook_1() != null)
                    if (!communicationModel.getFacebook_1().equals("NULL"))
                        et_facebook_1.setText(communicationModel.getFacebook_1());
                    else
                        rl_display_facebook_1.setVisibility(View.GONE);
                else
                    rl_display_facebook_1.setVisibility(View.GONE);
            /*----------------------*/
                if (communicationModel.getFacebook_2() != null)
                    if (!communicationModel.getFacebook_2().equals("NULL"))
                        et_facebook_2.setText(communicationModel.getFacebook_2());
                    else
                        rl_display_facebook_2.setVisibility(View.GONE);
                else
                    rl_display_facebook_2.setVisibility(View.GONE);
            /*----------------------*/
                if (communicationModel.getFacebook_3() != null)
                    if (!communicationModel.getFacebook_3().equals("NULL"))
                        et_facebook_3.setText(communicationModel.getFacebook_3());
                    else
                        rl_display_facebook_3.setVisibility(View.GONE);
                else
                    rl_display_facebook_3.setVisibility(View.GONE);
            }
        }
        /*----------------------*/
        if (communicationModel.getTwitter() != null)
            if (!communicationModel.getTwitter().equals("NULL"))
                et_twitter.setText(communicationModel.getTwitter());
            else
                layout_twitter.setVisibility(View.GONE);
        else
            layout_twitter.setVisibility(View.GONE);
        /*----------------------*/
        if (communicationModel.getInstagram() != null)
            if (!communicationModel.getInstagram().equals("NULL"))
                et_instagram.setText(communicationModel.getInstagram());
            else
                layout_instagram.setVisibility(View.GONE);
        else
            layout_instagram.setVisibility(View.GONE);
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
            case GET_COMMUNICATION:
                updateDisplay();
                break;
        }

    }

}
