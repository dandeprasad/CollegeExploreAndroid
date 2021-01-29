package collegeexplore.CollegeInfo.NewsImageSliders;

/**
 * Created by DANDE on 08-08-2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aphidmobile.flip.FlipViewController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import collegeexplore.CollegeInfo.R;

public class NewsImageActivity extends AppCompatActivity {

    private FlipViewController flipViewController;
    private FlipperAdapter adapter;
    private ArrayList<String> stringArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_flip_activity);

        flipViewController = (FlipViewController)findViewById(R.id.flip_view);
        stringArrayList = new ArrayList<>();
        stringArrayList.add("Unlike some of the numeric methods of class StrictMath, all implementations of the equivalent functions of class Math are not defined to return the bit-for-bit same results. This relaxation permits better-performing implementations where strict reproducibility is not required.\n" +
                "\n" +
                "By default many of the Math methods simply call the equivalent method in StrictMath for their implementation. Code generators are encouraged to use platform-specific native libraries or microprocessor instructions, where available, to provide higher-performance implementations of Math methods. Such higher-performance implementations still must conform to the specification for Math.");//Adding object in arraylist
        stringArrayList.add("Unlike some of the numeric methods of class StrictMath, all implementations of the equivalent functions of class Math are not defined to return the bit-for-bit same results. This relaxation permits better-performing implementations where strict reproducibility is not required.\n" +
                "\n" +
                "By default many of the Math methods simply call the equivalent method in StrictMath for their implementation. Code generators are encouraged to use platform-specific native libraries or microprocessor instructions, where available, to provide higher-performance implementations of Math methods. Such higher-performance implementations still must conform to the specification for Math.");
        stringArrayList.add("Unlike some of the numeric methods of class StrictMath, all implementations of the equivalent functions of class Math are not defined to return the bit-for-bit same results. This relaxation permits better-performing implementations where strict reproducibility is not required.\n" +
                "\n" +
                "By default many of the Math methods simply call the equivalent method in StrictMath for their implementation. Code generators are encouraged to use platform-specific native libraries or microprocessor instructions, where available, to provide higher-performance implementations of Math methods. Such higher-performance implementations still must conform to the specification for Math.");
        stringArrayList.add("Ajay");
        // readDataFromAssets();

        //create and attach adapter to flipper view
        adapter = new FlipperAdapter(this, stringArrayList);
        flipViewController.setAdapter(adapter);
    }

    private void readDataFromAssets() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("loremipsum.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                stringArrayList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}