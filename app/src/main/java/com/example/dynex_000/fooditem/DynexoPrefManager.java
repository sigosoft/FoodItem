package com.example.dynex_000.fooditem;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class DynexoPrefManager {
	
	public static void setResponse(String key, String value, Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static String showSavedResponse(String key, Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(key, null);
	}

	public void saveLoginDetails(String key, Context context){
		setResponse("login_user_pass", key, context);
	}
	public static boolean getSavedLogin(Context context,String userPass){ 
		 if(showSavedResponse("login_user_pass", context)!=null){
			 if(userPass.equals(showSavedResponse("login_user_pass", context))){
				 return true;
			 }
		 }
		return false;
	}


//...............................NAME OF USER............................................................

	public void saveName(String key, Context context){
		setResponse("name", key, context);
	}
	public String getSavedName(Context context){
		return showSavedResponse("name", context);
	}




//.......................AREA OF USER......................................................................

	public void saveArea(String key, Context context){
		setResponse("area", key, context);
	}
	public String getSavedArea(Context context){
		return showSavedResponse("area", context);
	}

//.................................DISTRICT OF USER.........................................................

	public void saveDistrict(String key, Context context){
		setResponse("district", key, context);
	}
	public String getSavedDistrict(Context context){
		return showSavedResponse("district", context);
	}

//.................................STATE OF USER............................................................

	public void saveState(String key, Context context){
		setResponse("state", key, context);
	}
	public String getSavedState(Context context){
		return showSavedResponse("state", context);
	}


//............................SERVICE TYPES.................................................................


	public void saveServiceTypes(String key, Context context){
		setResponse("service_types", key, context);
	}
	public String getSavedServiceTypes(Context context){
		return showSavedResponse("service_types", context);
	}


//...........................TYPE OF VEHICLE.................................................................

	public void saveVehicleType(String key, Context context){
		setResponse("vehicle_type", key, context);
	}
	public String getSavedVehicleType(Context context){
		return showSavedResponse("vehicle_type", context);
	}


//...............................PASSWORD OF USER.............................................................
	
	public void saveUserPassword(String key, Context context){
		setResponse("password", key, context);
	}
	public String getSavedUserPassword(Context context){
		return showSavedResponse("password", context);
	}

//...............................USER NAME OF USER.............................................................
	
	public void saveUserName(String key, Context context){
		setResponse("user_name", key, context);
	}
	public String getSavedUserName(Context context){
		return showSavedResponse("user_name", context);
	}
	
//................................MOBILE NUMBER OF USER........................................................


	public void saveUserMobile(String key, Context context){
		setResponse("customer_products", key, context);
	}
	public String getSavedUserMobile(Context context){
		return showSavedResponse("customer_products", context);
	}
	


//..................................MAIL ID OF USER.................................................................

	public void saveMailId(String key, Context context){
		setResponse("customer_registration", key, context);
	}
	public String getSavedMailId(Context context){
		return showSavedResponse("customer_registration", context);
	}


//.....................................Image Details...........................................................
	
	public void saveImage(String key, Context context){
		setResponse("image_str", key, context);
	}
	public String getSavedImage(Context context){
		return showSavedResponse("image_str", context);
	}
}
