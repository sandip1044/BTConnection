package com.bluetoothdeviceconnectiondemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.detection.algorithms.LowGPunchDetection;
import com.detection.algorithms.PunchVFACalculatorOnYYAxis;
import com.detection.algorithms.PunchVFACalculatorOnYZAxis;
import com.readerBean.PeakPunchValueDetector;
import com.readerBean.PunchDetectedMap;
import com.readerBean.PunchDetectionConfig;
import com.readerBean.PunchDetector;
import com.readerBean.PunchVFAData;
import com.readerBean.SensorData;
import com.readerBean.VFA;
import com.readerBean.VFAMap;



public class DeviceDataProcessingThread extends Observable implements Runnable { //Changes by #1014

	private static final String TAG = "DeviceDataProcessingThread";

	String boxerName = "";
	boolean shouldStop = false;
	Handler mhandler;
	File file;
	BlockingQueue<Integer[]> senserDatablockingQueue;
	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MMM_dd_HH_mm_ss_SSS");

	//public DBAdapter db ;				//	TODO changes by #1037 for Local Storage

	public DeviceDataProcessingThread(BlockingQueue<Integer []> senserDatablockingQueue, Handler mHandler) {
		System.out.println("aa in DeviceDataProcessingThread");
		try {
			synchronized (this) {
				this.senserDatablockingQueue = senserDatablockingQueue;
				this.mhandler = mHandler;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			readDataFromGloveDevice();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private ArrayList<Integer> readPacketFromQueue(){
		Integer []packet=null;
		ArrayList<Integer> arrayList = null;
		try {
			if (senserDatablockingQueue != null && !this.shouldStop) {
				packet = this.senserDatablockingQueue.take();
				arrayList = new ArrayList<Integer>(Arrays.asList(packet));
			}
		} catch (Exception e) {
			

		}
		return arrayList;
	}

	private int readDeviceDataByte(ArrayList<Integer> packetData) {
		int data = -1;
		try {
			if (packetData != null && !this.shouldStop && packetData.size()>0) {
				data = packetData.remove(0);
			}
		} catch (Exception e) {
		}
		return data;
	}

	private int readWordBE(ArrayList<Integer> packetData) {

		int dataLsb, dataMsb;
		short data = 0;
		try {

			dataLsb = readDeviceDataByte(packetData);
			dataMsb = readDeviceDataByte(packetData);
			data = (short) ((dataMsb << 8) | (short) dataLsb);

		} catch (Exception ioe) {
		}
		return data;
	}

	/**
	 * Method reading lowG & highG data from new chip
	 * @throws InterruptedException
	 */
	private void readDataFromGloveDevice() throws InterruptedException {
		try {
			Log.i(TAG, "readDataFromGloveDevice");
			PeakPunchValueDetector peakPunchValueDetector = new PeakPunchValueDetector();
			SensorData[] sensorData = new SensorData[CommonUtil.SAMPLE_PACKET_SIZE];
			PunchDetector punchDetector = new PunchDetector();
			LowGPunchDetection lowGPunchDetetction = new LowGPunchDetection();

			PunchVFACalculatorOnYYAxis punchVFACalculatorOnYYAxis = new PunchVFACalculatorOnYYAxis();
			PunchVFACalculatorOnYZAxis punchVFACalculatorOnYZAxis = new PunchVFACalculatorOnYZAxis();
			PunchVFAData punchVFAData = new PunchVFAData();
			int startByte = 0;
			boolean isContinue = true, isValidStartByte = false, isValidMode = false;
			StringTokenizer stringTokenizer = null;
			
			String line = null;
			boolean csvDataReadingFinished = false;
			BufferedReader bufRdr = null;
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MMM_dd_HH_mm_ss");
			String dateNow = formatter.format(currentDate.getTime());

			while (isContinue) {
				
				ArrayList<Integer> packet=readPacketFromQueue();
				startByte = readDeviceDataByte(packet);
				isContinue = (startByte != -1) && (!this.shouldStop);

				if (!isContinue) {
					break;
				}

				isValidStartByte = (CommonUtil.START_BYTE == startByte);
				if (isValidStartByte) {
					int messageId = 0;
					messageId = readDeviceDataByte(packet);
					String streamMode = (messageId == CommonUtil.LOW_G_MODE) ? "LowG" : "HighG";

					isValidMode = (CommonUtil.LOW_G_MODE == messageId || CommonUtil.HIGH_G_MODE == messageId);

					if (isValidMode) {
						int time = 0;
						int messageLengthLsb = readDeviceDataByte(packet);
						int messageLengthMsb = readDeviceDataByte(packet);
						// Combined time0-time3 for calculating
						// time(milliseconds)
						if (messageLengthLsb == CommonUtil.MESSAGE_LENGTH_LSB) {
							int time0 = readDeviceDataByte(packet);
							int time1 = readDeviceDataByte(packet);
							int time2 = readDeviceDataByte(packet);
							int time3 = readDeviceDataByte(packet);
							time = ((((((time3 << 8) | time2) << 8) | time1) << 8) | time0);

							if (messageId == CommonUtil.LOW_G_MODE) {
//								Log.i(TAG, "Low G mode Time="+formatter.format(currentDate.getTime()));
								for (int i = 0; i < CommonUtil.SAMPLE_PACKET_SIZE; i++) {
									sensorData[i] = new SensorData();
									sensorData[i].setHand("");
									sensorData[i].setPrevTime(sensorData[i].getMsgTime());
									sensorData[i].setMsgTime(time + (i * CommonUtil.LOWG_SAMPLE_TIME_DIFFERENCE));
									short ax = 0, ay = 0, az = 0;
									try {
										ax = (short) (((short) readWordBE(packet) >> 4) * 12);
										ay = (short) (((short) readWordBE(packet) >> 4) * 12);
										az = (short) (((short) readWordBE(packet) >> 4) * 12);
									} catch (Exception e) {
									}
									sensorData[i].setAx(ax);
									sensorData[i].setAy(ay);
									sensorData[i].setAz(az);
									punchVFAData.setHand(sensorData[i].getHand());

									processLowGData(sensorData[i], punchDetector, lowGPunchDetetction, punchVFACalculatorOnYYAxis,
											punchVFACalculatorOnYZAxis, punchVFAData, (time+(i*CommonUtil.LOWG_SAMPLE_TIME_DIFFERENCE)),peakPunchValueDetector);

								}
							}
							if (messageId == CommonUtil.HIGH_G_MODE) {
//								Log.i(TAG, "high G mode time="+formatter.format(currentDate.getTime()));
								for (int i = 0; i < CommonUtil.SAMPLE_PACKET_SIZE; i++) {
									sensorData[i] = new SensorData();
									sensorData[i].setHand("");
									punchVFAData.setHand(sensorData[i].getHand());
									sensorData[i].setPrevTime(sensorData[i].getMsgTime());
									sensorData[i].setMsgTime(time + i);
									try {
										sensorData[i].setAx((short) readWordBE(packet));
										sensorData[i].setAy((short) readWordBE(packet));
										sensorData[i].setAz((short) readWordBE(packet));
									} catch (Exception e) {
									}

									processHighGData(peakPunchValueDetector, sensorData[i], punchVFACalculatorOnYYAxis, punchVFACalculatorOnYZAxis,
											punchVFAData, (time+i),lowGPunchDetetction,punchDetector);
								}

							}
						}

					}
					//continue;
				} 
				
			}
			
		}
		catch (IOException e) {
			if (!this.shouldStop) {
				System.err.println("GloveMessageReader exting due to IOException");
				System.err.println("IOException in " + getClass().getCanonicalName() + ": ");
				e.printStackTrace(System.err);
			}
		}
		this.shouldStop = true;
	}

	protected String getCommonLoggerPattern(){
		return "";
	}
	
	/**
	 * Method for processing lowG data from device.
	 * @param sensorData
	 * @param punchDetector
	 * @param lowGPunchDetetction
	 * @param punchVFACalculatorOnYYAxis
	 * @param punchVFACalculatorOnYZAxis
	 * @param punchVFAData
	 * @param time
	 * @param i
	 * @param sensorDataObj
	 * @param peakPunchValueDetector
	 * @throws IOException
	 */
	private void processLowGData(SensorData sensorData, PunchDetector punchDetector, LowGPunchDetection lowGPunchDetetction,
			PunchVFACalculatorOnYYAxis punchVFACalculatorOnYYAxis, PunchVFACalculatorOnYZAxis punchVFACalculatorOnYZAxis, PunchVFAData punchVFAData,
		double time, PeakPunchValueDetector peakPunchValueDetector) throws IOException {


		lowGPunchDetetction.detectPunchType(sensorData, punchDetector, CommonUtil.TRADITIONAL_TEXT, getCommonLoggerPattern());

		if (punchDetector.isJab()) {
			punchVFAData.setJabFound(punchDetector.isJab());
			punchVFAData.setPunchFound(punchDetector.isJab());
		}else if (punchDetector.isStraight()) {
			punchVFAData.setStraightFound(punchDetector.isStraight());
			punchVFAData.setPunchFound(punchDetector.isStraight());
		}else if (punchDetector.isHook()) {
			punchVFAData.setHookFound(punchDetector.isHook());
			punchVFAData.setPunchFound(punchDetector.isHook());

		}else if (punchDetector.isUpperCut()) {
			punchVFAData.setUpperCutFound(punchDetector.isUpperCut());
			punchVFAData.setPunchFound(punchDetector.isUpperCut());
		}else if (punchDetector.isUnrecognized()) {
			punchVFAData.setUnrecognizedPunch(punchDetector.isUnrecognized());
			punchVFAData.setPunchFound(punchDetector.isUnrecognized());
		}
		if(punchVFAData.isPunchFound() && !lowGPunchDetetction.getPunchDetectedMap().containsKey(punchDetector.getPunchDetectedTime())){
			lowGPunchDetetction.getPunchDetectedMap().put(punchDetector.getPunchDetectedTime(),punchVFAData );
		}
	}
	
	/**
	 * Method for processing highG data
	 * 
	 * @param peakPunchValueDetector
	 * @param sensorData
	 * @param punchVFACalculatorOnYYAxis
	 * @param punchVFACalculatorOnYZAxis
	 * @param punchVFAData
	 * @param webserviceData
	 * @param time
	 * @param i
	 * @throws IOException
	 */
	private void processHighGData(PeakPunchValueDetector peakPunchValueDetector, SensorData sensorData, PunchVFACalculatorOnYYAxis punchVFACalculatorOnYYAxis,
			PunchVFACalculatorOnYZAxis punchVFACalculatorOnYZAxis, PunchVFAData punchVFAData, double time,
			LowGPunchDetection lowGPunchDetetction, PunchDetector punchDetector) throws IOException {

		punchVFACalculatorOnYYAxis.estimateVelocity(sensorData, getCommonLoggerPattern());
		punchVFACalculatorOnYZAxis.estimateVelocity(sensorData, getCommonLoggerPattern());

		PunchDetectionConfig punchDetectionConfig = PunchDetectionConfig.getInstance();
		double punchTime = getNearestLowGPunchTime(lowGPunchDetetction, (time-punchDetectionConfig.getVfaBufferSize()));
		if(punchTime > 0) {
			// For yy Axis............
			VFAMap vfaMapOnYY =null;
			try {
				vfaMapOnYY =new VFAMap();
				vfaMapOnYY.putAll(punchVFACalculatorOnYYAxis.getVfaMap().subMap((punchTime-punchDetectionConfig.getVfaBufferSize()), true, (punchTime+punchDetectionConfig.getVfaBufferSize()), true));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(vfaMapOnYY!=null && vfaMapOnYY.size()>0){
				punchVFAData.setVelocityFoundOnYYAxis(true);
				VFA vfaOnYY=vfaMapOnYY.getMaxValueOfVFA();
				punchVFAData.setVelocityOnYYAxis(vfaOnYY.getVxyzMPH());
				punchVFAData.setForceOnYYAxis(vfaOnYY.getForceLSB());
				punchVFAData.setAccOnYYAxis(vfaOnYY.getAxyz());
				punchVFAData.setVelocity(vfaOnYY.getVxyzMPH());
				punchVFAData.setForce(vfaOnYY.getForceLSB());
			}
			//For yz Axis............
			VFAMap vfaMapOnYZ = null;
			try {
				vfaMapOnYZ =new VFAMap();
				vfaMapOnYZ.putAll(punchVFACalculatorOnYZAxis.getVfaMap().subMap((punchTime-punchDetectionConfig.getVfaBufferSize()), true, (punchTime+punchDetectionConfig.getVfaBufferSize()), true));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(vfaMapOnYZ!=null && vfaMapOnYZ.size()>0){
				punchVFAData.setVelocityFoundOnYZAxis(true);
				VFA vfaOnYZ=vfaMapOnYZ.getMaxValueOfVFA();
				punchVFAData.setVelocityOnYZAxis(vfaOnYZ.getVxyzMPH());
				punchVFAData.setForceOnYZAxis(vfaOnYZ.getForceLSB());
				punchVFAData.setAccOnYZAxis(vfaOnYZ.getAxyz());
				punchVFAData.setVelocity(vfaOnYZ.getVxyzMPH());
				punchVFAData.setForce(vfaOnYZ.getForceLSB());
			}
			if(punchVFAData.isVelocityFoundOnYYAxis() || punchVFAData.isVelocityFoundOnYZAxis()){
				sendHighGAndLowGProcessedData(punchDetector, punchVFACalculatorOnYYAxis, punchVFACalculatorOnYZAxis,
						punchVFAData,sensorData,peakPunchValueDetector,lowGPunchDetetction.getPunchDetectedMap(),punchTime);
			}
		}
	}
	
	/**
	 * Method set velocity & force depending on Y/Z axis in bean according to punch type 
	 * 
	 * @param punchVFAData
	 * @param isSendMatchData
	 * @return
	 * @throws IOException
	 */
	private boolean isValidPunchFound(PunchVFAData punchVFAData,boolean isSendMatchData) throws IOException {
		if ((punchVFAData.isJabFound() || punchVFAData.isStraightFound()) && punchVFAData.isVelocityFoundOnYYAxis()) {
			punchVFAData.setVelocity(punchVFAData.getVelocityOnYYAxis());
			punchVFAData.setForce(punchVFAData.getForceOnYYAxis());
			isSendMatchData = true;
		}else if(punchVFAData.isHookFound() && (punchVFAData.isVelocityFoundOnYYAxis() || punchVFAData.isVelocityFoundOnYZAxis())){
			if (punchVFAData.getVelocityOnYZAxis() != 0) {
				punchVFAData.setVelocity(punchVFAData.getVelocityOnYZAxis());
			}else{
				punchVFAData.setVelocity(punchVFAData.getVelocityOnYYAxis());
			}
			if (punchVFAData.getForceOnYZAxis() != 0) {
				punchVFAData.setForce(punchVFAData.getForceOnYZAxis());
			}else{
				punchVFAData.setForce(punchVFAData.getForceOnYYAxis());
			}
			isSendMatchData = true;
		}else if (punchVFAData.isUpperCutFound() && punchVFAData.isVelocityFoundOnYYAxis()) {
			punchVFAData.setVelocity(punchVFAData.getVelocityOnYYAxis());
			punchVFAData.setForce(punchVFAData.getForceOnYYAxis());
			isSendMatchData = true;
		}else if(punchVFAData.isUnrecognizedPunch()  && (punchVFAData.isVelocityFoundOnYYAxis() || punchVFAData.isVelocityFoundOnYZAxis())){
			if (punchVFAData.getVelocityOnYYAxis() != 0) {
				punchVFAData.setVelocity(punchVFAData.getVelocityOnYYAxis());
			}else{
				punchVFAData.setVelocity(punchVFAData.getVelocityOnYZAxis());
			}
			if (punchVFAData.getForceOnYYAxis() != 0) {
				punchVFAData.setForce(punchVFAData.getForceOnYYAxis());
			}else{
				punchVFAData.setForce(punchVFAData.getForceOnYZAxis());
			}
			isSendMatchData = true;
		}
		
		return isSendMatchData;
	}
	
	
	/**
	 * Check for :- punch type get detected on LowG & Velocity get detected on HighG. If punch get detected then send it to server otherwise ignore it.
	 * @param punchDetector
	 * @param punchVFACalculatorOnYYAxis
	 * @param punchVFACalculatorOnYZAxis
	 * @param punchVFAData
	 * @param webserviceData
	 * @param isSendMatchData
	 * @param sensorDataObj
	 * @param peakPunchValueDetector
	 * @return
	 * @throws IOException
	 */
	private void sendHighGAndLowGProcessedData(PunchDetector punchDetector, PunchVFACalculatorOnYYAxis punchVFACalculatorOnYYAxis,
			PunchVFACalculatorOnYZAxis punchVFACalculatorOnYZAxis, PunchVFAData punchVFAData,  
			SensorData sensorDataObj,PeakPunchValueDetector peakPunchValueDetector,PunchDetectedMap  punchDetectedMap, double punchTime)
			throws IOException {
		boolean isSendMatchData=false;
		if (punchVFAData.isPunchFound()) {
			//Reset data when slap punch is detected
			if((punchVFACalculatorOnYYAxis.isSlapPunch() || punchVFACalculatorOnYZAxis.isSlapPunch())){
				resetAllData(punchDetector, punchVFACalculatorOnYYAxis,punchVFACalculatorOnYZAxis, punchVFAData,sensorDataObj,punchDetectedMap, punchTime);
				return;
			}
			isSendMatchData = isValidPunchFound(punchVFAData,isSendMatchData);
			if(isSendMatchData){
				if(punchVFAData.getVelocity()>CommonUtil.MAX_VELOCITY || punchVFAData.getVelocity()<CommonUtil.MIN_VELOCITY || punchVFAData.getForce()>CommonUtil.MAX_FORCE || punchVFAData.getForce()<CommonUtil.MIN_FORCE){
					Log.i(TAG, getCommonLoggerPattern()+"Not sending Data to Arena System .... =====> velocity = " + punchVFAData.getVelocity() + " force = "
							+ punchVFAData.getForce());
				}else{
					if (punchVFAData.isJabFound() && punchVFAData.isVelocityFoundOnYYAxis()) {
						punchVFAData.setJabVelocity(punchVFAData.getVelocity());
						punchVFAData.setJabForce(punchVFAData.getForce());
						punchVFAData.setJabAcc(punchVFAData.getAccOnYYAxis());
						if(punchVFAData.getHand().equalsIgnoreCase(CommonUtil.LEFT_HAND)){
							punchVFAData.setPunchType(CommonUtil.JAB);
						}else{
							punchVFAData.setPunchType(CommonUtil.JAB);
						}
					} else if (punchVFAData.isStraightFound() && punchVFAData.isVelocityFoundOnYYAxis()) {
						punchVFAData.setStraightVelocity(punchVFAData.getVelocity());
						punchVFAData.setStraightForce(punchVFAData.getForce());
						punchVFAData.setStraightAcc(punchVFAData.getAccOnYYAxis());
						if(punchVFAData.getHand().equalsIgnoreCase(CommonUtil.LEFT_HAND)){
							punchVFAData.setPunchType(CommonUtil.STRAIGHT);
						}else{
							punchVFAData.setPunchType(CommonUtil.STRAIGHT);
						}
					} else if (punchVFAData.isHookFound() && (punchVFAData.isVelocityFoundOnYYAxis() || punchVFAData.isVelocityFoundOnYZAxis())) {
						punchVFAData.setHookVelocity(punchVFAData.getVelocity());
						punchVFAData.setHookForce(punchVFAData.getForce());
						if (punchVFAData.getAccOnYZAxis() != 0) {
							punchVFAData.setHookAcc(punchVFAData.getAccOnYZAxis());
						}else{
							punchVFAData.setHookAcc(punchVFAData.getAccOnYYAxis());
						}
						if(punchVFAData.getHand().equalsIgnoreCase(CommonUtil.LEFT_HAND)){
							punchVFAData.setPunchType(CommonUtil.HOOK);
						}else{
							punchVFAData.setPunchType(CommonUtil.HOOK);
						}
					} else if (punchVFAData.isUpperCutFound() && punchVFAData.isVelocityFoundOnYYAxis()) {
						punchVFAData.setUpperCutVelocity(punchVFAData.getVelocity());
						punchVFAData.setUpperCutForce(punchVFAData.getForce());
						punchVFAData.setUpperCutAcc(punchVFAData.getAccOnYYAxis());
						if(punchVFAData.getHand().equalsIgnoreCase(CommonUtil.LEFT_HAND)){
							punchVFAData.setPunchType(CommonUtil.UPPERCUT);		//delete it when left upper-cut punch shown
						}else{
							punchVFAData.setPunchType(CommonUtil.UPPERCUT);			//delete it when left upper-cut punch shown
						}

					} else if (punchVFAData.isUnrecognizedPunch() && (punchVFAData.isVelocityFoundOnYYAxis() || punchVFAData.isVelocityFoundOnYZAxis())) {
						punchVFAData.setUnrecognizedVelocity(punchVFAData.getVelocity());
						punchVFAData.setUnrecognizedForce(punchVFAData.getForce());
						if (punchVFAData.getAccOnYYAxis() != 0) {
							punchVFAData.setUnrecognizedAcc(punchVFAData.getAccOnYYAxis());
						}else if(punchVFAData.getAccOnYZAxis() != 0){
							punchVFAData.setUnrecognizedAcc(punchVFAData.getAccOnYZAxis());
						}
						if(punchVFAData.getHand().equalsIgnoreCase(CommonUtil.LEFT_HAND)){
							punchVFAData.setPunchType(CommonUtil.UNRECOGNIZED);
						}else{
							punchVFAData.setPunchType(CommonUtil.UNRECOGNIZED);
						}
					} 
					Log.i(TAG, getCommonLoggerPattern()+"Arena System sending.... =====> velocity = " + punchVFAData.getVelocity() + " force = "
							+ punchVFAData.getForce()+"Punch Type="+punchVFAData.getPunchType());
					
					savePunchData(punchVFAData, 1 );
					saveMatchDataDetails(punchVFAData, sensorDataObj, 1);
					savePunchPeakSummary(punchVFAData, peakPunchValueDetector, 1);
					sendMatchData(punchVFAData);
					this.setChanged();
					this.notifyObservers("Punch Detected...");
				}
				isSendMatchData = false;
				resetAllData(punchDetector, punchVFACalculatorOnYYAxis,punchVFACalculatorOnYZAxis, punchVFAData,sensorDataObj,punchDetectedMap, punchTime);
			}
		}
		return ;
	}

	/**
	 * @param punchDetector
	 * @param punchVFACalculatorOnYYAxis
	 * @param punchVFACalculatorOnYZAxis
	 * @param punchVFAData
	 * @param sensorDataObj
	 */
	private void resetAllData(PunchDetector punchDetector,
			PunchVFACalculatorOnYYAxis punchVFACalculatorOnYYAxis,
			PunchVFACalculatorOnYZAxis punchVFACalculatorOnYZAxis,
			PunchVFAData punchVFAData, SensorData sensorDataObj,PunchDetectedMap  punchDetectedMap, double punchTime) {
		
		sensorDataObj.resetSensorData();
		punchVFAData.resetPunchVFAData();
		punchVFACalculatorOnYYAxis.resetVFA();
		punchVFACalculatorOnYZAxis.resetVFA();
		punchDetector.resetPunchDetector();
		punchVFACalculatorOnYYAxis.resetBufferAfterPunchFound(getCommonLoggerPattern(),punchTime);
		punchVFACalculatorOnYZAxis.resetBufferAfterPunchFound(getCommonLoggerPattern(),punchTime);
		punchDetectedMap.removeContentBeforeTime(punchTime);
	}
	
	private double getNearestLowGPunchTime(LowGPunchDetection lowGPunchDetection, double time){
		Double punchTime=null;
		try {
			punchTime=lowGPunchDetection.getPunchDetectedMap().lowerKey(time);
			if(punchTime==null){
				return 0;
			}
			
		} catch (Exception e) {
			return 0;
		}
		
		return punchTime;
	}
	

	private void savePunchPeakSummary(PunchVFAData punchVFAData,
			PeakPunchValueDetector peakPunchValueDetector, Integer trainingId) {
		
		if(punchVFAData.isJabFound()){
			Integer currentSpeedJab = (int) Math.round(punchVFAData.getVelocity());
			if (currentSpeedJab > 0&& currentSpeedJab > Integer.parseInt(peakPunchValueDetector.getMaxSpeedJab())) {
				peakPunchValueDetector.setMaxSpeedJab(currentSpeedJab.toString());
			} 
			Integer currentPsiJab = (int) Math.round(punchVFAData.getForce());
			if (currentPsiJab > 0 && currentPsiJab > Integer.parseInt(peakPunchValueDetector.getMaxPsiJab())) {
				peakPunchValueDetector.setMaxPsiJab(currentPsiJab.toString());
			}
		}else if(punchVFAData.isStraightFound()){
			Integer currentSpeedStraight = (int) Math .round(punchVFAData.getVelocity());
			if (currentSpeedStraight > 0 && currentSpeedStraight > Integer.parseInt(peakPunchValueDetector.getMaxSpeedStraight())) {
				peakPunchValueDetector.setMaxSpeedStraight(currentSpeedStraight .toString());
			} 
			Integer currentPsiStraight = (int) Math.round(punchVFAData.getForce());
			if (currentPsiStraight > 0 && currentPsiStraight > Integer.parseInt(peakPunchValueDetector.getMaxPsiStraight())) {
				peakPunchValueDetector.setMaxPsiStraight(currentPsiStraight.toString());
			}
		}else if(punchVFAData.isHookFound()){
			Integer currentSpeedHook = (int) Math.round(punchVFAData.getVelocity());
			if (currentSpeedHook > 0&& currentSpeedHook > Integer.parseInt(peakPunchValueDetector.getMaxSpeedHook())) {
				peakPunchValueDetector.setMaxSpeedHook(currentSpeedHook.toString());
			} 
			Integer currentPsiHook = (int) Math.round(punchVFAData.getForce());
			if (currentPsiHook > 0 && currentPsiHook > Integer.parseInt(peakPunchValueDetector.getMaxPsiHook())) {
				peakPunchValueDetector.setMaxPsiHook(currentPsiHook.toString());
			} 
		}else if(punchVFAData.isUpperCutFound()){
			Integer currentSpeedUpperCut = (int) Math.round(punchVFAData.getVelocity());
			if (currentSpeedUpperCut > 0&& currentSpeedUpperCut > Integer.parseInt(peakPunchValueDetector.getMaxSpeedUpper())) {
				peakPunchValueDetector.setMaxSpeedUpper(currentSpeedUpperCut.toString());
			} 
			Integer currentForceUpperCut = (int) Math.round(punchVFAData.getForce());
			if (currentForceUpperCut > 0 && currentForceUpperCut > Integer.parseInt(peakPunchValueDetector.getMaxPsiUpper())) {
				peakPunchValueDetector.setMaxPsiUpper(currentForceUpperCut.toString());
			} 
		}
		
	}
	
	private void saveMatchDataDetails(PunchVFAData punchVFAData,SensorData sensorData, Integer trainingId) {
	
		Date dateRecieved = new Date();
		java.sql.Timestamp dataReceiveTime = new java.sql.Timestamp(dateRecieved.getTime());

    }

	 private void savePunchData(PunchVFAData punchVFAData, Integer trainingId) {
	 	Date currentTime = new Date();
	 	java.sql.Timestamp punchTime = new java.sql.Timestamp(currentTime.getTime());
	}

	/**
	  * Method sending match data to client 
	  * @param maxSpeedStraight
	  * @param maxSpeedJab
	  * @param maxSpeedHook
	  * @param maxSpeedUpper
	  * @param maxPsiStraight
	  * @param maxPsiJab
	  * @param maxPsiHook
	  * @param maxPsiUpper
	  * @param hand
	  * @param VxyzMPH
	  * @param forceLSB
	  * @param headTrauma
	  * @param punchType
	  */
	private void sendMatchData(PunchVFAData punVfaData) {
		System.out.println("aa in sendmatchData...");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss:SS");
		String jsonData="{"+
		"\"success\":true,"+
		"\"jsonObject\":{"+
			"\"punchType\":\""+punVfaData.getPunchType()+"\","+
			"\"speed\":\""+Math.round(punVfaData.getVelocity())+"\","+
			"\"force\":\""+Math.round(punVfaData.getForce())+"\","+
			"\"boxerStatics\":{"+
			"\"maxSpeed\":{"+
				"\"straight\":\""+punVfaData.getStraightVelocity()+"\","+
				"\"jab\":\""+punVfaData.getJabVelocity()+"\","+
				"\"hook\":\""+punVfaData.getHookVelocity()+"\","+
				"\"upper\":\""+punVfaData.getUpperCutVelocity()+"\""+
			"},"+
			"\"maxPsi\":{"+
				"\"straight\":\""+punVfaData.getStraightForce()+"\","+
				"\"jab\":\""+punVfaData.getJabForce()+"\","+
				"\"hook\":\""+punVfaData.getHookForce()+"\","+
				"\"upper\":\""+punVfaData.getUpperCutForce()+"\""+
			"}"+
		"}"+
		"}"+
	    "}";
		
		Message msg = mhandler.obtainMessage(3);
		Bundle bundle = new Bundle();
		bundle.putString("jsonData", jsonData);
		msg.setData(bundle);
		mhandler.sendMessage(msg);
	}
}
