package fr.epita.services;

import java.sql.SQLException;
import java.sql.Statement;

import fr.epita.configuration.Config;
import fr.epita.connection.ConnectionDB;
import fr.epita.models.MCQChoice;

public class MCQChoiceService {



	/**
	 * createMCQChoice
	 * @param choice
	 * @throws SQLException
	 */
	public static void createMCQChoice(MCQChoice choice) throws SQLException {

		Statement st=ConnectionDB.open(); //open the conx

		String query = "insert into MCQCHOICE (choice,valid,mcqQuestionId) values (\""+choice.getChoice()+"\","+choice.getValid()+","+choice.getQuestion().getQuestionId()+")";
		Config.displayQueriesInConsole(query);
		st.execute(query); 

		ConnectionDB.close(st.getConnection(), st, null); //close conx

	}


	/**
	 * deleteMcqChoice
	 * @param questionId
	 * @throws SQLException
	 */
	public static void deleteMcqChoices(int questionId) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from MCQCHOICE where mcqQuestionId="+questionId;
		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx

	}


}
