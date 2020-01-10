package fr.epita.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.epita.configuration.Config;
import fr.epita.connection.ConnectionDB;
import fr.epita.models.Quiz;

public class QuizService {


	/**
	 * createQuiz
	 * @param lstMCQID
	 * @param lstOpenID
	 * @param quiz
	 * @throws SQLException
	 */
	public static void createQuiz(ArrayList<Integer> lstMCQID, ArrayList<Integer> lstOpenID, String quiz) throws SQLException {

		Statement st=ConnectionDB.open(); //open the conx

		String query = "insert into QUIZ (title) values (\""+quiz+"\")";

		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx

		Integer quizId=getQuizId(quiz);
		createQuizOpenQuestion(quizId,lstOpenID);
		createQuizMCQQuestion(quizId,lstMCQID);

	}

	/**
	 * createQuizMCQQuestion
	 * @param quizId
	 * @param lstMCQID
	 * @throws SQLException
	 */
	public static void createQuizMCQQuestion(Integer quizId, ArrayList<Integer> lstMCQID) throws SQLException {

		for (Integer id : lstMCQID) {
			Statement st=ConnectionDB.open(); //open the conx

			String query = "insert into QUIZ_MCQQUESTION (quizId,mcqQuestionId) values ("+quizId+","+id+")";

			Config.displayQueriesInConsole(query);
			st.execute(query); 
			ConnectionDB.close(st.getConnection(), st, null); //close conx
		}

	}

	/**
	 * createQuizOpenQuestion
	 * @param quizId
	 * @param lstOpenID
	 * @throws SQLException
	 */
	public static void createQuizOpenQuestion(Integer quizId, ArrayList<Integer> lstOpenID) throws SQLException {
		for (Integer id : lstOpenID) {
			Statement st=ConnectionDB.open(); //open the conx

			String query = "insert into QUIZ_OPENQUESTION (quizId,openQuestionId) values ("+quizId+","+id+")";

			Config.displayQueriesInConsole(query);
			st.execute(query); 
			ConnectionDB.close(st.getConnection(), st, null); //close conx
		}
	}

	/**
	 * getQuizId
	 * @param quiz
	 * @return
	 * @throws SQLException
	 */
	public static Integer getQuizId(String quiz) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "select * from QUIZ where title=\""+quiz+"\"";
		Config.displayQueriesInConsole(query);
		ResultSet rs=st.executeQuery(query); 
		Integer id = null;
		while (rs.next()) {
			id =rs.getInt("quizId");
		}
		ConnectionDB.close(st.getConnection(), st, rs); //close conx
		return id;
	}

	/**
	 * getAllQuiz
	 * @return ArrayList<Quiz>
	 * @throws SQLException
	 */
	public static ArrayList<Quiz> getAllQuiz() throws SQLException {
		ArrayList<Quiz> lstQuiz=new ArrayList<Quiz>();

		Statement st=ConnectionDB.open(); //open the conx

		String query = "select * from QUIZ";
		Config.displayQueriesInConsole(query);
		ResultSet rs=st.executeQuery(query); 
		Integer id = null;
		String title;
		while (rs.next()) {
			id =rs.getInt("quizId");
			title=rs.getString("title");
			Quiz q=new Quiz(title, id);
			lstQuiz.add(q);
		}
		ConnectionDB.close(st.getConnection(), st, rs); //close conx

		return lstQuiz;
	}

	/**
	 * deleteQuiz
	 * @param quizId
	 * @throws SQLException
	 */
	public static void deleteQuiz(int quizId) throws SQLException {
		deleteQuizOpenQuestions(quizId);
		deleteQuizMCQQuestions(quizId);



		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from QUIZ where quizId="+quizId;
		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx


	}

	/**
	 * deleteQuizMCQQuestions
	 * @param quizId
	 * @throws SQLException
	 */
	private static void deleteQuizMCQQuestions(int quizId) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from QUIZ_MCQQUESTION where quizId="+quizId;
		Config.displayQueriesInConsole(query);
		st.execute(query); 
	}

	/**
	 * deleteQuizOpenQuestions
	 * @param quizId
	 * @throws SQLException
	 */
	private static void deleteQuizOpenQuestions(int quizId) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from QUIZ_OPENQUESTION where quizId="+quizId;
		Config.displayQueriesInConsole(query);
		st.execute(query); 

	}

}
