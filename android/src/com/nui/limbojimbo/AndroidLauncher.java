package com.nui.limbojimbo;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nui.limbojimbo.LimboScreen;
import com.nui.limbojimbo.GdxSplashScreenGame;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class AndroidLauncher extends AndroidApplication {

	private final static int WRITE_EXTERNAL_RESULT = 105;



	private SharedPreferences sharedPreferences;
	private Button btnStorageWrite;
	private FrameLayout permissionSuccess;

	private ArrayList<String> permissionsToRequest;
	private ArrayList<String> permissionsRejected;
	private View coordinatorLayoutView;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
		//permissionSuccess.setVisibility(View.GONE);
		ArrayList<String> permissions = new ArrayList<>();
		int resultCode = 0;
		permissions.add(WRITE_EXTERNAL_STORAGE);
		resultCode = WRITE_EXTERNAL_RESULT;


		//filter out the permissions we have already accepted
		permissionsToRequest = findUnAskedPermissions(permissions);
		//get the permissions we have asked for before but are not granted..
		//we will store this in a global list to access later.
		permissionsRejected = findRejectedPermissions(permissions);

		if(permissionsToRequest.size()>0){//we need to ask for permissions
			//but have we already asked for them?
			requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), resultCode);
			//mark all these as asked..
			for(String perm : permissionsToRequest){
				markAsAsked(perm);
			}
		}else{
			//show the success banner
			if(permissionsRejected.size()<permissions.size()){
				//this means we can show success because some were already accepted.
				//permissionSuccess.setVisibility(View.VISIBLE);
			}

			if(permissionsRejected.size()>0){
				//we have none to request but some previously rejected..tell the user.
				//It may be better to show a dialog here in a prod application
				Snackbar
						.make(coordinatorLayoutView, String.valueOf(permissionsRejected.size()) + " permission(s) were previously rejected", Snackbar.LENGTH_LONG)
						.setAction("Allow to Ask Again", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								for(String perm: permissionsRejected){
									clearMarkAsAsked(perm);
								}
							}
						})
						.show();
			}
		}

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new GdxSplashScreenGame(), config);

		//initialize( new GdxSplashScreenGame(), config);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

		if(hasPermission(WRITE_EXTERNAL_STORAGE)){
			//permissionSuccess.setVisibility(View.VISIBLE);
		}else{
			permissionsRejected.add(WRITE_EXTERNAL_STORAGE);
			makePostRequestSnack();
		}

	}

	/**
	 * a method that will centralize the showing of a snackbar
	 */
	private void makePostRequestSnack(){
		Snackbar
				.make(coordinatorLayoutView, String.valueOf(permissionsRejected.size()) + " permission(s) were rejected", Snackbar.LENGTH_LONG)
				.setAction("Allow to Ask Again", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						for(String perm: permissionsRejected){
							clearMarkAsAsked(perm);
						}
					}
				})
				.show();
	}

	/**
	 * method that will return whether the permission is accepted. By default it is true if the user is using a device below
	 * version 23
	 * @param permission
	 * @return
	 */
	private boolean hasPermission(String permission) {
		if (canMakeSmores()) {
			return(checkSelfPermission(permission)== PackageManager.PERMISSION_GRANTED);
		}
		return true;
	}

	/**
	 * method to determine whether we have asked
	 * for this permission before.. if we have, we do not want to ask again.
	 * They either rejected us or later removed the permission.
	 * @param permission
	 * @return
	 */
	private boolean shouldWeAsk(String permission) {
		return(sharedPreferences.getBoolean(permission, true));
	}

	/**
	 * we will save that we have already asked the user
	 * @param permission
	 */
	private void markAsAsked(String permission) {
		sharedPreferences.edit().putBoolean(permission, false).apply();
	}

	/**
	 * We may want to ask the user again at their request.. Let's clear the
	 * marked as seen preference for that permission.
	 * @param permission
	 */
	private void clearMarkAsAsked(String permission) {
		sharedPreferences.edit().putBoolean(permission, true).apply();
	}


	/**
	 * This method is used to determine the permissions we do not have accepted yet and ones that we have not already
	 * bugged the user about.  This comes in handle when you are asking for multiple permissions at once.
	 * @param wanted
	 * @return
	 */
	private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
		ArrayList<String> result = new ArrayList<String>();

		for (String perm : wanted) {
			if (!hasPermission(perm) && shouldWeAsk(perm)) {
				result.add(perm);
			}
		}

		return result;
	}

	/**
	 * this will return us all the permissions we have previously asked for but
	 * currently do not have permission to use. This may be because they declined us
	 * or later revoked our permission. This becomes useful when you want to tell the user
	 * what permissions they declined and why they cannot use a feature.
	 * @param wanted
	 * @return
	 */
	private ArrayList<String> findRejectedPermissions(ArrayList<String> wanted) {
		ArrayList<String> result = new ArrayList<String>();

		for (String perm : wanted) {
			if (!hasPermission(perm) && !shouldWeAsk(perm)) {
				result.add(perm);
			}
		}

		return result;
	}

	/**
	 * Just a check to see if we have marshmallows (version 23)
	 * @return
	 */
	private boolean canMakeSmores() {
		return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);
	}
}
