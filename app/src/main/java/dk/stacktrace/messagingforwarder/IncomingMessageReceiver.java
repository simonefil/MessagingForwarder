package dk.stacktrace.messagingforwarder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class IncomingMessageReceiver extends BroadcastReceiver {
    private static final String TAG = IncomingMessageReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences;
        String phone_number;
        URL target_url;
        SmsMessage[] messages;
        boolean isRegex;
        String smsContentFilter;
        Pattern regexPattern = null;

        // Log
        Log.i(TAG, "Handling message for forwarding");

        // Get preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        // If neither the phone number nor the sms content is specified log an error and return
        if (!preferences.contains("phone_number") && !preferences.contains("sms_content")) {
            Log.w(TAG, "Phone number to forward from or sms content not set. Will not forward any messages");
            return;
        }

        // If the target URL is not specified log an error and return
        if (!preferences.contains("target_URL")) {
            Log.w(TAG, "URL to forward to not set. Will not forward any messages");
            return;
        }

        // Read the phone number from the textbox
        phone_number = preferences.getString("phone_number", "");
        smsContentFilter = preferences.getString("sms_content", "");

        // Check if the filter is a Regex
        isRegex = smsContentFilter.startsWith("<Regex>");

        // Get the target URL
        try {
            target_url = new URL(preferences.getString("target_URL", ""));
        } catch (MalformedURLException e) {
            Log.w(TAG, "Unable to parse URL: " + e.getMessage());
            return;
        }

        // Get the messages
        messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        // For each SMS ..
        for (SmsMessage message : messages) {

            // If the content filter is a regex and the pattern matcher is not initialized, initialize it
            if (isRegex && regexPattern == null) {
                smsContentFilter = smsContentFilter.replace("<Regex>", "");
                smsContentFilter = smsContentFilter.replace("</Regex>", "");
                regexPattern = Pattern.compile(smsContentFilter);
            }

            // If the phone number is defined and it doesn't match, go to the next message
            if (!phone_number.equals("") && !PhoneNumberUtils.compare(message.getDisplayOriginatingAddress(), phone_number))
                continue;

            // If the content filter is not a regex and the message does not contain the content filter, go to the next message
            if (!isRegex && !smsContentFilter.equals("") && !message.getMessageBody().contains(smsContentFilter))
                continue;

            // If the content filter is a regex and the message doesn't match the regex, go to the next message
            if ((isRegex) && (!regexPattern.matcher(message.getMessageBody()).matches()))
                continue;

            // Send the POST request
            Log.i(TAG, "Starting forwarding of message from " + phone_number);
            new Thread(new HttpPostThread(target_url, message.getMessageBody())).start();
        }
    }
}
