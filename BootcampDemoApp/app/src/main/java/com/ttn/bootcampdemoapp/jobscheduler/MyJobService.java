package com.ttn.bootcampdemoapp.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class MyJobService extends JobService {

    private static final String TAG = MyJobService.class.getSimpleName();
    private boolean mJobCancelled = false;

    /**
     * return true if you have to make some background operation
     *
     * @param params need to pass job params to stop job
     * @return
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "run: " + i);
                    if (mJobCancelled) {
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        mJobCancelled = true;
        return true;
    }
}
