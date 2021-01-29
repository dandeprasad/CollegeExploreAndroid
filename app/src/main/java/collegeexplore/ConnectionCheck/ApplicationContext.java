package collegeexplore.ConnectionCheck;

import android.content.Context;
import android.support.multidex.MultiDexApplication;


public class ApplicationContext extends MultiDexApplication {

    private static ApplicationContext mInstance;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        ApplicationContext.context = getApplicationContext();
    }

    public static synchronized ApplicationContext getInstance() {
        return mInstance;
    }
    public static Context getAppContext() {
        return ApplicationContext.context;
    }
    public void setConnectivityListener(ConnectivityCheck.ConnectivityReceiverListener listener) {
        ConnectivityCheck.connectivityReceiverListener = listener;
    }
}
