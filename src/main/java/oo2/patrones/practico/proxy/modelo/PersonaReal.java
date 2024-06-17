package oo2.patrones.practico.proxy.modelo;

import java.util.Set;

public class PersonaReal implements Persona {
	private int id;
	private String nombre;
	private Set<Telefono> telefonos;

	public PersonaReal(int id, String nombre, Set<Telefono> telefonos) {
		this.id = id;
		this.nombre = nombre;
		this.telefonos = telefonos;
	}

	public Telefono[] telefonos() {
		return telefonos.toArray(new Telefono[telefonos.size()]);
	}

	public String nombre() {
		return nombre;
	}
}
