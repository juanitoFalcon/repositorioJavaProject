package org.service.crud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.WebMethod;
import javax.jws.WebService;

/*
 * WEB SERVICE SOAP:
 * 
 * WSDL: WEB SERVICE DESCRPTION LANGUAJE, ES EL LENGUAJE ESPECIFICADO DEL WEB SERVICE.
 * 
 * UDDI: UNIVERSAL DESCRPTION AND DISCVERY INTEGRATION, PERMITE BUSCAR Y DESCUBRIR WEB SERVICES (PUBLIC, PRIVATE)
 * 
 * SOAP: SIMPLE OBJECT ACCESS PROTOCOL, PROTOCOLO DE DATOS QUE EMPLEA EL WEB ERVICE SOPA
 * ES DEL TIPO XML
 * 
 * ...........................................................................................................
 * 
 * REGLAS DE CREACIÓN DE WEB SERVICE SOAP
 * 
 * 1.- TODOS LOS METODOS DEBEN COMANZAR CON MÍNUSCULA, Y NO SE PUEDEN REESTRUCTURAR.
 * 
 * 2.- AGREGAR LAS ANOTACIONES @WebService, @WebMthod
 * 
 * ............................................................................................................
 * 
 * SI SE DESEA HACER ALGUN CAMBIO, UNA VEZ CREADO EL WEB SERVICE.
 * 
 * 1.- STOP SERVER APACHE...
 * 
 * 2.- ADD AND REMOVE - REMOVER EL PROYECTO DEL SERVIDOS Y LIMPIAR CLEAN
 * 
 * 3.- WEBCONTENT - WSDL (ELIMINAR LA CARPETA)
 * 
 * 4.- VOLVER A CREAR EL WEB SERVICE SOAP
 * 
 * ............................................................................................................
 * 
 * 											REMOTAMENTE
 * WEB SERVICE SOAP: WEB SERVICE SERVIDOR------------------> WEB SERVICE CLIENTE
 * 					CLASS JAVA SERVICE							JSP´S
 * NOTA: ***IMPORTANTE GUARDAR LA CLASE ANTES DE GENERAR EL WEB SERVICE SOAP***
 * 
 */

@WebService
public class ServiceCrudPersona {

	static Connection connection = null;
	static String driver = "oracle.jdbc.driver.OracleDriver";
	static String URL = "jdbc:Oracle:thin:@localhost:1521:orcl";
	
	@WebMethod
	public static void connectDataBaseOracle()throws IOException, SQLException{
		try {
			Class.forName(driver).newInstance();
			System.out.println("CARGO DRIVER ojdbc6.jar");
		} catch (Exception e) {
			System.out.println("Exception driver: " + e.getMessage());
		}
		try {
			connection = DriverManager.getConnection(URL, "System", "12345");
			System.out.println("CONEXION EXITOSA: Oracle11g");
		} catch (Exception e) {
			System.out.println("Exception connection: " + e.getMessage());
		}
	}
	
	@WebMethod
	public static String altaPErsona(int id, String nombre, String apepat, String tel) throws SQLException,IOException{
		try {
			connectDataBaseOracle();
			
			String sql = "INSERT INTO personabatch (ID, NOMBRE, APEPAT, TEL) VALUES (?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, nombre);
			ps.setString(3, apepat);
			ps.setString(4, tel);
			ps.execute();
					
		} catch (Exception e) {
			System.out.println("Exceptio: " + e.getMessage());
		}
		return "SE REALIZO CORRECTAMENTE EL INSERT";
	}
	
	@WebMethod
	public static String modificarPersona(String Nombre, String Apepat, String Tel, int id) throws SQLException, IOException{
		try {
			connectDataBaseOracle();
			String sql = "UPDATE PERSONABATCH SET NOMBRE = ?, APEPAT = ?, TEL = ? WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, Nombre);
			ps.setString(2, Apepat);
			ps.setString(3, Tel);
			ps.setInt(4, id);
			ps.execute();
		} catch (Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		return "SE REALIZO CORRECTAMENTE LA MODIFICACIÓN";
	}
	
	@WebMethod
	public static String eliminarPersona (int id) {
		try {
			connectDataBaseOracle();
			String sql = "DELETE FROM PERSONABATCH WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		return "SE ELIMINO CORRECTAMENTE EL ID: " + id;
	}
	
	@WebMethod
	public static String consultaIndividualPersona(int id) throws SQLException, IOException{
		String nombre = null;
		String apepat = null;
		String tel = null;
		try {
			connectDataBaseOracle();
			String sql = "SELECT * FROM PERSONABATCH WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				nombre = resultSet.getString("nombre");
				apepat = resultSet.getString("apepat");
				tel = resultSet.getString("tel");
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		return "CNSULTA" + id + ", " + nombre + ", " + apepat + ", " + tel;
	}
	
}
