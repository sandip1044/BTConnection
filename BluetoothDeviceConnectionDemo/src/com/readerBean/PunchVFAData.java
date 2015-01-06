package com.readerBean;

public class PunchVFAData {
	
	private double velocity;
	private double jabVelocity;
	private double straightVelocity;
	private double hookVelocity;
	private double upperCutVelocity;
	private double velocityOnYYAxis;
	private double velocityOnYZAxis;
	private double unrecognizedVelocity;


	private double force ;
	private double jabForce;
	private double straightForce;
	private double hookForce;
	private double upperCutForce;
	private double forceOnYYAxis;
	private double forceOnYZAxis;
	private double unrecognizedForce;


	private double jabAcc ;
	private double straightAcc;
	private double hookAcc;
	private double upperCutAcc;
	private double accOnYYAxis;
	private double accOnYZAxis;
	private double unrecognizedAcc;


	private boolean isPunchFound;
	private boolean isJabFound ;
	private boolean isStraightFound;
	private boolean isHookFound;
	private boolean isUpperCutFound;
	private boolean isUnrecognizedPunch;

	private boolean isVelocityFoundOnYYAxis;
	private boolean isVelocityFoundOnYZAxis;
	
	String hand;
	String punchType;
	
	
	public void resetAllPunchVFAData() {
		this.velocity = 0;
		this.jabVelocity = 0;
		this.straightVelocity = 0;
		this.hookVelocity = 0;
		this.upperCutVelocity = 0;
		this.velocityOnYYAxis = 0;
		this.velocityOnYZAxis = 0;
		this.unrecognizedVelocity = 0;
		this.force = 0;
		this.jabForce = 0;
		this.straightForce = 0;
		this.hookForce = 0;
		this.upperCutForce = 0;
		this.forceOnYYAxis = 0;
		this.forceOnYZAxis = 0;
		this.unrecognizedForce = 0;
		this.jabAcc = 0;
		this.straightAcc = 0;
		this.hookAcc = 0;
		this.upperCutAcc = 0;
		this.accOnYYAxis = 0;
		this.accOnYZAxis = 0;
		this.unrecognizedAcc = 0;
		this.isPunchFound = false;
		this.isJabFound = false;
		this.isStraightFound = false;
		this.isHookFound = false;
		this.isUpperCutFound = false;
		this.isUnrecognizedPunch = false;
		this.isVelocityFoundOnYYAxis = false;
		this.isVelocityFoundOnYZAxis = false;
		this.hand = "";
	}
	public PunchVFAData() {
		super();
		this.hand="";
		
	}
	/**
	 * @return the velocity
	 */
	public double getVelocity() {
		return velocity;
	}
	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	/**
	 * @return the jabVelocity
	 */
	public double getJabVelocity() {
		return jabVelocity;
	}
	/**
	 * @param jabVelocity the jabVelocity to set
	 */
	public void setJabVelocity(double jabVelocity) {
		this.jabVelocity = jabVelocity;
	}
	/**
	 * @return the straightVelocity
	 */
	public double getStraightVelocity() {
		return straightVelocity;
	}
	/**
	 * @param straightVelocity the straightVelocity to set
	 */
	public void setStraightVelocity(double straightVelocity) {
		this.straightVelocity = straightVelocity;
	}
	/**
	 * @return the hookVelocity
	 */
	public double getHookVelocity() {
		return hookVelocity;
	}
	/**
	 * @param hookVelocity the hookVelocity to set
	 */
	public void setHookVelocity(double hookVelocity) {
		this.hookVelocity = hookVelocity;
	}
	/**
	 * @return the upperCutVelocity
	 */
	public double getUpperCutVelocity() {
		return upperCutVelocity;
	}
	/**
	 * @param upperCutVelocity the upperCutVelocity to set
	 */
	public void setUpperCutVelocity(double upperCutVelocity) {
		this.upperCutVelocity = upperCutVelocity;
	}
	/**
	 * @return the velocityOnYYAxis
	 */
	public double getVelocityOnYYAxis() {
		return velocityOnYYAxis;
	}
	/**
	 * @param velocityOnYYAxis the velocityOnYYAxis to set
	 */
	public void setVelocityOnYYAxis(double velocityOnYYAxis) {
		this.velocityOnYYAxis = velocityOnYYAxis;
	}
	/**
	 * @return the velocityOnYZAxis
	 */
	public double getVelocityOnYZAxis() {
		return velocityOnYZAxis;
	}
	/**
	 * @param velocityOnYZAxis the velocityOnYZAxis to set
	 */
	public void setVelocityOnYZAxis(double velocityOnYZAxis) {
		this.velocityOnYZAxis = velocityOnYZAxis;
	}
	/**
	 * @return the force
	 */
	public double getForce() {
		return force;
	}
	/**
	 * @param force the force to set
	 */
	public void setForce(double force) {
		this.force = force;
	}
	/**
	 * @return the jabForce
	 */
	public double getJabForce() {
		return jabForce;
	}
	/**
	 * @param jabForce the jabForce to set
	 */
	public void setJabForce(double jabForce) {
		this.jabForce = jabForce;
	}
	/**
	 * @return the straightForce
	 */
	public double getStraightForce() {
		return straightForce;
	}
	/**
	 * @param straightForce the straightForce to set
	 */
	public void setStraightForce(double straightForce) {
		this.straightForce = straightForce;
	}
	/**
	 * @return the hookForce
	 */
	public double getHookForce() {
		return hookForce;
	}
	/**
	 * @param hookForce the hookForce to set
	 */
	public void setHookForce(double hookForce) {
		this.hookForce = hookForce;
	}
	/**
	 * @return the upperCutForce
	 */
	public double getUpperCutForce() {
		return upperCutForce;
	}
	/**
	 * @param upperCutForce the upperCutForce to set
	 */
	public void setUpperCutForce(double upperCutForce) {
		this.upperCutForce = upperCutForce;
	}
	/**
	 * @return the forceOnYYAxis
	 */
	public double getForceOnYYAxis() {
		return forceOnYYAxis;
	}
	/**
	 * @param forceOnYYAxis the forceOnYYAxis to set
	 */
	public void setForceOnYYAxis(double forceOnYYAxis) {
		this.forceOnYYAxis = forceOnYYAxis;
	}
	/**
	 * @return the forceOnYZAxis
	 */
	public double getForceOnYZAxis() {
		return forceOnYZAxis;
	}
	/**
	 * @param forceOnYZAxis the forceOnYZAxis to set
	 */
	public void setForceOnYZAxis(double forceOnYZAxis) {
		this.forceOnYZAxis = forceOnYZAxis;
	}
	/**
	 * @return the jabAcc
	 */
	public double getJabAcc() {
		return jabAcc;
	}
	/**
	 * @param jabAcc the jabAcc to set
	 */
	public void setJabAcc(double jabAcc) {
		this.jabAcc = jabAcc;
	}
	/**
	 * @return the straightAcc
	 */
	public double getStraightAcc() {
		return straightAcc;
	}
	/**
	 * @param straightAcc the straightAcc to set
	 */
	public void setStraightAcc(double straightAcc) {
		this.straightAcc = straightAcc;
	}
	/**
	 * @return the hookAcc
	 */
	public double getHookAcc() {
		return hookAcc;
	}
	/**
	 * @param hookAcc the hookAcc to set
	 */
	public void setHookAcc(double hookAcc) {
		this.hookAcc = hookAcc;
	}
	/**
	 * @return the upperCutAcc
	 */
	public double getUpperCutAcc() {
		return upperCutAcc;
	}
	/**
	 * @param upperCutAcc the upperCutAcc to set
	 */
	public void setUpperCutAcc(double upperCutAcc) {
		this.upperCutAcc = upperCutAcc;
	}
	/**
	 * @return the accOnYYAxis
	 */
	public double getAccOnYYAxis() {
		return accOnYYAxis;
	}
	/**
	 * @param accOnYYAxis the accOnYYAxis to set
	 */
	public void setAccOnYYAxis(double accOnYYAxis) {
		this.accOnYYAxis = accOnYYAxis;
	}
	/**
	 * @return the accOnYZAxis
	 */
	public double getAccOnYZAxis() {
		return accOnYZAxis;
	}
	/**
	 * @param accOnYZAxis the accOnYZAxis to set
	 */
	public void setAccOnYZAxis(double accOnYZAxis) {
		this.accOnYZAxis = accOnYZAxis;
	}
	/**
	 * @return the isPunchFound
	 */
	public boolean isPunchFound() {
		return isPunchFound;
	}
	/**
	 * @param isPunchFound the isPunchFound to set
	 */
	public void setPunchFound(boolean isPunchFound) {
		this.isPunchFound = isPunchFound;
	}
	/**
	 * @return the isJabFound
	 */
	public boolean isJabFound() {
		return isJabFound;
	}
	/**
	 * @param isJabFound the isJabFound to set
	 */
	public void setJabFound(boolean isJabFound) {
		this.isJabFound = isJabFound;
	}
	/**
	 * @return the isStraightFound
	 */
	public boolean isStraightFound() {
		return isStraightFound;
	}
	/**
	 * @param isStraightFound the isStraightFound to set
	 */
	public void setStraightFound(boolean isStraightFound) {
		this.isStraightFound = isStraightFound;
	}
	/**
	 * @return the isHookFound
	 */
	public boolean isHookFound() {
		return isHookFound;
	}
	/**
	 * @param isHookFound the isHookFound to set
	 */
	public void setHookFound(boolean isHookFound) {
		this.isHookFound = isHookFound;
	}
	/**
	 * @return the isUpperCutFound
	 */
	public boolean isUpperCutFound() {
		return isUpperCutFound;
	}
	/**
	 * @param isUpperCutFound the isUpperCutFound to set
	 */
	public void setUpperCutFound(boolean isUpperCutFound) {
		this.isUpperCutFound = isUpperCutFound;
	}
	/**
	 * @return the isUnrecognizedPunch
	 */
	public boolean isUnrecognizedPunch() {
		return isUnrecognizedPunch;
	}
	/**
	 * @param isUnrecognizedPunch the isUnrecognizedPunch to set
	 */
	public void setUnrecognizedPunch(boolean isUnrecognizedPunch) {
		this.isUnrecognizedPunch = isUnrecognizedPunch;
	}
	/**
	 * @return the isVelocityFoundOnYYAxis
	 */
	public boolean isVelocityFoundOnYYAxis() {
		return isVelocityFoundOnYYAxis;
	}
	/**
	 * @param isVelocityFoundOnYYAxis the isVelocityFoundOnYYAxis to set
	 */
	public void setVelocityFoundOnYYAxis(boolean isVelocityFoundOnYYAxis) {
		this.isVelocityFoundOnYYAxis = isVelocityFoundOnYYAxis;
	}
	/**
	 * @return the isVelocityFoundOnYZAxis
	 */
	public boolean isVelocityFoundOnYZAxis() {
		return isVelocityFoundOnYZAxis;
	}
	/**
	 * @param isVelocityFoundOnYZAxis the isVelocityFoundOnYZAxis to set
	 */
	public void setVelocityFoundOnYZAxis(boolean isVelocityFoundOnYZAxis) {
		this.isVelocityFoundOnYZAxis = isVelocityFoundOnYZAxis;
	}
	
