package com.mobilebytes.andhand.asyntask;

import com.mobilebytes.andhand.activities.AndHandActivity;
import com.mobilebytes.andhand.application.AndHandApplication;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Window;

/**
 *
 * @author FredGrott
 *
 * We implemented a callback to grab the context and state.
 *
 * @param <ParameterT>
 * @param <ProgressT>
 * @param <ReturnT>
 */
public class BackgroundTask<ParameterT, ProgressT, ReturnT> extends
AsyncTask<ParameterT, ProgressT, ReturnT>  {

	private final AndHandApplication appContext;
	private final boolean contextIsAndHandActivity;

	private Exception error;

    private boolean isTitleProgressEnabled,
            isTitleProgressIndeterminateEnabled = true;

    private final String callerId;

    private BackgroundTaskCallable<ParameterT, ProgressT, ReturnT> callable;

    private int dialogId = 0;


    /**
     * Creates a new BackgroundTask who displays a progress dialog on the
     * specified Context.
     *
     * @param context
     */
    public BackgroundTask(Context context) {

        if (!(context.getApplicationContext() instanceof AndHandApplication)) {
            throw new IllegalArgumentException(
                    "context bound to this task must be a DroidFu context (DroidFuApplication)");
        }
        this.appContext = (AndHandApplication) context.getApplicationContext();
        this.callerId = context.getClass().getCanonicalName();
        this.contextIsAndHandActivity = context instanceof AndHandActivity;

        appContext.setActiveContext(callerId, context);

        if (contextIsAndHandActivity) {
            int windowFeatures = ((AndHandActivity) context).getWindowFeatures();
            if (Window.FEATURE_PROGRESS == (Window.FEATURE_PROGRESS & windowFeatures)) {
                this.isTitleProgressEnabled = true;
            } else if (Window.FEATURE_INDETERMINATE_PROGRESS == (Window.FEATURE_INDETERMINATE_PROGRESS & windowFeatures)) {
                this.isTitleProgressIndeterminateEnabled = true;
            }
        }
    }

    /**
     * Gets the most recent instance of this Context.
     * This may not be the Context used to construct this BackgroundTask as
     * that Context might have been destroyed
     * when a incoming call was received, or the user rotated the screen.
     *
     * @return The current Context, or null if the current Context has ended,
     * and a new one has not spawned.
     */
    protected Context getCallingContext() {
        try {
            Context caller = (Context) appContext.getActiveContext(callerId);
            if (caller == null || !this.callerId.equals(caller.getClass().getCanonicalName())
                    || (caller instanceof Activity && ((Activity) caller).isFinishing())) {
                // the context that started this task has died and/or was
                // replaced with a different one
                return null;
            }
            return caller;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



	@Override
	protected ReturnT doInBackground(ParameterT... params) {
		// TODO Auto-generated method stub
		return null;
	}


}
