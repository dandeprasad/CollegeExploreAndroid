package collegeexplore.CollegeInfo.NewsImageSliders;

/**
 * Created by DANDE on 07-08-2017.
 */

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import collegeexplore.CollegeInfo.R;

public class FlipperAdapter extends BaseAdapter {

    private AppCompatActivity appCompatActivity;
    private List<String> strings;
    private int[] drawableIds = {R.drawable.back_button, R.drawable.back_button, R.drawable.back_button, R.drawable.back_button,
            R.drawable.back_button, R.drawable.back_button, R.drawable.back_button, R.drawable.back_button,
            R.drawable.back_button};

    public FlipperAdapter(AppCompatActivity appCompatActivity, List<String> strings) {
        super();
        this.strings = strings;
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public String getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return strings.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) appCompatActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.news_item_page, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(getItem(position));
        holder.imageView.setImageResource(drawableIds[position]);

        return convertView;
    }

    private static class ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(View v) {
            imageView = v.findViewById(R.id.image);
            textView = v.findViewById(R.id.text);
        }
    }
}