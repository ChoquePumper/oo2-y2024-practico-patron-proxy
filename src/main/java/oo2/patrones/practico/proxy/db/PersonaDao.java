package oo2.patrones.practico.proxy.db;

import oo2.patrones.practico.proxy.modelo.Persona;
import oo2.patrones.practico.proxy.modelo.Telefono;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PersonaDao extends ObjetoJDBC {

	public PersonaDao(String subprotocolo, String subnombre, String user, String password) {
		super(subprotocolo, subnombre, user, password);
	}

	private Connection obtenerConexion() throws SQLException {
		return super.getConnection();
	}

	public Persona personaPorId(int id) {
		String sql = "select p.nombre "
				+ "from personas p "
				+ "where p.id = ?";
		try (Connection conn = obtenerConexion();
		     PreparedStatement statement =
				     conn.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			Set<Telefono> telefonos = new HashSet<Telefono>();
			String nombrePersona = null;
			while (result.next()) {
				nombrePersona = result.getString(1);
			}

			// Devolver un objeto proxy
			return new PersonaProxy(id, nombrePersona, () -> verTelefonosAsociados(id));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Set<Telefono> verTelefonosAsociados(int id) {
		String sql = "select t.numero "
				+ "from personas p, telefonos t "
				+ "where p.id = t.idpersona and p.id = ?";
		try (Connection conn = obtenerConexion();
		     PreparedStatement statement =
				     conn.prepareStatement(sql);) {
			setearDatos(statement, id);
			ResultSet result = statement.executeQuery();

			Set<Telefono> telefonos = new HashSet<Telefono>();
			while (result.next()) {
				telefonos.add(new Telefono(result.getString(1)));
			}
			return telefonos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
