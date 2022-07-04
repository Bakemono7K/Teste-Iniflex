package main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import dao.DAO;
import model.Employee;

public class Main {
	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Locale locale = new Locale("en", "US");
		Scanner sc = new Scanner(System.in);
		sc.useLocale(locale);
		DAO<Employee> dao = new DAO<Employee>();
		System.out.println("Which method do you want to use?");
		int method = sc.nextInt();
		// Ol�!! Os metodos est�o comentados com o que eles deveriam fazer segundo as
		// instru��es e para executa-los basta inserir o n�mero do m�todo desejado.

		// 3.1 - Inserir todos os funcion�rios, na mesma ordem e informa��es da tabela
		// acima.
		// Implementei de uma forma em que se possa adicionar quantos funcionarios forem
		// desejados.
		// M�todo numero 1.

		switch (method) {
		case 1 : 
			String YesOrNo = "yes";
			while (YesOrNo.charAt(0) == 'y') {
			System.out.println("Enter employee data: ");
			String name = sc.next();
			String localDateString = sc.next();
			BigDecimal salary = sc.nextBigDecimal();
			String function = sc.next();
			LocalDate birthDate = LocalDate.parse(localDateString, formatter);
			Employee newEmp = new Employee(name, birthDate, salary, function);
			dao.completeAdd(newEmp);
			System.out.println("Do you want to add another employee? (y/n)");
			YesOrNo = sc.next();
			YesOrNo.toLowerCase();
			}
			break;
		// 3.2 - Remover o funcion�rio �Jo�o� da lista.
		// Remova qualquer funcion�rio do banco de dados informando o nome do mesmo.
		// M�todo n�mero 2.
		case 2 :
			System.out.println("Select employee by name to be deleted from database:");
			String name = sc.next();
			dao.deleteByName(name);
			break;
//		//3.3 � Imprimir todos os funcion�rios com todas suas informa��es, sendo que:
//		� informa��o de data deve ser exibido no formato dd/mm/aaaa;
//		� informa��o de valor num�rico deve ser exibida no formatado com separador de milhar como ponto e decimal como v�rgula.
//		M�todo n�mero 3.
		case 3 : 
			List<Employee> list = dao.getAllEmployee();
			for (Employee e : list) {
				String birthDateString = e.getBirthDate().format(formatter);
				System.out.println(e.getName() + " " + birthDateString + " " + e.getSalary() + " " + e.getFuncao());
			}
			break;
		// 3.4 � Os funcion�rios receberam 10% de aumento de sal�rio, atualizar a lista
		// de funcion�rios com novo valor. O m�todo tamb�m se asegura de apenas permitir 2 casas d�cimais depois do .
		// M�todo n�mero 4
		case 4 : 
			List<Employee> listRaise = dao.TenRaise();
			for (Employee e : listRaise) {
				String birthDateString = e.getBirthDate().format(formatter);
				System.out.println(e.getName() + " " + birthDateString + " " + e.getSalary() + " " + e.getFuncao());
			}
			break;
		// 3.5 - Agrupar os funcion�rios por fun��o em um MAP, sendo a chave a �fun��o�
		// e o valor a �lista de funcion�rios�.
		// 3.6 � Imprimir os funcion�rios, agrupados por fun��o.
		// M�todo que agrupa os funcion�rios e tamb�m imprime organizando pela suas
		// fun��es.
		// M�todo n�mero 5
		case 5 : 
			List<Employee> listGetAll = new ArrayList<>();
			listGetAll.addAll(dao.getAllEmployee());

			Map<String, List<Employee>> result = listGetAll.stream()
					.collect(Collectors.groupingBy(funcao -> funcao.getFuncao()));
			System.out.println(result);
			
			break;
		// 3.8 � Imprimir os funcion�rios que fazem anivers�rio no m�s 10 e 12.
		// M�todo n�mero 6
		case 6 : 
			List<Employee> listBirthday = new ArrayList<>();
			listBirthday.addAll(dao.getAllEmployee());
			List<Employee> resultList = listBirthday
					.stream().filter(ld -> ld.getBirthDate().getMonthValue() == 10
							|| ld.getBirthDate().getMonthValue() == 11 || ld.getBirthDate().getMonthValue() == 12)
					.collect(Collectors.toList());
			for (Employee e : resultList) {
				System.out.println(e.getName() + " " + e.getBirthDate().format(formatter) + " " + e.getFuncao());
			}
			
			break;	
		// 3.9 � Imprimir o funcion�rio com a maior idade, exibir os atributos: nome e
		// idade.
		// M�todo n�mero 7
		case 7 : 
			List<Employee> listOldestPerson = new ArrayList<>();
			listOldestPerson.addAll(dao.getAllEmployee());
			Employee oldestPerson = listOldestPerson.get(0);
			LocalDate timeNow = LocalDate.now();
			for (Employee e : listOldestPerson) {
				int listAge = Period.between(e.getBirthDate(), timeNow).getYears();
				int tempAge = Period.between(oldestPerson.getBirthDate(), timeNow).getYears();
				if (tempAge < listAge) {
					oldestPerson = e;
				}
			}
			int oldestPersonAge = Period.between(oldestPerson.getBirthDate(), timeNow).getYears();
			System.out
					.println("Oldest person is: " + oldestPerson.getName() + " at the age of: " + oldestPersonAge);
			
			break;
		// 3.10 � Imprimir a lista de funcion�rios por ordem alfab�tica.
		// M�todo n�mero 8.
		case 8 : 
			List<Employee> listAlphabetical = new ArrayList<>();
			listAlphabetical.addAll(dao.getAllEmployee());
			Comparator<Employee> byName = Comparator.comparing(Employee::getName);
			listAlphabetical.sort(byName);
			for (Employee e : listAlphabetical) {
				String birthDateString = e.getBirthDate().format(formatter);
				System.out.println(e.getName() + " " + birthDateString + " " + e.getSalary() + " " + e.getFuncao());
			}
			break;
		// 3.11 � Imprimir o total dos sal�rios dos funcion�rios.
		// M�todo n�mero 9.
		case 9 :
			List<Employee> listTotalSalary = new ArrayList<>();
			listTotalSalary.addAll(dao.getAllEmployee());
			BigDecimal totalSalary = new BigDecimal(0.0);
			for (Employee e : listTotalSalary) {
				totalSalary = totalSalary.add(e.getSalary());
			}
			System.out.printf("Sal�rio total dos funcionarios: R$%.2f", totalSalary);
			break;
		// 3.12 � Imprimir quantos sal�rios m�nimos ganha cada funcion�rio, considerando
		// que o sal�rio m�nimo � R$1212.00.
		// M�todo n�mero 10.
		case 10 : 
			List<Employee> listMinimumWage = new ArrayList<>();
			listMinimumWage.addAll(dao.getAllEmployee());
			BigDecimal howManyMinumumWage = new BigDecimal(0.0);
			BigDecimal minimumWage = new BigDecimal(1212.0);
			for (Employee e : listMinimumWage) {
				howManyMinumumWage = (e.getSalary().divide(minimumWage, 0, RoundingMode.HALF_UP));
				System.out.println(e.getName() + " ganha " + howManyMinumumWage + " sal�rios m�nimos");
			}
			break;
		}

	}
}
