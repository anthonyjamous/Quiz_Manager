package fr.epita.models;

public class OpenQuestion extends Question{

	
	private String hint;
	
	
	public OpenQuestion(String question, String[] topics, Integer difficulty, Integer questionId,String hint) {
		super(question, topics, difficulty, questionId);
		this.hint=hint;
	}

	

	public OpenQuestion(String question, String[] topics, Integer difficulty,String hint) {
		super(question, topics, difficulty);
		this.hint=hint;
	}

	public OpenQuestion(String question, Integer questionId) {
		super(question, questionId);
	}



	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}



}
