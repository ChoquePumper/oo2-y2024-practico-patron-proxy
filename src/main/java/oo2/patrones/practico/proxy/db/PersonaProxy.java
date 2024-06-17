package oo2.patrones.practico.proxy.db;

import oo2.patrones.practico.proxy.modelo.Persona;
import oo2.patrones.practico.proxy.modelo.PersonaReal;
import oo2.patrones.practico.proxy.modelo.Telefono;

import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class PersonaProxy implements Persona {
	private final Supplier<Set<Telefono>> methodTelefonos;
	private final int id;
	private PersonaReal real;

	// Para recrear el objeto real
	private Set<Telefono> telefonos = null;

	/**
	 * @param id
	 * @param nombre
	 * @param methodTelefonos función que recupera la lista de teléfonos.
	 */
	PersonaProxy(int id, String nombre, Supplier<Set<Telefono>> methodTelefonos) {
		this.id = id;
		this.real = new PersonaReal(id, nombre, null);
		this.methodTelefonos = methodTelefonos;
	}

	private void recrearReal() {
		this.real = new PersonaReal(id, real.nombre(), Objects.requireNonNull(telefonos));
	}

	@Override
	public Telefono[] telefonos() {
		if (this.telefonos == null) {
			this.telefonos = Set.copyOf(methodTelefonos.get());
			recrearReal();
		}
		return real.telefonos();
	}

	@Override
	public String nombre() {
		return real.nombre();
	}
}
