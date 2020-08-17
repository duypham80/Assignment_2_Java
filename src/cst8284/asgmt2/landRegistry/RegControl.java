/*
* Course Name: CST8284_303
* Student Name: Duy Pham
* Class Name: RegControl
* Date: July 12, 2020
*/

package cst8284.asgmt2.landRegistry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;

public class RegControl {
	private ArrayList<Registrant> registrants = new ArrayList<Registrant>();
	private ArrayList<Property> properties = new ArrayList<Property>();

	private ArrayList<Registrant> getRegistrants() {
		return registrants;
	}

	private ArrayList<Property> getProperties() {
		return properties;
	}

	public Registrant addNewRegistrant(Registrant reg) {
		getRegistrants().add(reg);
		return reg;
	}

	public Registrant findRegistrant(int regNum) {
		// check the regNum is contained in registrants ArrayList by calling
		// getRegistrants
		for (int i = 0; i < getRegistrants().size(); i++) {
			if (getRegistrants().get(i).getRegNum() == regNum)
				return getRegistrants().get(i);
		}
		return null;
	}

	public ArrayList<Registrant> listOfRegistrants() {
		return getRegistrants(); // just return the registrants
	}

	public Registrant deleteRegistrant(int regNum) {
		// find the registrant in the ArrayList then remove it out of the Array if it is
		// not null
		Registrant foundRegistrant = findRegistrant(regNum);
		if (foundRegistrant != null) {
			getRegistrants().remove(foundRegistrant);
		}
		return foundRegistrant;
	}

	public Property addNewProperty(Property prop) {
		// check if the input prop overlaps another one
		Property prop_overlap = propertyOverlaps(prop);
		if (prop_overlap != null) {
			prop = prop_overlap;
		} else {
			// just add it at the end of the array using ArrayList.add() method
			getProperties().add(prop);
		}
		return prop;
	}

	public boolean deleteProperties(ArrayList<Property> properties) {
		// if the array size is 0, there is no property
		if (properties.size() == 0) {
			return false;
		}
		// remove all properties in the array using for loop
		// for (Property property: getProperties()) {
		for (Property property : properties) {
			getProperties().remove(property);
		}
		return true;
	}

	public Property changePropertyRegistrant(Property originalProperty, int newRegNum) {
		// call findRegistrant to find the available registrant which has the same
		// regNum as originalProperty
		Registrant found_Registrant = findRegistrant(originalProperty.getRegNum());
		if (found_Registrant != null) {
			// if the registrant is found, return the changed property
			return new Property(originalProperty, newRegNum);
		} else { // return null if the program can't find the registrant
			return null;
		}
	}

	public ArrayList<Property> listOfProperties(int regNum) {
		// loop through all properties then check each one has the same regNum
		ArrayList<Property> prop_list = new ArrayList<Property>();
		for (Property prop : listOfAllProperties())
			if (prop.getRegNum() == regNum) {
				prop_list.add(prop);
			}
		return prop_list;
	}

	public ArrayList<Property> listOfAllProperties() {
		// return all properties
		return getProperties();
	}

	private Property propertyOverlaps(Property prop) {
		// loop through all the properties and use overLaps() method to check if the
		// prop overlaps each one
		for (Property property : listOfAllProperties()) {
			if (property.overLaps(prop)) {
				return property;
			}
		}
		return null;
	}
	
		
	public <T> boolean saveToFile(ArrayList<T> source, String fileName) {
		// Some methods used below is from Hybrid-06 by Dave Houtman
		// [2016] Java - FileInputStream and FileOutputStream example[Webpage]. Retrieved from
		// https://www.boraji.com/java-fileinputstream-and-fileoutputstream-example	
		try (FileOutputStream regFileStream = new FileOutputStream(RegView.REGISTRANTS_FILE);
				ObjectOutputStream oosRegStream = new ObjectOutputStream(regFileStream);
				FileOutputStream propFileStream = new FileOutputStream(RegView.PROPERTIES_FILE);
				ObjectOutputStream oosPropStream = new ObjectOutputStream(propFileStream);){				
			// create the file and save data to it
			oosRegStream.writeObject(registrants);	
			regFileStream.close();
			oosRegStream.close();
			oosPropStream.writeObject(properties);
			propFileStream.close();
			oosPropStream.close();
			return true;
			
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;		
		}
	}

	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> loadFromFile(String fileName) {		
		// Some methods used below is from Hybrid-06 by Dave Houtman
		// [2016] Java - FileInputStream and FileOutputStream example[Webpage]. Retrieved from
		// https://www.boraji.com/java-fileinputstream-and-fileoutputstream-example	
		try (FileInputStream fin = new FileInputStream(fileName); 
			ObjectInputStream ois = new ObjectInputStream(fin);) 
			{// load data from the file with the given fileName then return the ArrayList
			// contain the data
			ArrayList<T> obj = (ArrayList<T>) ois.readObject();
			// check if fileName is of Property objects or Registrant objects
			// clear the existing ArrayLists then add all the data from file to them
			if (fileName == RegView.PROPERTIES_FILE) {
				getProperties().clear();
				getProperties().addAll((ArrayList<Property>) obj);
			} else {
				getRegistrants().clear();
				getRegistrants().addAll((ArrayList<Registrant>) obj);
			}
			ois.close();
			return obj;
		} catch (ClassNotFoundException ex) {
			return null;
		} catch (FileNotFoundException ex) {
			return null;
		} catch (IOException ex) {
			return null;
		}
	}
}
