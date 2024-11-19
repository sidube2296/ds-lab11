package edu.uwm.cs351;

public class Person {

	private String firstName;
	private String lastName;
	
	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
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

	
	@Override
	public int hashCode()
	{
		int hash = lastName.charAt(0);
		return hash;
	}
	
	@Override
	public boolean equals(Object other)
	{
		Person otherPerson = (Person) other;
		
		return firstName.equals(otherPerson.firstName) && lastName.equals(otherPerson.lastName);
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}
