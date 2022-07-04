package model;

import java.time.LocalDate;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Person {

	private String name;
	
	private LocalDate birthDate;

	public Person() {

	}
	public Person(String name, LocalDate birthDate) {
		this.name = name;
		this.birthDate = birthDate;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setPrice(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

}
