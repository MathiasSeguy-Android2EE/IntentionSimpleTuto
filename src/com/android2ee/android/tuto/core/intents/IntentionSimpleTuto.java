/**
* <ul>
* Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
* Produced by <strong>Dr. Mathias SEGUY</strong> with the smart contribution of <strong>Julien PAPUT</strong>.</br>
* Delivered by <strong>http://android2ee.com/</strong></br>
* Belongs to <strong>Mathias Seguy</strong></br>
* ****************************************************************************************************************</br>
* This code is free for any usage but can't be distribute.</br>
* The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
* The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
* <em>http://mathias-seguy.developpez.com/</em></br>
* </br>
* For any information (Advice, Expertise, J2EE or Android Training, Rates, Business):</br>
* <em>mathias.seguy.it@gmail.com</em></br>
* *****************************************************************************************************************</br>
* Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
* Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
* Sa propriété intellectuelle appartient à <strong>Mathias Séguy</strong>.</br>
* <em>http://mathias-seguy.developpez.com/</em></br>
* </br>
* Pour tous renseignements (Conseil, Expertise, Formations J2EE ou Android, Prestations, Forfaits):</br>
* <em>mathias.seguy.it@gmail.com</em></br>
* *****************************************************************************************************************</br>
* Merci à vous d'avoir confiance en Android2EE les Ebooks de programmation Android.
* N'hésitez pas à nous suivre sur twitter: http://fr.twitter.com/#!/android2ee
* N'hésitez pas à suivre le blog Android2ee sur Developpez.com : http://blog.developpez.com/android2ee-mathias-seguy/
* *****************************************************************************************************************</br>
* com.android2ee.android.tuto</br>
* 25 mars 2011</br>
*/
package com.android2ee.android.tuto.core.intents;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class IntentionSimpleTuto extends Activity {
	/**
	 * The unique identifier for the call using Intent of contact
	 */
	private final int INTENT_CALL_ID = 10012220;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//To launch your own activity
		//First declare an intent
		final Intent intent = new Intent().setClass(this, OtherActivity.class);
		// Then add the action to the button:
		Button btnOtherActivity = (Button) findViewById(R.id.launchOther);
		btnOtherActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Instanciate the DatePicker using this as context, the dateListener
				// defined above and the current time
				startActivity(intent);
			}
		});
		// Do the same for Geo*
		// Retrieve latitude and longitude
		String latitude = "43.565715431592736";
		String longitude = "1.398482322692871";
		// Format the associated uri
		Uri uriGeo = Uri.parse("geo:" + latitude + "," + longitude);
		// Declare the associated Intent
		final Intent intentGeo = new Intent(Intent.ACTION_VIEW, uriGeo);
		// instanciate the button
		Button btnGeoActivity = (Button) findViewById(R.id.launchGeo);
		btnGeoActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Instanciate the DatePicker using this as context, the dateListener
				// defined above and the current time
				startActivity(intentGeo);
			}
		});
		// Do the same for Contact waiting for result
		// Format the associated uri
		Uri uriContact = Uri.parse("content://contacts/people");//"content://contacts/people"
		// Declare the intent:
		final Intent intentContactForResult = new Intent(Intent.ACTION_PICK, uriContact);
		// instanciate the button
		Button btnContactActivityForResults = (Button) findViewById(R.id.launchContactForResult);
		btnContactActivityForResults.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Instanciate the DatePicker using this as context, the dateListener
				// defined above and the current time
				startActivityForResult(intentContactForResult, INTENT_CALL_ID);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		StringBuilder strB=new StringBuilder();
		if (requestCode == INTENT_CALL_ID) {
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				strB.append("Result ok (uri:" + contactData + ") data " + contactData.toString());
				Cursor c = managedQuery(contactData, null, null, null, null);
				StringBuffer columnName = new StringBuffer("Result : ");
				String value;
				String name="...";
				if (c.moveToFirst()) {
					for (int i = 0; i < c.getColumnCount(); i++) {
						value = c.getString(c.getColumnIndexOrThrow(c.getColumnName(i)));
						columnName.append(",\r\n" + c.getColumnName(i) + ": " + value);
					}
					name = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
					strB.append("\r\ncolumnName : "+columnName+", name: "+name);
				}

			} else if (resultCode == Activity.RESULT_CANCELED) {
				strB.append("Result ko no contact picked");
			}
		}
		Log.d("IntentionSimpleTuto", strB.toString());
	}

}