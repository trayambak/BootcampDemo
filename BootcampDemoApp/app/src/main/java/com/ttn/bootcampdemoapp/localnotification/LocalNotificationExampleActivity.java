package com.ttn.bootcampdemoapp.localnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ttn.bootcampdemoapp.Constants;
import com.ttn.bootcampdemoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LocalNotificationExampleActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 1;

    @BindView(R.id.send_notification_button)
    Button mSendNotificationButton;

    private Unbinder mUnbinder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_notification_example);
        mUnbinder = ButterKnife.bind(this);
    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Notification Title")
                .setContentText("Notification Message")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        createNotificationChannel();

        // Create an explicit intent for an Activity in your app
        Intent notificationIntent = new Intent(this, LocalNotificationDisplayActivity.class);

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView
        notificationIntent.putExtra(Constants.INTENT_KEY_MESSAGE, "This is a notification message");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // Set the intent that will fire when the user taps the notification
        builder.setContentIntent(pendingIntent);

        // Notice this code calls setAutoCancel(), which automatically
        // removes the notification when the user taps it.
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must
        // define, Remember to save the notification ID that you pass to NotificationManagerCompat.notify()
        // because you'll need it later if you want to update or remove the notification.
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @OnClick(R.id.send_notification_button)
    public void onViewClicked() {
        sendNotification();
    }

    /**
     * Because you must create the notification channel before
     * posting any notifications on Android 8.0 and higher,
     * you should execute this code as soon as your app starts.
     * It's safe to call this repeatedly because creating an existing notification
     * channel performs no operation.
     */
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);

            /*
            NotificationChannel constructor requires an importance, using one of the constants from the NotificationManager class.
            This parameter determines how to interrupt the user for any notification that belongs
            to this channel—though you must also set the priority with setPriority() to
            support Android 7.1 and lower
             */
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
