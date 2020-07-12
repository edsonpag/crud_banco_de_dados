package ui;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import model.Employee;
import model.Animal;
import model.College;
import model.Department;

import dao.EmployeeDAO;
import dao.AnimalDAO;
import dao.CollegeDAO;
import dao.DepartmentDAO;

public class Menu {

	void menu () {
		
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		
		while(option != 9) {
			System.out.println("--------------------------------------");
			System.out.println("1 - CRUD de EMPREGADOS");
			System.out.println("2 - CRUD de DEPARTAMENTOS");
			System.out.println("3 - CRUD de ANIMAIS");
			System.out.println("4 - CRUD de FACULDADES");
			System.out.println("9 - SAIR");
			System.out.println("--------------------------------------");
			
			
			option = scanner.nextInt();
			
			switch(option) {
				case 1:
					this.menuEmployee();
					break;
					
				case 2:
					this.menuDepartment();
					break;
					
				case 3:
					this.menuAnimal();
					break;
					
				case 4:
					this.menuCollege();
					break;
					
				case 9:
					System.out.println("Saindo...");
					break;
					
				default:
					System.out.println("Opção invalida");
					break;
			}
		
		
		}
		
		EmployeeDAO dao = new EmployeeDAO();
		
		List<Employee> employees = dao.getAll();
		
		for(Employee e : employees) {
			System.out.print(e.getName());
		}
		
	}
	
	void menuEmployee() {
		
		int option = 0;
		int id = 0;
		
		EmployeeDAO dao = new EmployeeDAO();
		Scanner scanner = new Scanner(System.in);
		Employee employee = new Employee();
		Department department = new Department();
		
		
		while(option != 0) {
			System.out.println("--------------------------------------");
			System.out.println("    CRUD EMPREGADO");
			System.out.println("1 - Inserir");
			System.out.println("2 - Deletar");
			System.out.println("3 - Selecionar por id");
			System.out.println("4 - Listar todos");
			System.out.println("5 - Atualizar");
			System.out.println("9 - Voltar ao menu principal");
			System.out.println("--------------------------------------");
		}
		
		option = scanner.nextInt();
		
		switch(option) {
			case 1:
				String name;
				int age;
				String department_name;
				
				
				
				System.out.println("Digite o nome: ");
				name = scanner.nextLine();
				System.out.println("Digite a idade: ");
				age = scanner.nextInt();
				System.out.println("Digite o departamento: ");
				department_name = scanner.nextLine();
				
				department.setName(department_name);
				
				employee.setName(name);
				employee.setAge(age);
				employee.setDepartment(department);
				
				dao.insert(employee);
				System.out.println("Inserido!");
				break;
				
			case 2:
				System.out.println("Digite o id: ");
				id = scanner.nextInt();
				
				dao.delete(id);
				System.out.println("Deletado!");
				break;
				
			case 3:
				System.out.println("Digite o id: ");
				id = scanner.nextInt();
				
				employee = dao.getById(id);
				if(employee != null) {
					System.out.print(employee.getName());
				}
				
				break;
				
			case 9:
				System.out.println("Saindo...");
				break;
				
			default:
				System.out.println("Opção invalida");
				break;
		}
		
	}
	
	void menuDepartment() {
			
	}
	
	void menuAnimal() {
		
	}
	
	void menuCollege() {
		
	}

}
