package com.bluetoothdeviceconnectiondemo;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.manageconnection.BluetoothConnectionProcess;

public class StartTrainingActivity extends Activity {
	EditText sensorName;
	Button startTrainingBtn, stopTrainingBtn;
	public static final int ENABLE_BLUETOOTH = 1;
	private static final int BT_MESSAGE_WRITE = 3;
	public static final int BT_MESSAGE_TOAST = 2;
	public static final String TOAST = "TOAST";
	public static BluetoothConnectionProcess bluetoothConnectionProcess;
	public static Context activityContext;
	public static TrainingDetailsActivity trainingDetailsActivityInstance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_screen);
		initializeScreenComponents();
		((BTConnectionApplication)getApplication()).startTrainingActivity = this;
		activityContext = this.getApplicationContext();
	}

	private void initializeScreenComponents() {
		sensorName = (EditText) findViewById(R.id.sensorNameId);
		startTrainingBtn = (Button) findViewById(R.id.startTraining);
		stopTrainingBtn = (Button) findViewById(R.id.stopTraining);
		startTrainingBtn.setOnClickListener(btnClickListener);
		stopTrainingBtn.setOnClickListener(btnClickListener);
		bluetoothConnectionProcess = new BluetoothConnectionProcess();
	}

	private Button.OnClickListener btnClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
				case R.id.startTraining:
					startTraining();
					break;
				case R.id.stopTraining:
					stopTrainingBtn.setVisibility(View.GONE);
					startTrainingBtn.setVisibility(View.VISIBLE);
					bluetoothConnectionProcess.disconnect();
					break;
				default:
					break;
			}
		}
	};
	
	public enum Response {
		success, unsuccess, closed;
	}

	private void startTraining() {
		if (sensorName.getText().toString().trim().equals(CommonUtil.BLANK_TEXT)) {
			Toast.makeText(StartTrainingActivity.this,CommonUtil.BLANK_SENSOR_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
		} else {
			if (!sensorName.getText().toString().trim().equals(CommonUtil.BLANK_TEXT)) {
				BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				if (!mBluetoothAdapter.isEnabled()) {
					Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH);
				}
				new SensorConnectionTask().execute("Connecting With Device...");
			} else {
				Toast.makeText(getApplicationContext(),CommonUtil.BLANK_SENSOR_ERROR_MESSAGE,Toast.LENGTH_LONG).show();
			}
		}
	}

	public ProgressDialog progressDialog;

	public class SensorConnectionTask extends
			AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			if (progressDialog != null) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			progressDialog = new ProgressDialog(StartTrainingActivity.this);
			progressDialog.setMessage("Please wait...");
			progressDialog.setTitle("Connecting With Sensor...");
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			bluetoothConnectionProcess.performBTConnection(getFormattedSensorAddress(sensorName.getText().toString().trim()), mHandler);
			return "Done!";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}
	}
	
	@SuppressLint("DefaultLocale")
	public static String getFormattedSensorAddress(String address) {
		String formattedSensorAddress = "";

		for (int i = 0; i < address.length(); i++) {
			char ch = address.charAt(i);
			if (i % 2 == 0 && i != 0) {

				formattedSensorAddress = formattedSensorAddress + ":";
				formattedSensorAddress = formattedSensorAddress + ch;
			} else {
				formattedSensorAddress = formattedSensorAddress + ch;
			}
		}
		return formattedSensorAddress.toUpperCase();
	}
	
	@SuppressLint("HandlerLeak")
	public final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case BT_MESSAGE_WRITE:
				JSONObject punchDataDetailsJson = null;
				try {
					JSONObject punchDataJson = new JSONObject(msg.getData().getString("jsonData"));
					if (punchDataJson.getBoolean("success") || punchDataJson.getString("success").equals("true")) {
						punchDataDetailsJson = punchDataJson.getJSONObject("jsonObject");
						(((BTConnectionApplication)getApplication()).trainingDetailsActivity).getSpeedView().setText(punchDataDetailsJson.getString("speed")+"MPH");
						(((BTConnectionApplication)getApplication()).trainingDetailsActivity).getForceView().setText(punchDataDetailsJson.getString("force")+"LBF");
						(((BTConnectionApplication)getApplication()).trainingDetailsActivity).getPunchTypeView().setText(punchDataDetailsJson.getString("punchType"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case BT_MESSAGE_TOAST:
				try {
					Response response = Response.valueOf(msg.getData().getString("CONNECTION"));
					System.out.println("aa response:"+ response);
					handlingConnectionToastResponse(response, msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
	};
	
	private void handlingConnectionToastResponse(Response response, Message msg){
		try{
			switch(response) {
		    case success:
		    	Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
		    	startTrainingBtn.setVisibility(View.GONE);
				stopTrainingBtn.setVisibility(View.VISIBLE);
				progressDialog.dismiss();
				Intent i = new Intent(StartTrainingActivity.this, TrainingDetailsActivity.class);
				startActivity(i);
		        break;
		        
		    case unsuccess:
		    	Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
		    	progressDialog.dismiss();
		    	stopTrainingBtn.setVisibility(View.GONE);
				startTrainingBtn.setVisibility(View.VISIBLE);
		        break;
		        
		    case closed:
		    	Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
		    	progressDialog.dismiss();
		    	stopTrainingBtn.setVisibility(View.GONE);
				startTrainingBtn.setVisibility(View.VISIBLE);
		        break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
