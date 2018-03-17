/**
 * @author janman74 
 */

package com.appskc.czechfortravel;

import com.google.ads.AdRequest;
import com.google.android.gms.ads.*;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	public final static String CATEGORY_NAME = "Category";

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Action bar work around old APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setTitle("Czech for Travel");
			// actionBar.setSubtitle("Full Version");
			actionBar.show();
		}

		AdView ad = (AdView) this.findViewById(R.id.adView01);
        AdRequest adRequest = new AdRequest.Builder().build();
		ad.loadAd(adRequest);

		// RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl);
		// AdView ad = new AdView(this, AdSize.BANNER,
		// "ca-app-pub-6909324749144156/2894025628");
		// rl.addView(ad);
		// ad.loadAd(new AdRequest());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// Action bar options
	@Override
	public boolean onOptionsItemSelected(MenuItem itemM) {
		switch (itemM.getItemId()) {
		case R.id.action_about:
			Intent a = new Intent("com.appskc.ABOUT");
			startActivity(a);
			break;
		case R.id.action_exit:
			exitByBackKey();
			break;
		}
		return false;
	}

	// Buttons
	public void onCategoryButtonClick(View target) {
		Intent clickedCat = new Intent(this, CategoryActivity.class);
		clickedCat.putExtra(CATEGORY_NAME, target.getContentDescription());
		startActivity(clickedCat);
	}

	// Exiting
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			SharedPreferences sharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			boolean exitConfirmation = sharedPrefs.getBoolean(
					"key_checkboxexit", true);
			if (exitConfirmation) {
				exitByBackKey();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// Exiting with confirmation
	protected void exitByBackKey() {
		// Make dialog box
		AlertDialog.Builder exitBuilder = new AlertDialog.Builder(this);
		exitBuilder.setTitle("Exit");
		exitBuilder.setMessage("Are you sure you want to quit?");
		exitBuilder.setPositiveButton("Quit",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface d, int m) {
						finish();
					}
				});
		exitBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface d, int m) {
						// Do nothing
					}
				});
		exitBuilder.show();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		setContentView(R.layout.activity_main);

		AdView ad = (AdView) findViewById(R.id.adView01);
		ad.loadAd(new AdRequest());
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}
