package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick.Reviews;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import collegeexplore.CollegeInfo.R;

public class ReviewDetailFragment extends Fragment {

    private static final String EXTRA_ANIMAL_ITEM = "animal_item";
    private static final String EXTRA_TRANSITION_NAME = "transition_name";

    public ReviewDetailFragment() {
        // Required empty public constructor
    }

    public static ReviewDetailFragment newInstance(HashMap animalItem, String transitionName) {
        ReviewDetailFragment f = new ReviewDetailFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("HashMap",animalItem);
        bundle.putString(EXTRA_TRANSITION_NAME, transitionName);
        f.setArguments(bundle);

        return  f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        // setSharedElementEnterTransition(enterTransition());
         //setSharedElementReturnTransition(returnTransition());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newswkspc_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar myToolbar = view.findViewById(R.id.PurenewsToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.college_explore);

        // Get a support ActionBar corresponding to this toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //final Toolbar HomeToolbarfrag = (Toolbar) findViewById(R.id.toolbar_collaspe);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
            }

        });

        HashMap<String,String> animalItem =(HashMap) getArguments().getSerializable("HashMap");
        String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);

        TextView detailTextView = view.findViewById(R.id.data_detail_text);
        detailTextView.setText(animalItem.get("NEWS_DETAILS"));
        TextView data_header_text = view.findViewById(R.id.data_header_text);
        data_header_text.setText(animalItem.get("NEWS_HEADER"));

        ImageView imageView = view.findViewById(R.id.news_detail_image_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(transitionName);
        }

        Picasso.with(getContext())
                .load(animalItem.get("NEWSID"))
                .noFade()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        startPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        startPostponedEnterTransition();
                    }
                });
    }
    private Transition enterTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(50000);

        return bounds;
    }

    private Transition returnTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setInterpolator(new DecelerateInterpolator());
        bounds.setDuration(50000);

        return bounds;
    }
}