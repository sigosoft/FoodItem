package sigosoft.android.jackandtrades;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetCheck {
	 public static int WIFI = 1;
	    public static int MOBILE = 2;
	    public static int NOT_CONNECTED = 0;
	 
	 
	    public static int getConnectivityStatus(Context context) {
	        ConnectivityManager cm = (ConnectivityManager) context
	                .getSystemService(Context.CONNECTIVITY_SERVICE);
	 
	        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	        if (null != activeNetwork) {
	            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
	                return WIFI;
	 
	            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
	                return MOBILE;
	        } 
	        return NOT_CONNECTED;
	    }
	 
	    public static String getConnectivityStatusString(Context context) {
	        int conn = NetCheck.getConnectivityStatus(context);
	        String status = null;
	        if (conn == NetCheck.WIFI) {
	            status = "Wifi enabled";
	        } else if (conn == NetCheck.MOBILE) {
	            status = "Mobile data enabled";
	        } else if (conn == NetCheck.NOT_CONNECTED) {
	            status = "Not connected to Internet";
	        }
	        return status;
	    }

}
