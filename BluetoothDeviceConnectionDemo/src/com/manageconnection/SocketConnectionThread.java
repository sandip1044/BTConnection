package com.manageconnection;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.bluetoothdeviceconnectiondemo.CommonUtil;
import com.bluetoothdeviceconnectiondemo.DeviceDataProcessingThread;
import com.bluetoothdeviceconnectiondemo.StartTrainingActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SocketConnectionThread extends Thread{
	private final BluetoothSocket bluetoothSocket;
	String sensorId;
	private Handler connectionHandler;
	OutputStream outputStream ;
	InputStream inputStream ;
	DataInputStream dataInputStream;
	BlockingQueue<Integer []> sensorDatablockingQueue;
	DeviceDataProcessingThread deviceDataProcessingThread;
	private Thread dataProcessingThread;
	public SocketConnectionThread(BluetoothSocket bluetoothSocket, String sensorId, Handler connectionHandler)
	{
		System.out.println("aa in SocketConnectionThread");
		this.bluetoothSocket = bluetoothSocket;
		this.sensorId = sensorId;
		this.connectionHandler = connectionHandler;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			outputStream = bluetoothSocket.getOutputStream();
			inputStream = bluetoothSocket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.outputStream = outputStream;
		this.inputStream = inputStream;
		dataInputStream = new DataInputStream(inputStream);
		sensorDatablockingQueue= new LinkedBlockingQueue<Integer[]>();
		
		deviceDataProcessingThread = new DeviceDataProcessingThread(sensorDatablockingQueue, connectionHandler);
		dataProcessingThread = new Thread(this.deviceDataProcessingThread);
	}
	@Override
	public void run() {
		try{
			bluetoothSocket.connect();
			if(bluetoothSocket!=null)
			{
		        sendConnectionStatus(CommonUtil.SENSOR_CONNECTION_ESTABLISHED_MESSAGE+ sensorId, "success");
		        try {
					openStream();
				} catch (Exception e) {
					e.printStackTrace();
				}
				//int counter=0;
				dataProcessingThread.start();
				byte[] packetByte=new byte[104];
				while(true)
				{
					try {
						if(dataInputStream!=null)
						{
							dataInputStream.read(packetByte, 0, packetByte.length);
							unsignedToBytes(packetByte, packetByte.length);
						}

					} catch (Exception e) {
						sendConnectionStatus("connection closed","closed");
						StartTrainingActivity.bluetoothConnectionProcess.disconnect();
						break;
					}
				}
			}
			
		}catch(IOException e)
		{
	        sendConnectionStatus(CommonUtil.SENSOR_CONNECTION_FAILED_MESSAGE+ sensorId, "unsuccess");
			StartTrainingActivity.bluetoothConnectionProcess.disconnect();
			return;
		}catch(Exception ex){
			ex.printStackTrace();
	        sendConnectionStatus(CommonUtil.BLUETOOTH_SOCKET_CONNECTION_FAILED_MESSAGE, "unsuccess");
	        StartTrainingActivity.bluetoothConnectionProcess.disconnect();
			return;
		}
		
	}
	
	private void unsignedToBytes(byte [] packetByte,int noOfBytes) throws InterruptedException {
		Integer [] intArray=new Integer[noOfBytes];
		for(int i=0;i<noOfBytes;i++)
		{
			intArray[i]=(packetByte[i] & 0xFF);
		}
		sensorDatablockingQueue.put(intArray);
	}
	
	private void sendConnectionStatus(String message, String responseMsg){
		Message msg = connectionHandler.obtainMessage(StartTrainingActivity.BT_MESSAGE_TOAST);
		Bundle bundle = new Bundle();
        bundle.putString("TOAST", message);
        bundle.putString("DeviceAddress", sensorId);
        if(responseMsg.equalsIgnoreCase("success"))
        	bundle.putString("CONNECTION", "success");
        if(responseMsg.equalsIgnoreCase("unsuccess"))
        	bundle.putString("CONNECTION", "unsuccess");
        msg.setData(bundle);
        connectionHandler.sendMessage(msg);
	}
	
	private void openStream() {
		try {
			outputStream.write(170);
			outputStream.write(7);
			outputStream.write(5);
			outputStream.write(0);
			outputStream.write(1);
			
			outputStream.write(170);
			outputStream.write(1);
			outputStream.write(5);
			outputStream.write(0);
			outputStream.write(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeStreams()
	{
		synchronized (this) {
			try {
				if(this.inputStream!=null){
					this.inputStream.close();
				}
				if(this.outputStream!=null){
					this.outputStream.close();
				}
				if(this.dataInputStream!=null){
					this.dataInputStream.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
