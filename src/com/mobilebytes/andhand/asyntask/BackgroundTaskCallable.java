package com.mobilebytes.andhand.asyntask;

public interface BackgroundTaskCallable<ParameterT, ProgressT, ReturnT>  {

	public ReturnT call(BackgroundTask<ParameterT, ProgressT, ReturnT> task) throws Exception;

	}

