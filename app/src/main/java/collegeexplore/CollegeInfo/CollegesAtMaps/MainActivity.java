package collegeexplore.CollegeInfo.CollegesAtMaps;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import collegeexplore.CollegeInfo.R;

public class MainActivity extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	ArrayList<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandable_view);

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(
						getApplicationContext(),
						listDataHeader.get(groupPosition)
								+ " : "
								+ listDataChild.get(
										listDataHeader.get(groupPosition)).get(
										childPosition), Toast.LENGTH_SHORT)
						.show();
				return false;
			}
		});
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("States");
		listDataHeader.add("Cities");
		listDataHeader.add("More will be added soon");

		// Adding child data
		List<String> States = new ArrayList<String>();
		States.add("The Shawshank Redemption");
		States.add("The Godfather");
		States.add("The Godfather: Part II");
		States.add("Pulp Fiction");
		States.add("The Good, the Bad and the Ugly");
		States.add("The Dark Knight");
		States.add("12 Angry Men");

		List<String> Cities = new ArrayList<String>();
		Cities.add("The Conjuring");
		Cities.add("Despicable Me 2");
		Cities.add("Turbo");
		Cities.add("Grown Ups 2");
		Cities.add("Red 2");
		Cities.add("The Wolverine");

		List<String> More = new ArrayList<String>();


		listDataChild.put(listDataHeader.get(0), States); // Header, Child data
		listDataChild.put(listDataHeader.get(1), Cities);
		listDataChild.put(listDataHeader.get(2), More);
	}
}
