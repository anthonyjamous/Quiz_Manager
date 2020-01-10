package fr.epita.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import fr.epita.configuration.Config;

public class ConnectionDB {

	/**
	 * open
	 * @return Statement
	 */
	public static Statement open(){
		Connection con = null;
		Statement statement = null;
		try{
			con=DriverManager.getConnection(Config.URL_CONNECTION);
			statement = con.createStatement();
		}
		catch(Exception err){
			System.out.println(err);
		}
		return statement;
	}

	/**
	 * close
	 * @param con
	 * @param statement
	 * @param rs
	 * @throws SQLException
	 */
	public static void close(Connection con,Statement statement,ResultSet rs) throws SQLException{
		if(null != con) {
			// cleanup resources, once after processing
			if(rs!=null) {
				rs.close();
			}

			statement.close();


			// and then finally close connection
			con.close();
		}
	}

}