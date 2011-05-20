package com.mobilebytes.andhand.support;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.UUID;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

public class DiagnosticSupport {

	public static final int ANDROID_API_LEVEL;
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    static {
        int apiLevel = -1;
        try {
            apiLevel = Build.VERSION.class.getField("SDK_INT").getInt(null);
        } catch (Exception e) {
            apiLevel = Integer.parseInt(Build.VERSION.SDK);
        }
        ANDROID_API_LEVEL = apiLevel;
    }

    /**
     * Most have this wrong. We want an unique installation
     * as ANDROID_ID will not be unique if its wiped as its reset pre 2.2
     * @param context
     * @return uniqueID
     */
    public synchronized static String getAndroidId(Context context) {

    	if (uniqueID == null) {
          SharedPreferences sp = context.getSharedPreferences(
        		  PREF_UNIQUE_ID, Context.MODE_PRIVATE);
          uniqueID = sp.getString(PREF_UNIQUE_ID, null);
          if(uniqueID == null){
        	  uniqueID = UUID.randomUUID().toString();
        	  Editor editor = sp.edit();
        	  editor.putString(PREF_UNIQUE_ID, uniqueID);
        	  editor.commit();
          }
    	}


    	return uniqueID;
    }

    public static String getApplicationVersionString(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return "v" + info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String createDiagnosis(Activity context, Exception error) {
        StringBuilder sb = new StringBuilder();

        sb.append("Application version: " + getApplicationVersionString(context) + "\n");
        sb.append("Device locale: " + Locale.getDefault().toString() + "\n\n");
        sb.append("Android ID: " + getAndroidId(context));

        // phone information
        sb.append("PHONE SPECS\n");
        sb.append("model: " + Build.MODEL + "\n");
        sb.append("brand: " + Build.BRAND + "\n");
        sb.append("product: " + Build.PRODUCT + "\n");
        sb.append("device: " + Build.DEVICE + "\n\n");

        // android information
        sb.append("PLATFORM INFO\n");
        sb.append("Android " + Build.VERSION.RELEASE + " " + Build.ID + " (build "
                + Build.VERSION.INCREMENTAL + ")\n");
        sb.append("build tags: " + Build.TAGS + "\n");
        sb.append("build type: " + Build.TYPE + "\n\n");

        // settings
        sb.append("SYSTEM SETTINGS\n");
        String networkMode = null;
        ContentResolver resolver = context.getContentResolver();
        try {
            if (Settings.Secure.getInt(resolver, Settings.Secure.WIFI_ON) == 0) {
                networkMode = "DATA";
            } else {
                networkMode = "WIFI";
            }
            sb.append("network mode: " + networkMode + "\n");
            sb.append("HTTP proxy: "
                    + Settings.Secure.getString(resolver, Settings.Secure.HTTP_PROXY) + "\n\n");
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }

        sb.append("STACK TRACE FOLLOWS\n\n");

        StringWriter stackTrace = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTrace));

        sb.append(stackTrace.toString());

        return sb.toString();
    }


}
