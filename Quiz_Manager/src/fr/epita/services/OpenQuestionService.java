package fr.epita.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.epita.configuration.Config;
import fr.epita.connection.ConnectionDB;
import fr.epita.models.OpenQuestion;

public class OpenQuestionService {


	/**
	 * createQuestion
	 * @param openQuestion
	 * @throws SQLException
	 */
	public static void createQuestion(OpenQuestion openQuestion) throws SQLException {

		Statement st=ConnectionDB.open(); //open the conx

		String query = "insert into OPENQUESTION (question,difficulty,hint) values (\""+openQuestion.getQuestion()+"\","+openQuestion.getDifficulty()+",\""+openQuestion.getHint()+"\")";

		Config.displayQueriesInConsole(query);
		st.execute(query); 

		ConnectionDB.close(st.getConnection(), st, null); //close conx


		//fetch the id of the question created to pass it :
		Integer questionId=getQuestion(openQuestion);
		openQuestion.setQuestionId(questionId);

		createTopics(openQuestion);  //create topics

	}

	/**
	 * createTopics
	 * @param openQuestion
	 * @throws SQLException
	 */
	private static void createTopics(OpenQuestion openQuestion) throws SQLException {

		String[] topics=openQuestion.getTopics();
		for (String topic : topics) {
			Statement st=ConnectionDB.open(); //open the conx
			String query = "insert into TOPIC_OPENQUESTION (topic,openQuestionId) values (\""+topic+"\","+openQuestion.getQuestionId()+")";
			Config.displayQueriesInConsole(query);
			st.execute(query); 
			ConnectionDB.close(st.getConnection(), st, null); //close conx
		}
	}



	/**
	 * getQuestion
	 * @param mcqQuestion
	 * @return open question id
	 * @throws SQLException
	 */
	public static Integer getQuestion(OpenQuestion mcqQuestion) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "select * from OPENQUESTION where question=\""+mcqQuestion.getQuestion()+"\"";
		Config.displayQueriesInConsole(query);
		ResultSet rs=st.executeQuery(query); 
		Integer id = null;
		while (rs.next()) {
			id =rs.getInt("openQuestionId");
		}

		ConnectionDB.close(st.getConnection(), st, rs); //close conx

		return id;

	}

	/**
	 * getAllQuestions
	 * @return ArrayList<OpenQuestion>
	 * @throws SQLException
	 */
	public static ArrayList<OpenQuestion> getAllQuestions() throws SQLException {
		ArrayList<OpenQuestion> lstQuestions=new ArrayList<OpenQuestion>();
		Statement st=ConnectionDB.open(); //open the conx
		String query = "select * from OPENQUESTION";
		Config.displayQueriesInConsole(query);
		ResultSet rs=st.executeQuery(query); 
		Integer id = null;
		String question;
		while (rs.next()) {
			id =rs.getInt("openQuestionId");
			question=rs.getString("question");
			OpenQuestion qst=new OpenQuestion(question, id);
			lstQuestions.add(qst);
		}
		ConnectionDB.close(st.getConnection(), st, rs); //close conx
		return lstQuestions;
	}

	/**
	 * deleteQuestion
	 * @param questionId
	 * @throws SQLException
	 */
	public static void deleteQuestion(int questionId) throws SQLException {
		if(questionId!=0) {
			deleteQuestionTopics(questionId);
			deleteQuizOpenQuestion(questionId);
			deleteAnswers(questionId);
			deleteQuestions(questionId);
		}

	}


	/**
	 * deleteQuestionDetails
	 * @param questionId
	 * @throws SQLException
	 */
	public static void deleteQuestionDetails(int questionId) throws SQLException {
		if(questionId!=0) {
			deleteQuestionTopics(questionId);
			deleteQuizOpenQuestion(questionId);
			deleteAnswers(questionId);
		}

	}


	/**
	 * deleteAnswers
	 * @param questionId
	 * @throws SQLException
	 */
	public static void deleteAnswers(int questionId) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from ANSWER where questionId="+questionId ;
		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx

	}

	/**
	 * deleteQuestions
	 * @param questionId
	 * @throws SQLException
	 */
	public static void deleteQuestions(int questionId) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from OPENQUESTION where openQuestionId="+questionId;
		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx
	}


	/**
	 * deleteQuizOpenQuestion
	 * @param questionId
	 * @throws SQLException
	 */
	public static void deleteQuizOpenQuestion(int questionId) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from QUIZ_OPENQUESTION where openQuestionId="+questionId;
		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx
	}

	/**
	 * deleteQuestionTopics
	 * @param questionId
	 * @throws SQLException
	 */
	public static void deleteQuestionTopics(int questionId) throws SQLException {
		Statement st=ConnectionDB.open(); //open the conx
		String query = "delete from TOPIC_OPENQUESTION where openQuestionId="+questionId;
		Config.displayQueriesInConsole(query);
		st.execute(query); 
		ConnectionDB.close(st.getConnection(), st, null); //close conx
	}

	/**
	 * getQuestionsByTopic
	 * @param topic
	 * @return ArrayList<OpenQuestion>
	 * @throws SQLException
	 */
	public static ArrayList<OpenQuestion> getQuestionsByTopic(String topic) throws SQLException {
		ArrayList<OpenQuestion> lstQuestions=new ArrayList<OpenQuestion>();
		Statement st=ConnectionDB.open(); //open the conx

		String query = "select * from OPENQUESTION where openQuestionId in (select openQuestionId from TOPIC_OPENQUESTION where topic=\""+topic+"\")";
		Config.displayQueriesInConsole(query);
		ResultSet rs=st.executeQuery(query); 
		Integer id = null;
		String question;
		while (rs.next()) {
			id =rs.getInt("openQuestionId");
			question=rs.getString("question");
			OpenQuestion qst=new OpenQuestion(question, id);
			lstQuestions.add(qst);
		}
		ConnectionDB.close(st.getConnection(), st, rs); //close conx
		return lstQuestions;
	}

	/**
	 * updateQuestion
	 * @param openQuestion
	 * @throws SQLException
	 */
	public static void updateQuestion(OpenQuestion openQuestion) throws SQLException {

		Statement st=ConnectionDB.open(); //open the conx

		String query = "update OPENQUESTION set question=\""+openQuestion.getQuestion()+"\",difficulty="+openQuestion.getDifficulty()+ ",hint=\""+openQuestion.getHint()+"\" where openQuestionId= "+openQuestion.getQuestionId();

		Config.displayQueriesInConsole(query);
		st.execute(query); 

		ConnectionDB.close(st.getConnection(), st, null); //close conx

		createTopics(openQuestion);  //create topics


	}




}
