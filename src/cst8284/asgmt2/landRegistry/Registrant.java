/*
* Course Name: CST8284_303
* Student Name: Duy Pham
* Class Name: Registrant
* Date: July 11, 2020
*/

package cst8284.asgmt2.landRegistry;

import java.io.Serializable;

public class Registrant implements Serializable {
	public static final long serialVersionUID = 1L;
	private static final int REGNUM_START = 1000;
	private static int currentRegNum = REGNUM_START;
	private final int REGNUM = currentRegNum;
	private String firstName;
	private String lastName;

	public Registrant() {
		// default constructor which chains the parameterize constructor below with the
		// parameter is "unknown unknown"
		this("unknown unknown");
	}

	public Registrant(String firstLastName) {
		// split the first and last name by the space " " and store them into
		// coresponding variables using setters
		String[] nameStrings = firstLastName.split(" ");
		setFirstName(nameStrings[0]);
		setLastName(nameStrings[1]);
		// increase currentRegNum by 1 each time we create a new Registrant object
		incrToNextRegNum();
	}

	public int getRegNum() {
		return REGNUM;
	}

	private static void incrToNextRegNum() {
		currentRegNum++;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Registrant)) return false;
		Registrant reg = (Registrant)obj;
		// compare firstname, lastname and regNum of this with obj
		//this code provided by Dave Houtman [2020] personal communication
		return this.getFirstName() == reg.getFirstName()
				&& this.getLastName() == reg.getLastName()
				&& this.getRegNum() == reg.getRegNum();
	}

	public String toString() {
		return String.format("Name: %s %s\nRegistration Number: #%d", getFirstName(), getLastName(), getRegNum());
	}
}
