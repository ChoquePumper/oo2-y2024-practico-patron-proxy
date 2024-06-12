package oo2.patrones.practico.proxy.db;

import oo2.patrones.practico.proxy.modelo.Persona;
import oo2.patrones.practico.proxy.modelo.Telefono;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PersonaDao {

	private Connection obtenerConexion() {
		//Utilice aquí su motor de BD preferido
	}

	public Persona personaPorId(int id) {
		String sql = "select p.nombre,t.numero "
				+ "from personas p, telefonos t "
				+ "where p.id = t.idpersona and p.id = ?";
		try (Connection conn = obtenerConexion();
		     PreparedStatement statement =
				     conn.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			Set<Telefono> telefonos = new HashSet<Telefono>();
			String nombrePersona = null;
			while (result.next()) {
				nombrePersona = result.getString(1);
				telefonos.add(new Telefono(result.getString(2)));
			}
			return new Persona(id, nombrePersona, telefonos);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}