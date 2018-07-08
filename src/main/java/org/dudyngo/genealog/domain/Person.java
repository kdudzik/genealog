package org.dudyngo.genealog.domain;

public class Person {
	private String firstName;
	private String lastName;
	private Person father;
	private Person mother;
	private Sex sex;

	public Person() {
	}

	public Person(String firstName, String lastName, Sex sex) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
	}

	public static Person woman(String firstName, String lastName) {
		return new Person(firstName, lastName, Sex.WOMAN);
	}

	public static Person man(String firstName, String lastName) {
		return new Person(firstName, lastName, Sex.MAN);
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

	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		this.father = father;
	}

	public Person getMother() {
		return mother;
	}

	public void setMother(Person mother) {
		this.mother = mother;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Sex getSex() {
		return sex;
	}

	@Override
	public String toString() {
		if (lastName == null) {
			return firstName;
		} else {
			return firstName + ' ' + lastName;
		}
	}
}
