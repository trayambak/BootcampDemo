package com.ttn.bootcampdemoapp.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ttn.bootcampdemoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JobSchedulerExampleActivity extends AppCompatActivity {

    private static final String TAG = JobSchedulerExampleActivity.class.getSimpleName();
    @BindView(R.id.schedule_job_button)
    Button mScheduleJobButton;
    @BindView(R.id.cancel_job_button)
    Button mCancelJobButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler_example);
        ButterKnife.bind(this);
    }

    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, MyJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

    public void cancelJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelled");
    }

    @OnClick({R.id.schedule_job_button, R.id.cancel_job_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.schedule_job_button:
                scheduleJob();
                break;
            case R.id.cancel_job_button:
                cancelJob();
                break;
        }
    }
}
