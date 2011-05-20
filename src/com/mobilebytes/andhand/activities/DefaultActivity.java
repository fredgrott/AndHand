package com.mobilebytes.andhand.activities;

import java.util.List;

import com.mobilebytes.andhand.dialogs.DialogClickListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.mobilebytes.andhand.application.*;

public class DefaultActivity extends Activity implements AndHandActivity{

	private boolean wasCreated, wasInterrupted;

    private int progressDialogTitleId;

    private int progressDialogMsgId;

    private Intent currentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.wasCreated = true;
        this.currentIntent = getIntent();

        Application application = getApplication();
        if (application instanceof AndHandApplication) {
        	((AndHandApplication) application).setActiveContext(getClass().getCanonicalName(), this);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        wasInterrupted = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasCreated = wasInterrupted = false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.currentIntent = intent;
    }



	@Override
	public int getWindowFeatures() {
		return ActivityHelper.getWindowFeatures(this);

	}

	 public void setProgressDialogTitleId(int progressDialogTitleId) {
	        this.progressDialogTitleId = progressDialogTitleId;
	    }

	public void setProgressDialogMsgId(int progressDialogMsgId) {
	        this.progressDialogMsgId = progressDialogMsgId;
	    }



	public boolean isRestoring() {
        return wasInterrupted;
    }

    public boolean isResuming() {
        return !wasCreated;
    }

    public boolean isLaunching() {
        return !wasInterrupted && wasCreated;
    }


	@Override
	public boolean isApplicationBroughtToBackground() {
		return ActivityHelper.isApplicationBroughtToBackground(this);

	}

	public Intent getCurrentIntent() {
        return currentIntent;
    }

    public boolean isLandscapeMode() {
        return getWindowManager().getDefaultDisplay().getOrientation() == 1;
    }

    public boolean isPortraitMode() {
        return !isLandscapeMode();
    }


	@Override
	public void onConfigurationChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRetainConfigurationInstance() {
		// TODO Auto-generated method stub

	}

	 public AlertDialog newYesNoDialog(int titleResourceId, int messageResourceId,
	            OnClickListener listener) {
	        return ActivityHelper.newYesNoDialog(this, getString(titleResourceId),
	            getString(messageResourceId), android.R.drawable.ic_dialog_info, listener);
	    }

	    public AlertDialog newInfoDialog(int titleResourceId, int messageResourceId) {
	        return ActivityHelper.newMessageDialog(this, getString(titleResourceId),
	            getString(messageResourceId), android.R.drawable.ic_dialog_info);
	    }

	    public AlertDialog newAlertDialog(int titleResourceId, int messageResourceId) {
	        return ActivityHelper.newMessageDialog(this, getString(titleResourceId),
	            getString(messageResourceId), android.R.drawable.ic_dialog_alert);
	    }

	    public AlertDialog newErrorHandlerDialog(int titleResourceId, Exception error) {
	        return ActivityHelper.newErrorHandlerDialog(this, getString(titleResourceId), error);
	    }

	    public AlertDialog newErrorHandlerDialog(Exception error) {
	        return newErrorHandlerDialog(getResources().getIdentifier(
	            ActivityHelper.ERROR_DIALOG_TITLE_RESOURCE, "string", getPackageName()), error);
	    }

	    public <T> Dialog newListDialog(String title, List<T> elements,
	            DialogClickListener<T> listener,
	            boolean closeOnSelect) {
	        return ActivityHelper.newListDialog(this, title, elements, listener, closeOnSelect);
	    }

	    @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        ActivityHelper.handleApplicationClosing(this, keyCode);
	        return super.onKeyDown(keyCode, event);
	    }


}
