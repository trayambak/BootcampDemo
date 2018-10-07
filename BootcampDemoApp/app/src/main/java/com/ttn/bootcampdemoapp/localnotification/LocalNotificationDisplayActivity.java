package com.ttn.bootcampdemoapp.localnotification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ttn.bootcampdemoapp.Constants;
import com.ttn.bootcampdemoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LocalNotificationDisplayActivity extends AppCompatActivity {

    @BindView(R.id.notification_display_text_view)
    TextView mNotificationDisplayTextView;

    private Unbinder mUnbinder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_notification_display);
        mUnbinder = ButterKnife.bind(this);

        //getting the notification message from intent extras
        String message = getIntent().getStringExtra(Constants.INTENT_KEY_MESSAGE);
        mNotificationDisplayTextView.setText(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