	/**
	 * @return the hand
	 */
	public String getHand() {
		return hand;
	}
	/**
	 * @param hand the hand to set
	 */
	public void setHand(String hand) {
		this.hand = hand;
	}
	
	
	
	/**
	 * @return the unrecognizedVelocity
	 */
	public double getUnrecognizedVelocity() {
		return unrecognizedVelocity;
	}
	/**
	 * @param unrecognizedVelocity the unrecognizedVelocity to set
	 */
	public void setUnrecognizedVelocity(double unrecognizedVelocity) {
		this.unrecognizedVelocity = unrecognizedVelocity;
	}
	/**
	 * @return the unrecognizedForce
	 */
	public double getUnrecognizedForce() {
		return unrecognizedForce;
	}
	/**
	 * @param unrecognizedForce the unrecognizedForce to set
	 */
	public void setUnrecognizedForce(double unrecognizedForce) {
		this.unrecognizedForce = unrecognizedForce;
	}
	/**
	 * @return the unrecognizedAcc
	 */
	public double getUnrecognizedAcc() {
		return unrecognizedAcc;
	}
	/**
	 * @param unrecognizedAcc the unrecognizedAcc to set
	 */
	public void setUnrecognizedAcc(double unrecognizedAcc) {
		this.unrecognizedAcc = unrecognizedAcc;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	public String getPunchType() {
		return punchType;
	}
	public void setPunchType(String punchType) {
		this.punchType = punchType;
	}
	public void resetPunchVFAData() {
		this.isPunchFound = false;
		this.isJabFound = false;
		this.isStraightFound = false;
		this.isHookFound = false;
		this.isUpperCutFound = false;
		this.isUnrecognizedPunch = false;
		this.isVelocityFoundOnYYAxis = false;
		this.isVelocityFoundOnYZAxis = false;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PunchVFAData [velocity=" + velocity + ", jabVelocity=" + jabVelocity + ", straightVelocity=" + straightVelocity + ", hookVelocity="
				+ hookVelocity + ", upperCutVelocity=" + upperCutVelocity + ", velocityOnYYAxis=" + velocityOnYYAxis + ", velocityOnYZAxis="
				+ velocityOnYZAxis + ", unrecognizedVelocity=" + unrecognizedVelocity + ", force=" + force + ", jabForce=" + jabForce
				+ ", straightForce=" + straightForce + ", hookForce=" + hookForce + ", upperCutForce=" + upperCutForce + ", forceOnYYAxis="
				+ forceOnYYAxis + ", forceOnYZAxis=" + forceOnYZAxis + ", unrecognizedForce=" + unrecognizedForce + ", jabAcc=" + jabAcc
				+ ", straightAcc=" + straightAcc + ", hookAcc=" + hookAcc + ", upperCutAcc=" + upperCutAcc + ", accOnYYAxis=" + accOnYYAxis
				+ ", accOnYZAxis=" + accOnYZAxis + ", unrecognizedAcc=" + unrecognizedAcc + ", isPunchFound=" + isPunchFound + ", isJabFound="
				+ isJabFound + ", isStraightFound=" + isStraightFound + ", isHookFound=" + isHookFound + ", isUpperCutFound=" + isUpperCutFound
				+ ", isUnrecognizedPunch=" + isUnrecognizedPunch + ", isVelocityFoundOnYYAxis=" + isVelocityFoundOnYYAxis
				+ ", isVelocityFoundOnYZAxis=" + isVelocityFoundOnYZAxis + ", hand=" + hand + "]";
	}
	
	 
	

}
