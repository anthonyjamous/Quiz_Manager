package fr.epita.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.epita.configuration.Config;
import fr.epita.connection.ConnectionDB;
import fr.epita.models.MCQQuestion;

public class MCQQuestionService {

	/**
	 * createQuestion
	 * @param mcqQuestion
	 * @throws SQLException
	 */
	public static void createQuestion(MCQQuestion mcqQuestion) throws SQLException {

		Statement st=ConnectionDB.open(); //open the conx
		String query = "insert into MCQQUESTION (question,difficulty) values (\""+mcqQuestion.getQuestion()+"\","+mcqQuestion.getDifficulty()+")";
		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx

		//fetch the id of the question created to pass it :
		Integer questionId=getQuestion(mcqQuestion);
		mcqQuestion.setQuestionId(questionId);

		createTopics(mcqQuestion);  //create topics
	}

	/**
	 * createTopics
	 * @param mcqQuestion
	 * @throws SQLException
	 */
	public static void createTopics(MCQQuestion mcqQuestion) throws SQLException {
		String[] topics=mcqQuestion.getTopics();
		for (String topic : topics) {
			Statement st=ConnectionDB.open(); //open the conx

			String query = "insert into TOPIC_MCQQUESTION (topic,mcqQuestionId) values (\""+topic+"\","+mcqQuestion.getQuestionId()+")";

			Config.displayQueriesInConsole(query);
			st.execute(query); 
			ConnectionDB.close(st.getConnection(), st, null); //close conx
		}
	}


	/**
	 * getQuestion
	 * @param mcqQuestion
	 * @return mcq question id
	 * @throws SQLException
	 */
	public static Integer getQuestion(MCQQuestion mcqQuestion) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "select * from MCQQUESTION where question=\""+mcqQuestion.getQuestion()+"\"";
		Config.displayQueriesInConsole(query);
		ResultSet rs=st.executeQuery(query); 
		Integer id = null;
		while (rs.next()) {
			id =rs.getInt("mcqQuestionId");
		}
		ConnectionDB.close(st.getConnection(), st, rs); //close conx
		return id;
	}


	/**
	 * getAllQuestions
	 * @return  ArrayList<MCQQuestion>
	 * @throws SQLException
	 */
	public static ArrayList<MCQQuestion> getAllQuestions() throws SQLException {
		ArrayList<MCQQuestion> lstQuestions=new ArrayList<MCQQuestion>();
		Statement st=ConnectionDB.open(); //open the conx

		String query = "select * from MCQQUESTION";
		Config.displayQueriesInConsole(query);
		ResultSet rs=st.executeQuery(query); 
		Integer id = null;
		String question;
		while (rs.next()) {
			id =rs.getInt("mcqQuestionId");
			question=rs.getString("question");
			MCQQuestion qst=new MCQQuestion(question, id);
			lstQuestions.add(qst);
		}
		ConnectionDB.close(st.getConnection(), st, rs); //close conx
		return lstQuestions;
	}

	/**
	 * deleteQuetion
	 * @param questionId
	 * @throws SQLException
	 */
	public static void deleteQuestion(int questionId) throws SQLException {
		if(questionId!=0) {
			deleteQuestionTopics(questionId);
			deleteQuizMcqQuestion(questionId);
			deleteMcqAnswers(questionId);
			MCQChoiceService.deleteMcqChoices(questionId);
			deleteQuestions(questionId);
		}

	}


	public static void deleteQuestionDetails(int questionId) throws SQLException { //for the update
		if(questionId!=0) {
			deleteQuestionTopics(questionId);
			deleteQuizMcqQuestion(questionId);
			deleteMcqAnswers(questionId);
			MCQChoiceService.deleteMcqChoices(questionId);
		}

	}

	public static void deleteQuestions(int questionId) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from MCQQUESTION where mcqQuestionId="+questionId;
		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx
	}



	public static void deleteMcqAnswers(int questionId) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from MCQANSWER where mcqChoiceId in (select mcqChoiceId from MCQCHOICE where mcqQuestionId="+questionId+")" ;
		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx

	}

	public static void deleteQuizMcqQuestion(int questionId) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from QUIZ_MCQQUESTION where mcqQuestionId="+questionId;
		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx
	}

	public static void deleteQuestionTopics(int questionId) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from TOPIC_MCQQUESTION where mcqQuestionId="+questionId;
		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx
	}

	public static ArrayList<MCQQuestion> getQuestionsByTopic(String topic) throws SQLException {
		ArrayList<MCQQuestion> lstQuestions=new ArrayList<MCQQuestion>();
		Statement st=ConnectionDB.open(); //open the conx

		String query = "select * from MCQQUESTION where mcqQuestionId in (select mcqQuestionId from TOPIC_MCQQUESTION where topic=\""+topic+"\")";
		Config.displayQueriesInConsole(query);
		ResultSet rs=st.executeQuery(query); 
		Integer id = null;
		String question;
		while (rs.next()) {
			id =rs.getInt("mcqQuestionId");
			question=rs.getString("question");
			MCQQuestion qst=new MCQQuestion(question, id);
			lstQuestions.add(qst);
		}
		ConnectionDB.close(st.getConnection(), st, rs); //close conx
		return lstQuestions;
	}

	public static void updateQuestion(MCQQuestion mcqQuestion) throws SQLException {

		Statement st=ConnectionDB.open(); //open the conx

		String query = "update MCQQUESTION set question=\""+mcqQuestion.getQuestion()+"\",difficulty="+mcqQuestion.getDifficulty() +" where mcqQuestionId="+mcqQuestion.getQuestionId();

		Config.displayQueriesInConsole(query);
		st.execute(query); 

		ConnectionDB.close(st.getConnection(), st, null); //close conx


		createTopics(mcqQuestion);  //create topics



	}
}
