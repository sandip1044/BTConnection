package com.DTO;

import java.io.Serializable;

public class TrainingDetailsDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String speed;
	private String force;
	private String punchType;
	
	public TrainingDetailsDTO() {
		super();
	}
	
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getForce() {
		return force;
	}
	public void setForce(String force) {
		this.force = force;
	}
	public String getPunchType() {
		return punchType;
	}
	public void setPunchType(String punchType) {
		this.punchType = punchType;
	}
	public TrainingDetailsDTO(String speed, String force, String punchType) {
		super();
		this.speed = speed;
		this.force = force;
		this.punchType = punchType;
	}
	@Override
	public String toString() {
		return "TrainingDetailsDTO [speed=" + speed + ", force=" + force
				+ ", punchType=" + punchType + ", getSpeed()=" + getSpeed()
				+ ", getForce()=" + getForce() + ", getPunchType()="
				+ getPunchType() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
