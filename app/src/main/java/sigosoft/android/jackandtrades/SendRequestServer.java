package sigosoft.android.jackandtrades;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class SendRequestServer {

    int timeConnct = 180000;
   // String url1 = "http://jackandtrades.com/jacktrades/";

// String url1 = "http://jackandtrades.com/jacktrades/";
//   String url1 = "http://192.168.1.102/jack/";
String url1="http://rs500andrs1000.com/jacktrades/";
//   String url1 = "http://192.168.1.102/jacktrades/";

   // String url1 = "http://dynexoit.com/calldriver/";


    public String requestSender(String requestURL, HashMap<String, String> postDataParams, Context context) {

//        try {
//            postDataParams.put("div_id",getDivId(context).trim());
//        } catch (Exception e) {
//            e.printStackTrace();
//            postDataParams.put("div_id","000000000000");
//        }

        URL url;
        String response = "";
        try {
            url = new URL(url1 +requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000);
            conn.setConnectTimeout(65000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private static String getDivId(Context context){
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }


/*		public String requestSender(String url12, List<NameValuePair> nameValuePairs,Context context) {
			HttpParams httpParameters = new BasicHttpParams();
			String message = null;
			try {
				HttpConnectionParams.setConnectionTimeout(httpParameters,timeConnct);
				HttpConnectionParams.setSoTimeout(httpParameters, timeConnct);

				HttpClient httpclient = new DefaultHttpClient(httpParameters);
				HttpPost httppost = new HttpPost(url1 + url12);
				nameValuePairs.add(new BasicNameValuePair("div_id",getDivId(context).trim()));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = null;
				try {
					//System.out.println(url1 + url12);
					response = httpclient.execute(httppost);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				InputStream instream = null;
				if (response != null) {
					HttpEntity entity = response.getEntity();
					try {
						instream = entity.getContent();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (httpclient != null)
						System.out.println("By The Name Of GOD");
					String message1 = read(instream);
					httpclient.getConnectionManager().shutdown();
					return message1;
				}
				message = read(instream);
			} catch (Exception e) {
				message = null;
			}
			return message;
		}
		private StringBuffer request(String urlString) {
		    // TODO Auto-generated method stub

		    StringBuffer chaine = new StringBuffer("");
		    try{
		        URL url = new URL(urlString);
		        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		        connection.setRequestProperty("User-Agent", "");
		        connection.setRequestMethod("POST");
		        connection.setDoInput(true);
		        connection.connect();

		        InputStream inputStream = connection.getInputStream();

		        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
		        String line = "";
		        while ((line = rd.readLine()) != null) {
		            chaine.append(line);
		        }

		    } catch (IOException e) {
		        // writing exception to log
		        e.printStackTrace();
		    }

		    return chaine;
		}

		public String read(InputStream instream) {
			StringBuilder sb = null;
			try {
				sb = new StringBuilder();
				BufferedReader r = new BufferedReader(new InputStreamReader(
						instream));
				for (String line = r.readLine(); line != null; line = r.readLine()) {
					sb.append(line);
				}
				instream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return sb.toString();

		}*/

    public String requestSenderSampleExample(String requestURL, HashMap<String, String> postDataParams, Context context) {

//        try {
//            postDataParams.put("div_id",getDivId(context).trim());
//        } catch (Exception e) {
//            e.printStackTrace();
//            postDataParams.put("div_id","000000000000");
//        }

        URL url;
        String response = "";
        String a="http://192.168.1.102/jack/historyview.php";
        try {
            url = new URL(a);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000);
            conn.setConnectTimeout(65000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }



}
