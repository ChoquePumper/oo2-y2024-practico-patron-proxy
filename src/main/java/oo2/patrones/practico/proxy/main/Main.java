package oo2.patrones.practico.proxy.main;

import oo2.patrones.practico.proxy.db.PersonaDao;
import oo2.patrones.practico.proxy.modelo.Persona;
import oo2.patrones.practico.proxy.modelo.Telefono;

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
