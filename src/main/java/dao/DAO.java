package dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Employee;

public class DAO<E> {

	private static EntityManagerFactory emf;

	private static EntityManager em;

	private Class<E> wantedClass;

	private static final DecimalFormat df = new DecimalFormat("0.00");

	static {
		try {
			emf = Persistence.createEntityManagerFactory("jpa");
		} catch (Exception e) {

		}
	}

	public DAO() {
		this(null);
	}

	public DAO(Class<E> wantedClass) {
		this.wantedClass = wantedClass;
		em = emf.createEntityManager();
	}

	public DAO<E> openTransaction() {
		em.getTransaction().begin();
		return this;
	}

	public DAO<E> closeTransaction() {
		em.getTransaction().commit();
		return this;
	}

	public void completeAdd(E entity) {
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
	}
	public void deleteByName(Object name) {
		em.getTransaction().begin();
		Query q = em.createNativeQuery("DELETE FROM funcionarios WHERE name = ?", Employee.class);
		q.setParameter(1, name);
		q.executeUpdate();

		em.getTransaction().commit();
	}

	public List<Employee> getAllEmployee() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		Query q = em.createNativeQuery("SELECT * FROM funcionarios", Employee.class);
		List<Employee> list = new ArrayList<>();
		list = (List<Employee>) q.getResultList();
		em.close();
		emf.close();
		return list;

	}

	public List<Employee> TenRaise() {
		DAO<Employee> dao = new DAO<Employee>();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		Query q = em.createNativeQuery("SELECT * FROM funcionarios", Employee.class);
		List<Employee> list = new ArrayList<>();
		list = (List<Employee>) q.getResultList();
		BigDecimal raise = new BigDecimal(0.10);
		for (Employee e : list) {
			e.setSalary(e.getSalary().multiply(raise).add(e.getSalary()).setScale(2, RoundingMode.HALF_UP));
			System.out.println(e.getName() + " " + e.getBirthDate() + " " + e.getSalary() + " " + e.getFuncao());
		}
		for (Employee e : list) {
			em.getTransaction().begin();
			em.persist(e);
			em.getTransaction().commit();
		}
		em.close();
		emf.close();
		return list;
	}

	public void closeDAO() {
		em.close();
	}
}
