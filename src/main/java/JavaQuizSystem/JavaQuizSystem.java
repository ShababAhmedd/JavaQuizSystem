package JavaQuizSystem;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class JavaQuizSystem {

    private static final String USERS_FILE_PATH = "./src/main/resources/users.json";
    private static final String QUIZ_BANK_FILE_PATH = "./src/main/resources/quizBank.json";

    public static void main(String[] args) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("System:> Enter your username ");
        System.out.print("User:> ");
        String loggedUserName = sc.nextLine();
        System.out.println("System:> Enter password");
        System.out.print("User:> ");
        String loggedPassword = sc.nextLine();

        JSONArray userArray = readJSONFile(USERS_FILE_PATH);

        boolean loggedIn = false;
        for (Object user : userArray) {
            JSONObject userObject = (JSONObject) user;
            String storedUsername = (String) userObject.get("username");
            String storedPassword = (String) userObject.get("password");
            String role = (String) userObject.get("role");

            if (loggedUserName.equals(storedUsername) && loggedPassword.equals(storedPassword)) {
                loggedIn = true;
                if (role.equals("admin")) {
                    System.out.println("System:> Welcome " + loggedUserName + "! Please create new questions in the question bank.");
                    addQuestion();
                } else if (role.equals("student")) {
                    System.out.println("System:> Welcome " + loggedUserName + " to the quiz!");
                    takeQuiz(loggedUserName);
                }
                break;
            }
        }
        if (!loggedIn) {
            System.out.println("System:> Wrong Credentials");
        }
    }


    public static void addQuestion() throws IOException, ParseException {
        JSONArray quizBankArray = readJSONFile(QUIZ_BANK_FILE_PATH);

        boolean flag = false;
        while (true) {
            if (!flag) {
                System.out.println("System:> Do you want to add questions? (press s for start and q for quit)");
                System.out.print("Admin:> ");
            }
            flag = true;
            Scanner sc = new Scanner(System.in);
            String adminInput = sc.nextLine();
            if (adminInput.equalsIgnoreCase("q")) {
                System.out.println("System:> Process Terminated");
                break;
            } else if (!adminInput.equalsIgnoreCase("s")) {
                System.out.println("System:> Invalid input given. Try again!");
                System.out.print("User:> ");
                continue;
            }

            // Statement of the MCQ
            JSONObject qObject = new JSONObject();
            System.out.println("System:> Input your question");
            System.out.print("Admin:> ");
            String q = sc.nextLine();
            qObject.put("question", q);

            // MCQ options
            for (int i = 1; i <= 4; i++) {
                System.out.println("System: Input option " + i + ":");
                System.out.print("Admin:> ");
                String options = sc.nextLine();
                qObject.put("option " + i, options);
            }

            // answer key
            System.out.println("System: What is the answer key?");
            System.out.print("Admin:> ");
            // need to throw a exception if the anskey is outside the range.
            while (true) {
                try {
                    int ansKey = Integer.parseInt(sc.nextLine());
                    if (ansKey < 1 || ansKey > 4) {
                        System.out.println("System:> Invalid input given");
                        System.out.println("System:> Try again!");
                        System.out.print("Admin:> ");
                    } else {
                        quizBankArray.add(qObject);
                        System.out.println("System:> Saved successfully! Do you want to add more questions? (press s for start and q for quit)");
                        System.out.print("Admin:> ");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("System:> Invalid input given");
                    System.out.println("System:> Try again!");
                    System.out.print("Admin:> ");
//                    System.out.println(e);
                }
            }
        }
        writeJSONFile(QUIZ_BANK_FILE_PATH, quizBankArray);
    }


    public static void takeQuiz(String loggedUserName) throws IOException, ParseException {
        System.out.println("System:> We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready?");
        System.out.println("System:> Press 's' for start and 'q' to abort.");
        System.out.print("Student:> ");

        if (!getUserChoiceToStartOrQuit()) {
            return;
        }

        do {
            JSONArray quizBank = readJSONFile(QUIZ_BANK_FILE_PATH);;

            HashSet<Integer> randomIdx = new HashSet<>();
            Random random = new Random();
            while (randomIdx.size() < 10) {
                int randGenerator = random.nextInt(quizBank.size());
                randomIdx.add(randGenerator);
            }

            int score = 0;
            int qsn_no = 1;
            for (int idx : randomIdx) {
                JSONObject mcq = (JSONObject) quizBank.get(idx);   // gets the entire question set. One JSON object
                System.out.println(" ");
                score = processQuizQuestion(mcq, score, qsn_no);
                qsn_no++;
            }

            result(score);
            System.out.println("Would you like to start again? press s for start or q for quit");
            System.out.print("Student:> ");

        } while (getUserChoiceToStartOrQuit());
    }


    public static boolean getUserChoiceToStartOrQuit() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String studentInput = sc.nextLine();
            try {
                if (studentInput.equalsIgnoreCase("s")) {
                    return true;
                } else if (studentInput.equalsIgnoreCase("q")) {
                    System.out.println("System:> Quiz Cancelled");
                    return false;
                } else {
                    System.out.println("System:> Invalid input given.\nTry again.");
                    System.out.print("User:> ");
                }
            } catch (Exception e) {
//                throw new RuntimeException(e);
                System.out.println("System:> Invalid Input given.\nTry again.");
                System.out.print("User:> ");
            }
        }
    }

    public static int checkAnswer(JSONObject mcq, int score){
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Student:> ");
            String userInput = sc.nextLine();
            try {
                int userAns = Integer.parseInt(userInput.trim());
//                System.out.println(userAns);
                if (userAns >= 1 && userAns <= 4) {
                    long anskey = (long) mcq.get("answerkey");
                    int ans = (int) anskey;
                    if (ans == userAns) {
                        score++;
                    }
                    break;
                } else {
                    System.out.println("System:> Invalid input given.\nTry again!");
                }
            } catch (NumberFormatException e) {
//            throw new RuntimeException(e);
                System.out.println("System:> Invalid input given. Please enter a valid number.");
            }
        }
        return score;
    }

    public static void result(int score) {
        if (score < 2) {
            System.out.println("System:> Very sorry you failed. You got " + score + " out of 10");
        } else if (score < 5) {
            System.out.println("System:> Very poor! You got " + score + " out of  10");
        } else if (score < 8) {
            System.out.println("System:> Good job! You got " + score + " out of 10");
        } else {
            System.out.println("System:> Excellent! You got " + score + " out of 10");
        }
    }

    public static JSONArray readJSONFile(String filePath) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader = new FileReader(filePath);
        return (JSONArray) jsonParser.parse(fileReader);
    }

    public static void writeJSONFile(String filePath, JSONArray data) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(data.toJSONString());
        writer.flush();
        writer.close();
    }

    public static int processQuizQuestion(JSONObject mcq, int score, int qsn_no) {
        System.out.println("System:> [Question " + qsn_no + "]");
        System.out.println(mcq.get("question"));

        for (int i = 1; i <= 4; i++) {
            System.out.println(i + "." + mcq.get("option " + i));
        }
        return checkAnswer(mcq, score);
    }



}