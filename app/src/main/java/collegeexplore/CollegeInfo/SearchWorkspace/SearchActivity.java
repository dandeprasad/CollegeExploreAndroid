package collegeexplore.CollegeInfo.SearchWorkspace;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.databinding.SearchActivityMainBinding;


public class SearchActivity extends AppCompatActivity {

    SearchActivityMainBinding activityMainBinding;
    ListAdapter adapter;

    List<String> arrayList= new ArrayList<>();
    List<String> arrayList_CLGID= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //  final Toolbar HomeToolbar = (Toolbar) findViewById(R.id.my_toolbar_search);


        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.search_activity_main);
        final Toolbar HomeToolbar = activityMainBinding.myToolbarSearch;
        setSupportActionBar(HomeToolbar);
        getSupportActionBar().setTitle("College Explore");
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up but     ton
        ab.setDisplayHomeAsUpEnabled(true);
        arrayList.add("National Institute of Technology, Agartala");

        arrayList.add("Motilal Nehru National Institute of Technology, Allahabad");
        arrayList.add("National Institute of Technology, Tadepalligudem");
        arrayList.add("National Institute of Technology, Arunachal Pradesh (Yupia)");
        arrayList.add("Maulana Azad National Institute of Technology, Bhopal");
        arrayList.add("National Institute of Technology, Calicut");
        arrayList.add("National Institute of Technology, Delhi");
        arrayList.add("National Institute of Technology, Durgapur");
        arrayList.add("National Institute of Technology, Goa");
        arrayList.add("National Institute of Technology, Hamirpur");
        arrayList.add("Malaviya National Institute of Technology, Jaipur");
        arrayList.add("Dr. B. R. Ambedkar National Institute of Technology, Jalandhar");
        arrayList.add("National Institute of Technology, Jamshedpur");
        arrayList.add("National Institute of Technology, Kurukshetra");
        arrayList.add("National Institute of Technology, Manipur");
        arrayList.add("National Institute of Technology, Meghalaya");
        arrayList.add("National Institute of Technology Mizoram");
        arrayList.add("National Institute of Technology, Nagaland");
        arrayList.add("Visvesvaraya National Institute of Technology, Nagpur");
        arrayList.add("National Institute of Technology, Patna");
        arrayList.add("National Institute of Technology, Pondicherry");
        arrayList.add("National Institute of Technology, Raipur");
        arrayList.add("National Institute of Technology, Rourkela");
        arrayList.add("National Institute of Technology Sikkim");
        arrayList.add("National Institute of Technology, Silchar");
        arrayList.add("National Institute of Technology, Srinagar");
        arrayList.add("S V National Institute of Technology, Surat");
        arrayList.add("National Institute of Technology Karnataka, Surathkal");
        arrayList.add("National Institute of Technology, Trichy");
        arrayList.add("National Institute of Technology Uttarakhand");
        arrayList.add("National Institute of Technology, Warangal");
        arrayList_CLGID.add("NIT_AG");
        arrayList_CLGID.add("NIT_ALLAHABAD");
        arrayList_CLGID.add("NIT_ANDHRA");
        arrayList_CLGID.add("NIT_AR_PRDSH");
        arrayList_CLGID.add("NIT_BHOPAL");
        arrayList_CLGID.add("NIT_CA");
        arrayList_CLGID.add("NIT_DEL");
        arrayList_CLGID.add("NIT_DGPR");
        arrayList_CLGID.add("NIT_GOA");
        arrayList_CLGID.add("NIT_HAMPR");
        arrayList_CLGID.add("NIT_JAIPUR");
        arrayList_CLGID.add("NIT_JAL");
        arrayList_CLGID.add("NIT_JAMSPR");
        arrayList_CLGID.add("NIT_KURU");
        arrayList_CLGID.add("NIT_MANI");
        arrayList_CLGID.add("NIT_MEGA");
        arrayList_CLGID.add("NIT_MIZ");
        arrayList_CLGID.add("NIT_NAGLND");
        arrayList_CLGID.add("NIT_NAGPUR");
        arrayList_CLGID.add("NIT_PATNA");
        arrayList_CLGID.add("NIT_PY");
        arrayList_CLGID.add("NIT_RAIPUR");
        arrayList_CLGID.add("NIT_ROURK");
        arrayList_CLGID.add("NIT_SIKKIM");
        arrayList_CLGID.add("NIT_SILCHAR");
        arrayList_CLGID.add("NIT_SRINGR");
        arrayList_CLGID.add("NIT_SURAT");
        arrayList_CLGID.add("NIT_SURUTKAL");
        arrayList_CLGID.add("NIT_TRICHY");
        arrayList_CLGID.add("NIT_UTTARAK");
        arrayList_CLGID.add("NIT_WANGL");
        adapter= new ListAdapter(arrayList,this,arrayList_CLGID);
        activityMainBinding.listView.setAdapter(adapter);

        activityMainBinding.search.setActivated(true);
        activityMainBinding.search.setQueryHint("Search Colleges");
        activityMainBinding.search.onActionViewExpanded();
        activityMainBinding.search.setIconified(false);
        activityMainBinding.search.clearFocus();
        activityMainBinding.search.setIconifiedByDefault(true);
        activityMainBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });
    }
}
