package collegeexplore.CollegeInfo;

/**
 * Created by DANDE on 08-01-2017.
 */

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Util {
    public static String getProperty(String key,Context context) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("serverDetails.properties");
        properties.load(inputStream);
        return properties.getProperty(key);



    }
    public static String Nullcheck(String key) throws Exception {
if(key==null){
    key="";
}
        return key;



    }

}