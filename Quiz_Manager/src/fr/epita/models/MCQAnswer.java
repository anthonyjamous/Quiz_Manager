package fr.epita.models;

public class MCQAnswer {

	private Quiz quiz;
	private MCQChoice choice;
	private Student student;
	private Integer MCQAnswerId;
	

	
	public MCQAnswer(Quiz quiz, MCQChoice choice, Student student, Integer mCQAnswerId) {
		this.quiz = quiz;
		this.choice = choice;
		this.student = student;
		MCQAnswerId = mCQAnswerId;
	}
	
	public Integer getMCQAnswerId() {
		return MCQAnswerId;
	}
	public void setMCQAnswerId(Integer mCQAnswerId) {
		MCQAnswerId = mCQAnswerId;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	public MCQChoice getChoice() {
		return choice;
	}
	public void setChoice(MCQChoice choice) {
		this.choice = choice;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	
	
}
