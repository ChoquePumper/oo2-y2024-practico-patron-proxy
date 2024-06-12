package oo2.patrones.practico.proxy.main;

import oo2.patrones.practico.proxy.modelo.Persona;
import oo2.patrones.practico.proxy.modelo.Telefono;

import java.io.IOException;
import java.util.Properties;

// La clase Main NO se toca!
public class Main {

	public static void main(String args[]) {

		PersonaDao dao = new PersonaDao();
		Persona p = dao.personaPorId(1);
		System.out.println(p.nombre());
		for (Telefono telefono : p.telefonos()) {
			System.out.println(telefono);
		}
	}
}

// Una clase wrapper para que lo use la clase Main.
class PersonaDao {
	private final oo2.patrones.practico.proxy.db.PersonaDao dao;

	PersonaDao() {
		// Carga propiedades desde un archivo
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			throw new RuntimeException("No se pudo leer el archivo de propiedades.", e);
		}

		String subprotocol = prop.getProperty("jdbc.subprotocol");
		String subname = prop.getProperty("jdbc.subname");
		String user = prop.getProperty("jdbc.user"), password = prop.getProperty("jdbc.password");

		this.dao = new oo2.patrones.practico.proxy.db.PersonaDao(subprotocol, subname, user, password);
	}

	// Métodos a implementar de la otra clase PersonaDao:

	public Persona personaPorId(int id) {
		return dao.personaPorId(id);
	}
}