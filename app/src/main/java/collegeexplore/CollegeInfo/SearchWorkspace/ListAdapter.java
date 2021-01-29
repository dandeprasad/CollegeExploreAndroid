package collegeexplore.CollegeInfo.SearchWorkspace;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import collegeexplore.CollegeInfo.CollegesAtMaps.homeMapsActivity;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.databinding.RowItemBinding;

/**
 * Created by anupamchugh on 07/02/16.
 */
public class ListAdapter extends BaseAdapter implements Filterable {

    List<String> mData;
    List<String> mData_clg_id;
    List<String> mStringFilterList;
    ValueFilter valueFilter;
    private LayoutInflater inflater;
    SearchActivity context1;

    public ListAdapter(List<String> cancel_type, SearchActivity searchActivity, List<String> arrayList_CLGID) {
        mData=cancel_type;
        mStringFilterList = cancel_type;
        context1= searchActivity;
        mData_clg_id=arrayList_CLGID;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        final RowItemBinding rowItemBinding = DataBindingUtil.inflate(inflater, R.layout.row_item, parent, false);
        rowItemBinding.stringName.setText(mData.get(position));
        rowItemBinding.stringName.setTag(mData_clg_id.get(position));
        rowItemBinding.stringName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context1, "MyLocation button "+position+mData.get(position)+rowItemBinding.stringName.getTag(), Toast.LENGTH_SHORT).show();
               // new homeMapsActivity.GetWholeClgDataTask(clickItem,(String)CollegeName.get(clickItem)).execute(serverUrl);
                Intent intent = new Intent(context1,
                        homeMapsActivity.class);
                intent.putExtra("collegeID",(String) rowItemBinding.stringName.getTag());
                intent.putExtra("collegeName",mData.get(position));
                intent.putExtra("FROM_ACTIVITY","SEARCH_ACTIVITY");
                context1.startActivity(intent);
            }
        });

        return rowItemBinding.getRoot();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<String> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mData = (List<String>) results.values;
            notifyDataSetChanged();
        }

    }

}
