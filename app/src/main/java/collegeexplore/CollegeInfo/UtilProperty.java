package collegeexplore.CollegeInfo;

/**
 * Created by DANDE on 08-01-2017.
 */

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UtilProperty {
    public static String getProperty(String key,Context context,String propertyfile) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(propertyfile);
        properties.load(inputStream);
        return properties.getProperty(key);

    }
}