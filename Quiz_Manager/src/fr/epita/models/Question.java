package fr.epita.models;

public class Question {

	private String question;
	private String[] topics;
	private Integer difficulty;
	private Integer questionId;





	public Question(String question, String[] topics, Integer difficulty, Integer questionId) {
		this.question = question;
		this.topics = topics;
		this.difficulty = difficulty;
		this.questionId = questionId;
	}
	
	public Question(String question, String[] topics, Integer difficulty) {
		this.question = question;
		this.topics = topics;
		this.difficulty = difficulty;
	}
	
	public Question(String question, Integer questionId) {
		this.question = question;
		this.questionId = questionId;
	}

	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String[] getTopics() {
		return topics;
	}
	public void setTopics(String[] topics) {
		this.topics = topics;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}




}
