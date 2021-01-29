package collegeexplore.CollegeInfo.RankingsWorkspace;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.R;


public class RankingsFragmentAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ArrayList> mDataset;

    public final int TYPE_NEWS = 0;
    public final int TYPE_LOAD = 1;
    private OnItemClickListener mListener;
    Context contextForFragmentadp;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;


    public interface OnItemClickListener {
        void onClick(View view, String position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
     static class NewsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView,CLG_NAME,STREAMTXT;
public Button ROUND;
        TableLayout stk;
        Button stk1;

        public NewsViewHolder(View v) {
            super(v);
             stk = v.findViewById(R.id.table_main_carrer);
            CLG_NAME= v.findViewById(R.id.CLG_NAME);
            STREAMTXT= v.findViewById(R.id.STREAMTXT);
            ROUND= v.findViewById(R.id.ROUND);
            stk1 = v.findViewById(R.id.table_header);

          //  newsimgtagy = (ImageView) v.findViewById(R.id.newsimgtagy);
           // nitsView=(RelativeLayout)RelativeLayout(nitsView);

        }
    }
    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public RankingsFragmentAdaptor(Context contextForFragment, ArrayList<ArrayList> maincontents, ArrayList<String> imageId, OnItemClickListener listener) {
        mDataset = maincontents;
        mListener = listener;

        contextForFragmentadp=contextForFragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            return new NewsViewHolder(inflater.inflate(R.layout.rankings_mhrd_layout,parent,false));


    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
     /*   int i;
        View dande = null;
        for (i=0;i<mDataset.length;i++)
        {
           String king = mDataset[position];
            holder.mTextView.setText(king);
            holder.mTextView.setTextColor(Color.BLACK);

            if (dande != null) {
                holder.seperat = dande.findViewById(R.id.separate);
            }
            // seperat =  seperat.findViewById(R.id.separate);

        }

        */


        if(getItemViewType(position)==TYPE_NEWS) {

            if (!(mDataset.isEmpty())) {

               ArrayList<HashMap> valuesend = mDataset.get(position);

               // TextView bal111 = UpdateAsyncdataheader1("JEE Entrance 2017");
                ((NewsViewHolder) holder).stk1.setHeight(100);
                ((NewsViewHolder) holder).stk1.setPadding(15,15,15,15);
                ((NewsViewHolder) holder).stk1.setText("MHRD Rankings 2017");
                ((NewsViewHolder) holder).stk1.setTextSize(12);
                ((NewsViewHolder) holder).stk1.setTextColor(Color.BLACK);
                ((NewsViewHolder) holder).stk1.setBackgroundColor(Color.parseColor("#40000000"));
                ((NewsViewHolder) holder).stk1.setTypeface(((NewsViewHolder) holder).stk1.getTypeface(), Typeface.BOLD);
                ((NewsViewHolder) holder).stk1.setTypeface(null, Typeface.BOLD);
                //  t1v.setTextAppearance(contextForFragmentadp, R.style.TextViewMyTheme);
                ((NewsViewHolder) holder).stk1.setGravity(Gravity.CENTER);
                // t1v.setBackground(xw);
                ((NewsViewHolder) holder).stk1.setTypeface(Typeface.create("arial", Typeface.NORMAL));

                TableRow tbrow1 = new TableRow(contextForFragmentadp);
                TableRow.LayoutParams tlp1 = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
                tbrow1.setLayoutParams(tlp1);
             //   Drawable xw = contextForFragmentadp.getResources().getDrawable(R.drawable.textinputborder1);
                //tbrow.setBackgroundResource(android.R.drawable.gallery_thumb);

                TextView bal = UpdateAsyncdataheader("College Name");
                TextView bal1 = UpdateAsyncdataheader("City");
                TextView bal2 = UpdateAsyncdataheader("State");
                TextView bal3 = UpdateAsyncdataheader("Score");

                TextView bal4 = UpdateAsyncdataheader("Rank");
               // TextView bal5 = UpdateAsyncdataheader("Quota HS (OR)");
                //  TextView val6 = UpdateAsyncdataline((String) values.get("COURSE"));
                tbrow1.addView(bal);
                // tbrow.addView(val6);
                tbrow1.addView(bal1);
                // tbrow.addView(val6);
                tbrow1.addView(bal2);
                //  tbrow.addView(val6);
                tbrow1.addView(bal3);
                //  tbrow.addView(val6);
                tbrow1.addView(bal4);
                //  tbrow.addView(val6);
              //  tbrow1.addView(bal5);
                ((NewsViewHolder) holder).stk.addView(tbrow1);
                for(int i=0;i<valuesend.size();i++) {
                    HashMap values = valuesend.get(i);

/*            if (position==0){
                final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.newsimg);
                holder.label.setLayoutParams(params);
            }*/
                    TableRow tbrow = new TableRow(contextForFragmentadp);
                    TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
                    tbrow.setLayoutParams(tlp);
                    Drawable xw = contextForFragmentadp.getResources().getDrawable(R.drawable.textinputborder1);
                    //tbrow.setBackgroundResource(android.R.drawable.gallery_thumb);

                    TextView val = UpdateAsyncdata((String) values.get("CLG_NAME"));
                    TextView val1 = UpdateAsyncdata123((String) values.get("CLG_CITY"));
                    TextView val2 = UpdateAsyncdata123((String) values.get("CLG_STATE"));
                    TextView val3 = UpdateAsyncdata123((String) values.get("CLG_SCORE"));
                    TextView val4 = UpdateAsyncdata123((String) values.get("CLG_RANK"));
                //    TextView val5 = UpdateAsyncdata((String) values.get("QUOTA_HS_CR"));
                  //  TextView val6 = UpdateAsyncdataline((String) values.get("COURSE"));
                    tbrow.addView(val);
                   // tbrow.addView(val6);
                    tbrow.addView(val1);
                   // tbrow.addView(val6);
                    tbrow.addView(val2);
                  //  tbrow.addView(val6);
                    tbrow.addView(val3);
                  //  tbrow.addView(val6);
                    tbrow.addView(val4);


                    ((NewsViewHolder) holder).stk.addView(tbrow);

                }


            }}


    }
    @Override
    public int getItemViewType(int position) {

            return TYPE_NEWS;

        }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1;
    }
    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }
    public TextView UpdateAsyncdata(String StringNews) {
        Drawable xw = contextForFragmentadp.getResources().getDrawable(R.drawable.textinputborder4);
        TextView t1v = new TextView(contextForFragmentadp);
        t1v.setHeight(115);
        t1v.setWidth(450);
        t1v.setTextSize(10);
        t1v.setPadding(15,15,15,15);
        t1v.setText(StringNews);
        t1v.setTextColor(Color.BLACK);
      //  t1v.setTextAppearance(contextForFragmentadp, R.style.TextViewMyTheme);
       // t1v.setGravity(Gravity.CENTER);++
       // t1v.setBackground(xw);
        t1v.setTypeface(Typeface.create("arial", Typeface.NORMAL));
        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.parseColor("#40000000"));
        t1v.setBackground(border);
        return t1v;
    }
    public TextView UpdateAsyncdata123(String StringNews) {
        Drawable xw = contextForFragmentadp.getResources().getDrawable(R.drawable.textinputborder4);
        TextView t1v = new TextView(contextForFragmentadp);
        t1v.setHeight(115);
        t1v.setTextSize(10);
        t1v.setPadding(15,15,15,15);
        t1v.setText(StringNews);
        t1v.setTextColor(Color.BLACK);
        //  t1v.setTextAppearance(contextForFragmentadp, R.style.TextViewMyTheme);
        // t1v.setGravity(Gravity.CENTER);++
        // t1v.setBackground(xw);
        t1v.setTypeface(Typeface.create("arial", Typeface.NORMAL));
        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.parseColor("#40000000"));
        t1v.setBackground(border);
        return t1v;
    }
    public TextView UpdateAsyncdataheader(String StringNews) {
        Drawable xw = contextForFragmentadp.getResources().getDrawable(R.drawable.textinputborder4);
        TextView t1v = new TextView(contextForFragmentadp);

        t1v.setHeight(115);
       // t1v.setHeight(135);

        t1v.setPadding(25,25,25,25);
        t1v.setText(StringNews);
        t1v.setTextSize(15);
        t1v.setTextColor(Color.parseColor("#4292f4"));
        t1v.setTypeface(t1v.getTypeface(), Typeface.BOLD);
        t1v.setTypeface(null, Typeface.BOLD);
        //  t1v.setTextAppearance(contextForFragmentadp, R.style.TextViewMyTheme);
      //  t1v.setGravity(Gravity.CENTER);
        // t1v.setBackground(xw);
        t1v.setTypeface(Typeface.create("arial", Typeface.NORMAL));
        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.parseColor("#40000000"));
        t1v.setBackground(border);
        return t1v;
    }
    public TextView UpdateAsyncdataheader1(String StringNews) {
        Drawable xw = contextForFragmentadp.getResources().getDrawable(R.drawable.textinputborder4);
        TextView t1v = new TextView(contextForFragmentadp);
        t1v.setHeight(100);
        t1v.setPadding(15,15,15,15);
        t1v.setText(StringNews);
        t1v.setTextSize(15);
        t1v.setTextColor(Color.BLACK);
        t1v.setBackgroundColor(Color.parseColor("#40000000"));
        t1v.setTypeface(t1v.getTypeface(), Typeface.BOLD);
        t1v.setTypeface(null, Typeface.BOLD);
        //  t1v.setTextAppearance(contextForFragmentadp, R.style.TextViewMyTheme);
        t1v.setGravity(Gravity.CENTER);
        // t1v.setBackground(xw);
        t1v.setTypeface(Typeface.create("arial", Typeface.NORMAL));
 /*       ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.BLACK);
        t1v.setBackground(border);*/
        return t1v;
    }
/*    public TextView UpdateAsyncdataline(String StringNews) {
         Drawable xw = contextForFragmentadp.getResources().getDrawable(R.drawable.textinputborder4);
        TextView t1v = new TextView(contextForFragmentadp);
        t1v.setHeight(5);

        t1v.setText("");
        t1v.setTextColor(Color.GRAY);


      *//*  ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.BLACK);*//*
        t1v.setBackground(xw);
        return t1v;
    }*/
    interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}