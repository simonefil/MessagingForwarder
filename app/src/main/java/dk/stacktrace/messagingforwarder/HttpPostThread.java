package dk.stacktrace.messagingforwarder;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostThread implements Runnable {
    private static final String TAG = HttpPostThread.class.getName();

    public HttpPostThread(URL url, String message) {
        this.url = url;
        this.message = message;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)this.url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");

            byte bytes[] = this.message.getBytes("UTF-8");
            OutputStream out = connection.getOutputStream();
            out.write(bytes);
            out.flush();
            int status = connection.getResponseCode();
            Log.i(TAG, "Server replied with HTTP status: " + status);
            out.close();
        }
        catch (IOException e) {
            Log.w(TAG, "Error communicating with HTTP server", e);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    private URL url;
    private String message;
}
