package fr.epita.models;

public class Answer {

	private String text;
	private Student student;
	private Quiz quiz;
	private OpenQuestion question;
	private Integer answerId;
	

	
	public Answer(String text, Student student, Quiz quiz, OpenQuestion question, Integer answerId) {
		this.text = text;
		this.student = student;
		this.quiz = quiz;
		this.question = question;
		this.answerId = answerId;
	}
	
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(OpenQuestion question) {
		this.question = question;
	}
	
	
}
