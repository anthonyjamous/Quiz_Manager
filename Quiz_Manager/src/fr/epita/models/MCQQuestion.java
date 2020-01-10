package fr.epita.models;

public class MCQQuestion extends Question{

	public MCQQuestion(String question, String[] topics, Integer difficulty, Integer questionId) {
		super(question, topics, difficulty, questionId);
		// TODO Auto-generated constructor stub
	}

	
	public MCQQuestion(String question, String[] topics, Integer difficulty) {
		super(question, topics, difficulty);
		// TODO Auto-generated constructor stub
	}


	public MCQQuestion(String question, Integer questionId) {
		super(question,questionId);
	}


}
