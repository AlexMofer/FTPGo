package am.project.ftpgo.util;

import android.os.StrictMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 工具类
 * Created by Alex on 2017/12/27.
 */

public class Utils {

    private Utils() {
        //no instance
    }

    private static void bindPort(String host, int port) throws IOException {
        Socket s = new Socket();
        s.bind(new InetSocketAddress(host, port));
        s.close();
    }

    /**
     * 检查端口是否可用
     *
     * @param port 端口
     * @return 是否可用
     */
    public static boolean isPortAvailable(int port) {
        boolean available;
        StrictMode.ThreadPolicy defaultPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        try {
            bindPort("0.0.0.0", port);
            bindPort(InetAddress.getLocalHost().getHostAddress(), port);
            available = true;
        } catch (IOException e) {
            available = false;
        }
        StrictMode.setThreadPolicy(defaultPolicy);
        return available;
    }

    private String mName = "name";
    private String mPassword = "password";
    private String mHomeDirectory = "homeDirectory";
    private boolean mAdmin = true;
    private boolean mEnable = true;
    private int mIdleSec = 10;
    private boolean mHasWritePermission = true;
    private int mMaxDownloadRate = 0;
    private int mMaxUploadRate = 0;
    private int mMaxConcurrentLogin = 10;
    private int mMaxConcurrentLoginPerIP = 10;
    private boolean mAnonymous = false;

    public String getJson() {
        try {
            JSONObject json = new JSONObject();
            json.put("name", mName);
            json.put("password", mPassword);
            json.put("homeDirectory", mHomeDirectory);
            json.put("admin", mAdmin);
            json.put("enable", mEnable);
            json.put("idleSec", mIdleSec);
            json.put("hasWritePermission", mHasWritePermission);
            json.put("maxDownloadRate", mMaxDownloadRate);
            json.put("maxUploadRate", mMaxUploadRate);
            json.put("maxConcurrentLogin", mMaxConcurrentLogin);
            json.put("maxConcurrentLoginPerIP", mMaxConcurrentLoginPerIP);
            json.put("anonymous", mAnonymous);
            return json.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    public void formString(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        mName = json.getString("name");
        mPassword = json.getString("password");
        mHomeDirectory = json.getString("homeDirectory");
        mAdmin = json.getBoolean("admin");
        mEnable = json.getBoolean("enable");
        mIdleSec = json.getInt("idleSec");
        mHasWritePermission = json.getBoolean("hasWritePermission");
        mMaxDownloadRate = json.getInt("maxDownloadRate");
        mMaxUploadRate = json.getInt("maxUploadRate");
        mMaxConcurrentLogin = json.getInt("maxConcurrentLogin");
        mMaxConcurrentLoginPerIP = json.getInt("maxConcurrentLoginPerIP");
        mAnonymous = json.getBoolean("anonymous");
    }

    public static String getFTPUserJson() {
        return new Utils().getJson();
    }
}
