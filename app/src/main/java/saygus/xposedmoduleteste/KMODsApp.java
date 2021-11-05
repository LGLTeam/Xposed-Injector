package saygus.xposedmoduleteste;

import android.app.Application;
import java.io.IOException;

public class KMODsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
