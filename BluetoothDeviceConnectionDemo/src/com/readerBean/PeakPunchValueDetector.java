package com.readerBean;

public class PeakPunchValueDetector {

	private String maxSpeedStraight ;
	private String maxSpeedJab ;
	private String maxSpeedHook ;
	private String maxSpeedUpper ;
	private String maxPsiStraight ;
	private String maxPsiJab ;
	private String maxPsiHook ;
	private String maxPsiUpper ;
	private double maxForceLSB ;
	private double maxVxyzMPH ;
	
	public PeakPunchValueDetector() {
		super();
		this.maxSpeedStraight = "0";
		this.maxSpeedJab = "0";
		this.maxSpeedHook = "0";
		this.maxSpeedUpper = "0";
		this.maxPsiStraight = "0";
		this.maxPsiJab = "0";
		this.maxPsiHook = "0";
		this.maxPsiUpper = "0";
		this.maxForceLSB = 0;
		this.maxVxyzMPH = 0;
	}

	public PeakPunchValueDetector(String maxSpeedStraight, String maxSpeedJab,
			String maxSpeedHook, String maxSpeedUpper, String maxPsiStraight,
			String maxPsiJab, String maxPsiHook, String maxPsiUpper,
			double maxForceLSB, double maxVxyzMPH) {
		super();
		this.maxSpeedStraight = maxSpeedStraight;
		this.maxSpeedJab = maxSpeedJab;
		this.maxSpeedHook = maxSpeedHook;
		this.maxSpeedUpper = maxSpeedUpper;
		this.maxPsiStraight = maxPsiStraight;
		this.maxPsiJab = maxPsiJab;
		this.maxPsiHook = maxPsiHook;
		this.maxPsiUpper = maxPsiUpper;
		this.maxForceLSB = maxForceLSB;
		this.maxVxyzMPH = maxVxyzMPH;
	}

	/**
	 * @return the maxSpeedStraight
	 */
	public String getMaxSpeedStraight() {
		return maxSpeedStraight;
	}

	/**
	 * @param maxSpeedStraight the maxSpeedStraight to set
	 */
	public void setMaxSpeedStraight(String maxSpeedStraight) {
		this.maxSpeedStraight = maxSpeedStraight;
	}

	/**
	 * @return the maxSpeedJab
	 */
	public String getMaxSpeedJab() {
		return maxSpeedJab;
	}

	/**
	 * @param maxSpeedJab the maxSpeedJab to set
	 */
	public void setMaxSpeedJab(String maxSpeedJab) {
		this.maxSpeedJab = maxSpeedJab;
	}

	/**
	 * @return the maxSpeedHook
	 */
	public String getMaxSpeedHook() {
		return maxSpeedHook;
	}

	/**
	 * @param maxSpeedHook the maxSpeedHook to set
	 */
	public void setMaxSpeedHook(String maxSpeedHook) {
		this.maxSpeedHook = maxSpeedHook;
	}

	/**
	 * @return the maxSpeedUpper
	 */
	public String getMaxSpeedUpper() {
		return maxSpeedUpper;
	}

	/**
	 * @param maxSpeedUpper the maxSpeedUpper to set
	 */
	public void setMaxSpeedUpper(String maxSpeedUpper) {
		this.maxSpeedUpper = maxSpeedUpper;
	}

	/**
	 * @return the maxPsiStraight
	 */
	public String getMaxPsiStraight() {
		return maxPsiStraight;
	}

	/**
	 * @param maxPsiStraight the maxPsiStraight to set
	 */
	public void setMaxPsiStraight(String maxPsiStraight) {
		this.maxPsiStraight = maxPsiStraight;
	}

	/**
	 * @return the maxPsiJab
	 */
	public String getMaxPsiJab() {
		return maxPsiJab;
	}

	/**
	 * @param maxPsiJab the maxPsiJab to set
	 */
	public void setMaxPsiJab(String maxPsiJab) {
		this.maxPsiJab = maxPsiJab;
	}

	/**
	 * @return the maxPsiHook
	 */
	public String getMaxPsiHook() {
		return maxPsiHook;
	}

	/**
	 * @param maxPsiHook the maxPsiHook to set
	 */
	public void setMaxPsiHook(String maxPsiHook) {
		this.maxPsiHook = maxPsiHook;
	}

	/**
	 * @return the maxPsiUpper
	 */
	public String getMaxPsiUpper() {
		return maxPsiUpper;
	}

	/**
	 * @param maxPsiUpper the maxPsiUpper to set
	 */
	public void setMaxPsiUpper(String maxPsiUpper) {
		this.maxPsiUpper = maxPsiUpper;
	}

	/**
	 * @return the maxForceLSB
	 */
	public double getMaxForceLSB() {
		return maxForceLSB;
	}

	/**
	 * @param maxForceLSB the maxForceLSB to set
	 */
	public void setMaxForceLSB(double maxForceLSB) {
		this.maxForceLSB = maxForceLSB;
	}

	/**
	 * @return the maxVxyzMPH
	 */
	public double getMaxVxyzMPH() {
		return maxVxyzMPH;
	}

	/**
	 * @param maxVxyzMPH the maxVxyzMPH to set
	 */
	public void setMaxVxyzMPH(double maxVxyzMPH) {
		this.maxVxyzMPH = maxVxyzMPH;
	}

	 /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PeakPunchValueDetector [maxSpeedStraight=" + maxSpeedStraight + ", maxSpeedJab=" + maxSpeedJab + ", maxSpeedHook=" + maxSpeedHook
				+ ", maxSpeedUpper=" + maxSpeedUpper + ", maxPsiStraight=" + maxPsiStraight + ", maxPsiJab=" + maxPsiJab + ", maxPsiHook="
				+ maxPsiHook + ", maxPsiUpper=" + maxPsiUpper + ", maxForceLSB=" + maxForceLSB + ", maxVxyzMPH=" + maxVxyzMPH + "]";
	}
}
