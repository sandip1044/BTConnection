package com.bluetoothdeviceconnectiondemo;

import com.readerBean.PunchDetectionConfig;


public class CommonUtil {
	public static final String MMAGLOVE_PREFIX = "MMAGlove-";
	public static final String SCANNING_FINISHED = "Scanning finished.";
	public static final String BLANK_TEXT = "";
	public static final String SENSOR_NOT_FOUND_MESSAGE = "Sensor not found.";
	public static final String BLUETOOTH_ACCESS_ERROR = "Sorry, having problem with bluetooth.";
	public static final String DETECTED_TEXT = " detected.";
	public static final String SENSOR_FOUND_MESSAGE = "Sensor :- ";
	public static final String BLANK_SENSOR_ERROR_MESSAGE = "Sensor id cannot be blank.";
	public static final String UNABLE_TO_CONNECT_WITH_SENSOR_MESSAGE_TEXT = "Unable to connect sensor.";
	public static final String SENSOR_CONNECTION_LOST_MESSAGE_TEXT = "Sensor connection was lost.";
	public static final String SOCKET_CONNECTION_FAIL_ERROR = "Socket connection failed.";
	public static final String SENSOR_CONNECTED = "Sensor connected.";
	public static final String SENSOR_DISCONNECTED = "Sensor disconnected.";
	public static final String SENSOR_CONNECTION_ESTABLISHED_MESSAGE = "Connection established with sensor ";
	public static final String CONNECTED_DEVICE_TEXT = "ConnectedDevice";
	public static final String DISCONNECTED_DEVICE_TEXT = "DisconnectedDevice";
	public static final String SENSOR_CONNECTION_FAILED_MESSAGE = "Failed to connect with sensor = ";
	public static final String BLUETOOTH_SOCKET_CONNECTION_FAILED_MESSAGE = "Failed to connect with bluetooth socket.";
	public static final String PROPERTIESFILEPATH = "config.properties";
	public static final String TEMPLATEFILEPATH = "templates.csv";
	public static final String FORCE_TABLE_TEMPLATE_FILEPATH = "forceTable.csv";
	public static final String APP_DIRECTORY = "EFD Training";
	public static final String CONFIG_DIRECTORY = "config";
	public static final String CONFIG_PATH = "config";
	
	public static final String LEFT_HAND = "left";
	public static final String RIGHT_HAND = "right";
	
	public static final String NON_TRADITIONAL_TEXT = "Non-Traditional";
	public static final String TRADITIONAL_TEXT = "Traditional";
	public static final String NON_TRADITIONAL = "Non-Traditional (right foot front)";
	public static final String TRADITIONAL = "Traditional- Left foot front";
	
	public static final String OLD_FORCE_FORMULA = "0";
	public static final String CSV_FORCE_FORMULA = "1";
	
	public static final int START_BYTE = 170;
	public static final int LOW_G_MODE = 64;
	public static final int HIGH_G_MODE = 65;
	public static final int MESSAGE_LENGTH_LSB = 104;
	public static final int MESSAGE_LENGTH_MSB = 0;
	public static final int SAMPLE_PACKET_SIZE = 16;
	
	//----------------------------- Punch Types --------------------------------
	public static final String LEFT_JAB = "LJ";
	public static final String RIGHT_JAB = "RJ";
	public static final String LEFT_STRAIGHT = "LS";
	public static final String RIGHT_STRAIGHT = "RS";
	public static final String LEFT_HOOK = "LH";
	public static final String RIGHT_HOOK = "RH";
	public static final String LEFT_UPPERCUT = "LU";
	public static final String RIGHT_UPPERCUT = "RU";
	public static final String LEFT_UNRECOGNIZED = "LR";
	public static final String RIGHT_UNRECOGNIZED = "RR";
	public static final String JAB = "JAB";
	public static final String STRAIGHT = "STRAIGHT";
	public static final String HOOK = "HOOK";
	public static final String UPPERCUT = "UPPERCUT";
	public static final String UNRECOGNIZED = "UNRECOGNIZED";
	public static final String UNIDENTIFIED = "UNIDENTIFIED";
	
	public static final String VELOCITY_EST_YY_AXIS = "YY";
	public static final String VELOCITY_EST_YZ_AXIS = "YZ";
	public static final double MAX_VELOCITY = PunchDetectionConfig.getInstance().getMaxVelocity();
	public static final double MIN_VELOCITY = PunchDetectionConfig.getInstance().getMinVelocity();
	public static final double MAX_FORCE = PunchDetectionConfig.getInstance().getMaxForce();
	public static final double MIN_FORCE = PunchDetectionConfig.getInstance().getMinForce();
	
	public static final double LOWG_SAMPLE_TIME_DIFFERENCE = 2.5;
}
