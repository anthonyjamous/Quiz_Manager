package Main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import fr.epita.models.MCQChoice;
import fr.epita.models.MCQQuestion;
import fr.epita.models.OpenQuestion;
import fr.epita.models.Quiz;
import fr.epita.services.MCQChoiceService;
import fr.epita.services.MCQQuestionService;
import fr.epita.services.OpenQuestionService;
import fr.epita.services.QuizService;



public class Main {

	public static void main(String[] args) throws SQLException {

		Scanner sc;
		boolean wrongFormat = true;

		while(wrongFormat) {
			System.out.println("");
			System.out.println("Please choose one of the options: ");
			System.out.println("1- Open questions");
			System.out.println("2- MCQ Questions");
			System.out.println("3- Quiz");

			sc=new Scanner(System.in);
			String input=sc.nextLine();
			switch (input) {
			case "1": case "2":  // CRUD for the open questions and MCQ questions
				boolean wrongQuestionFormat = true;
				while (wrongQuestionFormat) {
					System.out.println("Please choose one of the operations: ");
					System.out.println("C- Create");
					System.out.println("R- Read");
					System.out.println("U- Update");
					System.out.println("D- Delete");
					System.out.println("S- Search");

					sc=new Scanner(System.in);
					String questionInput=sc.nextLine();
					switch (questionInput) {
					case "C": //create  question
						createOrUpdateQuestion(input,0);
						wrongQuestionFormat=false;
						break;
					case "R": case "U" : case "D": //read all  questions , update and delete
						//first we display all questions for the three operations
						ArrayList<MCQQuestion> lstMCQQuestions;
						ArrayList<OpenQuestion> lstOpenQuestions;
						ArrayList<Integer> mcqLstID; // list that contains all the mcq  ids to be able to compare with input
						ArrayList<Integer> openQuestionLstID;// list that contains all the open questions  ids to be able to compare with input
						if("2".equals(input)) { //specific to mcqquestion
							lstMCQQuestions=MCQQuestionService.getAllQuestions();
							mcqLstID=new ArrayList<Integer>();

							if(lstMCQQuestions.size()!=0) {
								for (MCQQuestion mcqQuestion : lstMCQQuestions) {
									System.out.println("ID: "+mcqQuestion.getQuestionId()+"| question : "+mcqQuestion.getQuestion());
									mcqLstID.add(mcqQuestion.getQuestionId());
								}
							}else {
								System.out.println("No questions to display");
								continue;
							}
							if("U".equals(questionInput)) {
								System.out.println("Choose the id of the question you would like to update:");
								int id=sc.nextInt();
								createOrUpdateQuestion(input,id);
							}else if("D".equals(questionInput)) {
								System.out.println("Choose the id of the question you would like to delete:");
								int id=sc.nextInt();
								MCQQuestionService.deleteQuestion(id); //delete all topics related to this question,all the questions existing in a quiz, all the answers to this question, and it's choices, and the questions
							}
						}else { //specific to open questions
							lstOpenQuestions=OpenQuestionService.getAllQuestions();
							openQuestionLstID=new ArrayList<Integer>();

							if(lstOpenQuestions.size()!=0) {
								for (OpenQuestion openQuestion : lstOpenQuestions) {
									System.out.println("ID: "+openQuestion.getQuestionId()+"| question : "+openQuestion.getQuestion());
									openQuestionLstID.add(openQuestion.getQuestionId());
								}
							}else {
								System.out.println("No questions to display");
								continue;
							}
							if("U".equals(questionInput)) {
								System.out.println("Choose the id of the question you would like to update:");
								int id=sc.nextInt();
								createOrUpdateQuestion(input,id);
							}else if("D".equals(questionInput)) {
								System.out.println("Choose the id of the question you would like to delete:");
								int id=sc.nextInt();

								OpenQuestionService.deleteQuestion(id);//delete all topics related to this question,all the questions existing in a quiz, all the answers to this question, and the questions
							}
						}
						wrongQuestionFormat=false;
						break;

					case "S": //search
						System.out.print("Type the topic:");
						String topic=sc.next();
						if("2".equals(input)) { //search for the mcqquestion

							ArrayList<MCQQuestion>lstQuestions=MCQQuestionService.getQuestionsByTopic(topic);


							if(lstQuestions.size()==0) {
								System.out.println("Nothing found!");
								continue;
							}
							for (MCQQuestion quest : lstQuestions) {
								System.out.println("ID: "+quest.getQuestionId()+"| question : "+quest.getQuestion());
							}
						}else { //search for the openquestion
							ArrayList<OpenQuestion>lstQuestions=OpenQuestionService.getQuestionsByTopic(topic);

							if(lstQuestions.size()==0) {
								System.out.println("Nothing found!");
								continue;
							}

							for (OpenQuestion quest : lstQuestions) {
								System.out.println("ID: "+quest.getQuestionId()+"| question : "+quest.getQuestion());
							}
						}
						wrongQuestionFormat=false;
						break;
					default:
						System.out.println("Wrong Choice!Try Again: ");
						wrongQuestionFormat=true;
						break;
					}
				}
				break;

			case "3": // quiz
				boolean wrongInput=true;
				do {
					System.out.println("Please choose one of the operations: ");
					System.out.println("C- Create"); // to create a quiz
					System.out.println("R- Read"); // to create a quiz
					System.out.println("D- Delete"); // to delete a quiz
					sc=new Scanner(System.in);

					String inp=sc.nextLine();
					switch (inp) {
					case "C": //create  quiz
						System.out.print("Choose a quiz name: ");

						String quiz=sc.nextLine();
						ArrayList<Integer> lstMCQID=new ArrayList<Integer>();
						ArrayList<Integer> lstOpenID=new ArrayList<Integer>();
						ArrayList<MCQQuestion> lstMCQQuestions;
						ArrayList<OpenQuestion> lstOpenQuestions;
						lstMCQQuestions=MCQQuestionService.getAllQuestions();
						int id=-1;
						if(lstMCQQuestions.size()!=0) {
							for (MCQQuestion mcqQuestion : lstMCQQuestions) {
								System.out.println("ID: "+mcqQuestion.getQuestionId()+"| question : "+mcqQuestion.getQuestion());
							}
							System.out.println("Type all the mcq questions's ids you want to include in this quiz: ");

							do {
								System.out.print("Question id (0 to stop):");
								id=sc.nextInt();

								if(id!=0) {
									lstMCQID.add(id);
								}
							}while(id!=0);
						}else {
							System.out.println("No  mcq questions to display");
							System.out.println("");

						}

						lstOpenQuestions=OpenQuestionService.getAllQuestions();
						if(lstOpenQuestions.size()!=0) {
							for (OpenQuestion openQuestion : lstOpenQuestions) {
								System.out.println("ID: "+openQuestion.getQuestionId()+"| question : "+openQuestion.getQuestion());
							}

							id=-1;
							System.out.println("Type all the open questions's ids you want to include in this quiz: ");
							do {
								System.out.println("Question id (0 to stop):");
								id=sc.nextInt();
								if(id!=0) {
									lstOpenID.add(id);
								}
							}while(id!=0);
						}else {
							System.out.print("No open questions to display");
							System.out.println("");
						}

						//create the quiz
						QuizService.createQuiz(lstMCQID,lstOpenID,quiz);
						wrongInput=false;
						break;
					case "D": case "R":// delete  and read quiz
						ArrayList<Quiz> lstQuiz=QuizService.getAllQuiz();

						if(lstQuiz.size()==0) {
							System.out.println("No available quiz");
							continue;
						}
						for (Quiz q : lstQuiz) {
							System.out.println("ID:"+q.getQuizId()+" | quiz title: "+q.getTitle());
						}
						if("D".equals(inp)) {
							System.out.println("Choose the quiz id you want to delete: ");
							int quizId=sc.nextInt();
							QuizService.deleteQuiz(quizId);
						}

						wrongInput=false;
						break;
					default:
						System.out.println("Wrong choice");
						wrongInput=true;
						break;
					}
				}while(wrongInput);
				break;
			default:
				System.out.println("Wrong Choice!Try Again: ");
				break;
			}
		}
	}
	private static void createOrUpdateQuestion(String input, int id) throws SQLException {
		Scanner sc=new Scanner(System.in);
		System.out.print("Question:");
		String question=sc.nextLine();
		System.out.print("Difficulty:");
		int difficulty=sc.nextInt();
		boolean isNotZero=true;
		int topicIndex=1;
		ArrayList<String> lstTopic=new ArrayList<String>();
		do {
			System.out.print("Topic "+topicIndex+" (0 to stop): ");
			topicIndex++;
			String topic=sc.next();
			if("0".equals(topic)) {
				isNotZero=false;
				continue;
			}else {
				lstTopic.add(topic);
			}
		} while (isNotZero);

		String[] lstTopics = null;

		lstTopics=new String[lstTopic.size()];
		for (int i = 0; i < lstTopic.size(); i++) {
			lstTopics[i]=lstTopic.get(i);
		}

		if("1".equals(input)) { //create open question

			System.out.print("Hint: ");
			sc.nextLine();
			String hint=sc.nextLine();

			OpenQuestion openQuestion=new OpenQuestion(question, lstTopics, difficulty, hint);

			if(id==0) {//create
				OpenQuestionService.createQuestion(openQuestion);  //CALL the createQuestion method
			}else { //update
				openQuestion.setQuestionId(id);
				OpenQuestionService.deleteQuestionDetails(id);
				OpenQuestionService.updateQuestion(openQuestion);  //CALL the createQuestion method
			}
		}else { //create MCQ question
			MCQQuestion mcqQuestion=new MCQQuestion(question, lstTopics, difficulty);
			if(id==0) {//create
				MCQQuestionService.createQuestion(mcqQuestion);  //CALL the createQuestion method
			}else { //update
				mcqQuestion.setQuestionId(id);
				MCQQuestionService.deleteQuestionDetails(id);
				MCQQuestionService.updateQuestion(mcqQuestion); //CALL the createQuestion method
			}
			//fetch the id of the question created to pass it to the choice:
			Integer questionId=MCQQuestionService.getQuestion(mcqQuestion);
			mcqQuestion.setQuestionId(questionId);
			//create choices
			System.out.println("Type the choices for this questions ( 0 to stop): ");
			HashMap<String, Boolean> choices=new HashMap<String, Boolean>();
			int choiceNumber=1;
			boolean inputNotEmpty=true;
			do {
				System.out.print("Choice "+choiceNumber +" (0 to stop): ");
				sc=new Scanner(System.in);
				String choice=sc.nextLine();

				if ("0".equals(choice)) {
					inputNotEmpty=false;
					continue;
				}
				boolean wrongValidityFormat=false;
				do {

					System.out.println("Validity(y for valid, n for not valid): ");
					String validity=sc.nextLine();

					if(!"y".equals(validity) && !"n".equals(validity)) {
						wrongValidityFormat=true;
						System.out.println("Please put a valid input");
						continue;
					}
					wrongValidityFormat=false;
					boolean valid = false;
					if("y".equals(validity)) {
						valid=true;
					}else if("n".equals(validity)) {
						valid=false;
					}
					choices.put(choice, valid);
				}while(wrongValidityFormat);

				choiceNumber++;
			}while(inputNotEmpty);

			choices.forEach((k, v) -> {
				MCQChoice choice=new MCQChoice(k, v, mcqQuestion);

				try {
					MCQChoiceService.createMCQChoice(choice);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
	}
}
