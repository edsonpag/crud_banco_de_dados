package model;

public class Animal {
	int id;
	private String animal;
	int age;
	
	public Animal() {
		
	}
	
	public Animal(int id, String animal, int age) {
		this.id = id;
		this.animal = animal;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
	
}
