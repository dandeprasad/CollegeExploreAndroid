package collegeexplore.CollegeInfo;

/**
 * Created by DANDE on 16-02-2018.
 */

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class awstestactivity extends Activity {
    public static final String PREFS_NAME1 = "NewsNotifyPatch";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aws_test);
        AWSMobileClient.getInstance().initialize(this).execute();
     //   uploadData();
        downloadData();
    }

    public void downloadData() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("topcitiesdande", MODE_PRIVATE);
        // Create imageDir
        final File mypath=new File(directory,"collegeexplore.jpg");

       // final File f= new File("positiondande2.jpg");
        // Initialize AWSMobileClient if not initialized upon the app startup.
        // AWSMobileClient.getInstance().initialize(this).execute();

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();

        TransferObserver downloadObserver =
                transferUtility.download(
                        "collegeexplore.jpg",mypath
                       );
        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {

                    try {
                        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));
                        ImageView sdf=findViewById(R.id.imagerest);
                        sdf.setImageBitmap(b);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(awstestactivity.this,"succeess", Toast.LENGTH_SHORT).show();
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("MainActivity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
                Toast.makeText(awstestactivity.this,"failed", Toast.LENGTH_SHORT).show();
            }

        });
    }
    public void uploadData() {

        // Initialize AWSMobileClient if not initialized upon the app startup.
        // AWSMobileClient.getInstance().initialize(this).execute();

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();
        SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME1, 0);
        String pathtoget =prefs.getString("imagepath0"+Integer.toString(2),null);
       // new File(pathtoget, "position2.jpg");
        TransferObserver uploadObserver =
                transferUtility.upload(
                        "positiondande2.jpg",
                        new File(pathtoget, "position2.jpg"));

        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                    Toast.makeText(awstestactivity.this, "succees", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("MainActivity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
                Toast.makeText(awstestactivity.this,"failed", Toast.LENGTH_SHORT).show();
            }

        });

        // If your upload does not trigger the onStateChanged method inside your
        // TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
            String d="affg";
            Toast.makeText(awstestactivity.this, "done", Toast.LENGTH_SHORT).show();
        }
    }
}