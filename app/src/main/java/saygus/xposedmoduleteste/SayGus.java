package saygus.xposedmoduleteste;

import android.content.Context;

import java.io.IOException;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class SayGus implements IXposedHookLoadPackage {
    public static final String TAG = "Musk";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.dts.freefireth"))
            return;

        XposedBridge.log(TAG +  " " + lpparam.packageName);

        try {
            System.load("/data/local/tmp/libsaygus.so");
            XposedBridge.log(TAG + " lib carregada");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
