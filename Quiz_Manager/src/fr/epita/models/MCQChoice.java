package fr.epita.models;

public class MCQChoice {

	private String choice;
	private boolean valid;
	private MCQQuestion question;
	private Integer MCQChoiceId;
	
	
	
	public MCQChoice(String choice, boolean valid, MCQQuestion question, Integer mCQChoiceId) {
		this.choice = choice;
		this.valid = valid;
		this.question = question;
		MCQChoiceId = mCQChoiceId;
	}
	
	
	public MCQChoice(String choice, boolean valid, MCQQuestion question) {
		super();
		this.choice = choice;
		this.valid = valid;
		this.question = question;
	}
	
	
	
	public Integer getMCQChoiceId() {
		return MCQChoiceId;
	}
	public void setMCQChoiceId(Integer mCQChoiceId) {
		MCQChoiceId = mCQChoiceId;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public boolean getValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public MCQQuestion getQuestion() {
		return question;
	}

	public void setQuestion(MCQQuestion question) {
		this.question = question;
	}
	
	
}
