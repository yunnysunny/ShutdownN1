package com.feixun.shutdown;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    public static final String cmd = "input keyevent 26";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {


                    System.out.println(getLocalIPAddress());
                    String host="";
                    host=getLocalIPAddress();
                    EasyHttp.post("/v1/keyevent")
                            .baseUrl("http://"+host+":8080")
                            .upJson(createJson(26))
                            .execute(new SimpleCallBack<String>() {
                                @Override
                                public void onError(ApiException e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onSuccess(String s) {
                                    finish();
                                }
                            });
//                            .execute(object : CallBack<String>() {
//                        override fun onError(e: ApiException?) {
//                        }
//
//                        override fun onStart() {
//                            //To change body of created functions use File | Settings | File Templates.
//
//                        }
//
//                        override fun onCompleted() {
//                            //To change body of created functions use File | Settings | File Templates.
//                        }
//
//                        override fun onSuccess(t: String?) {
//                            if (sp.getBoolean("vibrate", false)) {
//                                var vibrator =
//                                        this@MainActivity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//                                vibrator.vibrate(100)
//                            }
//
//                        }
//                    })

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 1000);

    }

    private String createJson(int code) {
        JSONObject jb = new JSONObject();
        try {
            jb.put("keycode", code);
            jb.put("longclick", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jb.toString();
    }

    public static String getLocalIPAddress() throws SocketException {
        try{
            for(Enumeration en = NetworkInterface.getNetworkInterfaces();
                en.hasMoreElements();)
            {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for(Enumeration enumIpAddr = intf.getInetAddresses();
                    enumIpAddr.hasMoreElements();){
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if(!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)){
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return null;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 26) {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}