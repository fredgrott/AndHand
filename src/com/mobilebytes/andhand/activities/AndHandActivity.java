package com.mobilebytes.andhand.activities;

import java.util.List;

import com.mobilebytes.andhand.dialogs.DialogClickListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

/**
 * Improving the Activity class through an interface in which we
 * extend the android activity and implement this interface to use.
 *
 * @author FredGrott
 *
 */
public interface AndHandActivity {

	public int getWindowFeatures();

    public void setProgressDialogTitleId(int progressDialogTitleId);

    public void setProgressDialogMsgId(int progressDialogMsgId);

    /**
     * @return true, if the activity is recovering from in interruption (i.e.
     *         onRestoreInstanceState was called.
     */
    public boolean isRestoring();

    /**
     * @return true, if the activity is "soft-resuming", i.e. onResume has been
     *         called without a prior call to onCreate
     */
    public boolean isResuming();

    /**
     * @return true, if the activity is launching, i.e. is going through
     *         onCreate but is not restoring.
     */
    public boolean isLaunching();

    /**
     * Android doesn't distinguish between your Activity being paused by another
     * Activity of your own application, or by an Activity of an entirely
     * different application. This function only returns true, if your Activity
     * is being paused by an Activity of another app, thus hiding yours.
     *
     * @return true, if the Activity is being paused because an Activity of
     *         another application received focus.
     */
    public boolean isApplicationBroughtToBackground();

    /**
     * Retrieves the current intent that was used to create or resume this
     * activity. If the activity received a call to onNewIntent (e.g. because it
     * was launched in singleTop mode), then the Intent passed to that method is
     * returned. Otherwise the returned Intent is the intent returned by
     * getIntent (which is the Intent which was used to initially launch this
     * activity).
     *
     * @return the current {@link Intent}
     */
    public Intent getCurrentIntent();

    /**
     * return true if in Landscape mode
     * @return
     */
    public boolean isLandscapeMode();

    /**
     * return true if in portrait mode
     * @return
     */
    public boolean isPortraitMode();

    /**
     * onConfigurationChanged indicates an orientation change which
     * than destroys and recreates and activity.
     */
    public void onConfigurationChanged();

    /**
     * if object being serialized is too large retain the object
     * during a configuration change such as a orientation change or
     * virtual keyboard is called, etc.
     *
     * Use for data only, objects that connect to views, act or app context, etc
     * cannot be passed this way to the recreated activity.
     *
     * certain views can be restored by having it ID named and making sure that
     * when overriding onCreate,onRestoreInstanceState, or onSaveInstanceState
     * that you call super.methodName(Bundle savedInstanceState)
     */
    public void onRetainConfigurationInstance();

    public void onLowMemory();

    public AlertDialog newYesNoDialog(int titleResourceId, int messageResourceId,
            OnClickListener listener);

    public AlertDialog newInfoDialog(int titleResourceId, int messageResourceId);

    public AlertDialog newAlertDialog(int titleResourceId, int messageResourceId);

    public AlertDialog newErrorHandlerDialog(int titleResourceId, Exception error);

    public AlertDialog newErrorHandlerDialog(Exception error);

    public <T> Dialog newListDialog(String title, final List<T> listItems,
            final DialogClickListener<T> listener, boolean closeOnSelect);




}
