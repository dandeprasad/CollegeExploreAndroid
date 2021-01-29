package collegeexplore.CollegeInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AboutUsClgEplore extends Fragment{
    int mNum;

    Context contextForFragment;

    public static Bitmap[] arrayOfBitmap= new Bitmap[20];


    public static AboutUsClgEplore newInstance() {
        AboutUsClgEplore f = new AboutUsClgEplore();


        Bundle args = new Bundle();
        args.putInt("num", 1);
        f.setArguments(args);

        return  f;
    }
    //for getting the bundle data in fragments here we are getting the context.
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        contextForFragment  = context;
    }


    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNum = getArguments() != null ? getArguments().getInt("num") : 1;


        }

    /**
     * This is called soon after oncreate and expects a view return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.aboutus_text, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        Toolbar myToolbar = view.findViewById(R.id.aboutustollbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.about_us_java);
        // Get a support ActionBar corresponding to this toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //final Toolbar HomeToolbarfrag = (Toolbar) findViewById(R.id.toolbar_collaspe);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }

        });
    }


}
