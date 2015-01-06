package com.manageconnection;

import java.util.UUID;

import com.bluetoothdeviceconnectiondemo.CommonUtil;
import com.bluetoothdeviceconnectiondemo.StartTrainingActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class BluetoothConnectionProcess {
	private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private final BluetoothAdapter btAdapter;
	private BluetoothSocket bluetoothSocket;
	private SocketConnectionThread socketConnectionThread;
	private Handler connectionHandler;
	
	public BluetoothConnectionProcess(){
		btAdapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	@SuppressLint("NewApi")
	public synchronized void performBTConnection(String sensorId, Handler btHandler){
		try {
			connectionHandler = btHandler;
			BluetoothDevice btSensor= btAdapter.getRemoteDevice(sensorId);
			bluetoothSocket = btSensor.createInsecureRfcommSocketToServiceRecord(uuid);
			socketConnectionThread = new SocketConnectionThread(bluetoothSocket, sensorId, connectionHandler);
			socketConnectionThread.start();
		}catch(Exception e){
			System.out.println("aa connection failed...");
			setToastMessage(CommonUtil.UNABLE_TO_CONNECT_WITH_SENSOR_MESSAGE_TEXT);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method to create a deviceDataProcessingThread thread for a successful connection to a bluetooth device.
	 * 
	 * @param bluetoothSocket
	 */
	public synchronized void connected(BluetoothSocket bluetoothSocket) {

		
		
	}
	
	/**
	 * Cancels all running threads for the open connection on disconnect.
	 */
	public synchronized void disconnect()
	{
		try{
			socketConnectionThread.closeStreams();
			bluetoothSocket.close();
		}catch(Exception e){
			setToastMessage(CommonUtil.SOCKET_CONNECTION_FAIL_ERROR);
			System.out.println("aa socket closed failed.");
		}
	}
	
	/**
	 * Identifies the UI of the connection attempt failure.
	 */
	public void connectionFailed()
	{
		setToastMessage(CommonUtil.UNABLE_TO_CONNECT_WITH_SENSOR_MESSAGE_TEXT);
	}
	
	/**
	 * Identifies the UI of the connection being lost.
	 */
	public void connectionLost()
	{
		setToastMessage(CommonUtil.SENSOR_CONNECTION_LOST_MESSAGE_TEXT);
	}
	
	public void setToastMessage(String message){
		Message msg = connectionHandler.obtainMessage(StartTrainingActivity.BT_MESSAGE_TOAST);
		Bundle bundle = new Bundle();
		bundle.putString(StartTrainingActivity.TOAST, message);
		msg.setData(bundle);
		connectionHandler.sendMessage(msg);
	}
	
}
