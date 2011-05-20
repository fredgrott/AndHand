package com.mobilebytes.andhand.application;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.app.Application;
import android.content.Context;


/**
 * AndhAnd library usage requires that you create
 * an application class in your application and extend
 * the AndHandApplication class
 *
 * @author FredGrott
 *
 */
public class AndHandApplication extends Application {

	private HashMap<String, WeakReference<Context>> contextObjects =
			new HashMap<String, WeakReference<Context>>();

	public synchronized Context getActiveContext(String className) {
        WeakReference<Context> ref = contextObjects.get(className);
        if (ref == null) {
            return null;
        }

        final Context c = ref.get();
        if (c == null) // If the WeakReference is no longer valid, ensure it is removed.
            contextObjects.remove(className);

        return c;
    }





	public synchronized void setActiveContext(String className, Context context) {
        WeakReference<Context> ref = new WeakReference<Context>(context);
        this.contextObjects.put(className, ref);
    }

    public synchronized void resetActiveContext(String className) {
        contextObjects.remove(className);
    }






	 public void onClose() {
	        // NO-OP by default
	    }




}
