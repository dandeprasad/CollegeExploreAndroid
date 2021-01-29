package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import collegeexplore.CollegeInfo.R;


public class DeparmentsAdaptor extends RecyclerView.Adapter<DeparmentsAdaptor.carrerGuidanceViewHolder>   {

    private OnItemClickListener mListener;
    public TextView mTextView;
    Context contextAdaptor;
    ArrayList stringDataAdaptor;
    View v=null;


    public interface OnItemClickListener {
        void onClick(View view, int position, String clg_id);
    }

    public  class carrerGuidanceViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout departLinear;


        public carrerGuidanceViewHolder(View v) {
            super(v);

            departLinear = v.findViewById(R.id.linear_depart);


        }
    }

    public DeparmentsAdaptor(OnItemClickListener listener, Context contextForFragment, ArrayList datafromFragment) {
        contextAdaptor = contextForFragment;
        mListener = listener;
        stringDataAdaptor=datafromFragment;
    }

    @Override
    public DeparmentsAdaptor.carrerGuidanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType ==3) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.deparments, parent, false);

        }
        carrerGuidanceViewHolder vh = new carrerGuidanceViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder( carrerGuidanceViewHolder holder,  int position) {


        int val = position;


if (!(stringDataAdaptor.isEmpty())) {

    LinearLayout.LayoutParams params = new    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    params.gravity = Gravity.CENTER;
    holder.departLinear.removeAllViews();
    ArrayList dandeval = (ArrayList) stringDataAdaptor.get(position);
    TextView tv = new TextView(contextAdaptor);
    tv.setText(dandeval.get(0).toString());
    tv.setTextColor(Color.parseColor("#872c59"));
    tv.setPadding(15,45,15,45);
    tv.setTypeface(null, Typeface.BOLD);
    holder.departLinear.addView(tv);
    View v = new View(contextAdaptor);
    v.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            2
    ));
    v.setBackgroundColor(Color.parseColor("#B3B3B3"));

    holder.departLinear.addView(v,params);

    for (int i = 1; i < dandeval.size(); i++) {
        TextView tv1 = new TextView(contextAdaptor);
        tv1.setText(dandeval.get(i).toString());
        tv1.setPadding(15,25,15,25);
        holder.departLinear.addView(tv1);
        if(!(dandeval.size()-1==i))  {
            View v1 = new View(contextAdaptor);
            v1.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1
            ));
            v1.setBackgroundColor(Color.parseColor("#80B3B3B3"));

            holder.departLinear.addView(v1);
        }
    }
}


        }

    public int getItemCount() {
        return stringDataAdaptor.size();}

    @Override
    public int getItemViewType(int position) {

        if(true) {
            return 3;
        }


        return super.getItemViewType(position);
    }




}