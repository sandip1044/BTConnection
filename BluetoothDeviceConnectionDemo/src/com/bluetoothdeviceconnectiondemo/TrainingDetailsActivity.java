package com.bluetoothdeviceconnectiondemo;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TrainingDetailsActivity extends Activity{
	TextView speedView, forceView, punchTypeView;
	Button stopTrainingDetailsBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.training_details);
		//startTrainingActivityInstance.setTrainingDetailsInstance(this);
		StartTrainingActivity.trainingDetailsActivityInstance = this;
		initializeScreenComponents();
		((BTConnectionApplication)getApplication()).trainingDetailsActivity = this;
	}
	
	public TextView getSpeedView() {
		return this.speedView;
	}

	public TextView getForceView() {
		return this.forceView;
	}

	public TextView getPunchTypeView() {
		return this.punchTypeView;
	}

	/**
	 * linkToStartTrainingScreen to show force,speed and punch-type value 
	 */
	private void linkToStartTrainingScreen() {
		StartTrainingActivity.bluetoothConnectionProcess.disconnect();
		Intent i = new Intent(TrainingDetailsActivity.this, StartTrainingActivity.class);
		startActivity(i);
		finish();
	}
	
	/**
	 * initializeScreenComponents to initialize screen components
	 */
	private void initializeScreenComponents() {
		speedView = (TextView) findViewById(R.id.speed_value_id);
		forceView = (TextView) findViewById(R.id.force_value_id);
		punchTypeView = (TextView) findViewById(R.id.punch_type_value_id);
		stopTrainingDetailsBtn = (Button) findViewById(R.id.stopTrainingDetails);
		stopTrainingDetailsBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StartTrainingActivity.bluetoothConnectionProcess.disconnect();
				Toast.makeText(TrainingDetailsActivity.this,CommonUtil.SENSOR_DISCONNECTED,Toast.LENGTH_LONG).show();
				stopTrainingDetailsBtn.setEnabled(false);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		linkToStartTrainingScreen();
	}

}
