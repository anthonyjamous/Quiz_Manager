package fr.epita.models;

public class Quiz {

	private String title;
	private Integer quizId;

	

	public Integer getQuizId() {
		return quizId;
	}

	public void setQuizId(Integer quizId) {
		this.quizId = quizId;
	}

	public Quiz(String title, Integer quizId) {
		this.title = title;
		this.quizId = quizId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
