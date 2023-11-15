package BLL;

//import DTO.ExamDTO;
//import DTO.ExamQuestionDTO;
//import DTO.ResultDTO;
//import DTO.SubjectDTO;
import DTO.TransactionDTO;
import DTO.UserDTO;
//import GUI.Login;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class Controller_Server {

    UserBLL uBLL = new UserBLL();
    TransactionBLL tBLL = new TransactionBLL();

    public JSONObject checkFunction(JSONObject json) throws IOException, SQLException {

        switch (json.getString("func")) {
            case "login" -> {
                UserDTO user = uBLL.getUser(json.getString("email"), json.getString("password"));
                if (user == null) {
                    json.put("status", false);
                    json.put("message", "Sai tên tài khoản hoặc mật khẩu!");
                } else {
                    json.put("email", user.getEmail());
                    json.put("money", user.getMoney());
                    json.put("status", true);
                    uBLL.Logon(user.getEmail());

                }

                break;
            }

            case "signup" -> {
                if (uBLL.insertUser(json.getString("email"), json.getString("password")) == 0) {
                    json.put("status", false);
                } else {
                    json.put("status", true);
                    json.put("money", 0);

                }
                break;
            }

            case "addMoney" -> {
                if (uBLL.addMoney(json.getString("email"), json.getInt("money")) == 0) {
                    json.put("status", false);
                } else {
                    json.put("status", true);
                    json.put("money", json.getInt("money"));
                }
                break;
            }
            
            case "getAllUser" -> {

                List list = uBLL.LoadAllUser();
                JSONArray userlist = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    UserDTO uDTO = (UserDTO) list.get(i);
                    userlist.put(new JSONObject()
                            .put("email", uDTO.getEmail()));
                            
                }
                json.put("userList", userlist);
                break;
            }

            case "getRecentTransactions" -> {

                List list = tBLL.LoadRecentTransaction(json.getString("email"));
                JSONArray transactionlist = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    TransactionDTO tDTO = (TransactionDTO) list.get(i);
                    transactionlist.put(new JSONObject()
                            .put("transaction_id", tDTO.getId())
                            .put("sender", tDTO.getSender())
                            .put("receiver", tDTO.getReceiver())
                            .put("note", tDTO.getNote())
                            .put("total", tDTO.getTotal())
                            .put("date", tDTO.getDate()));
                }
                json.put("transactionList", transactionlist);
                break;
            }

            case "getALLTransactions" -> {

                List list = tBLL.LoadAllTransaction(json.getString("email"));
                JSONArray transactionlist = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    TransactionDTO tDTO = (TransactionDTO) list.get(i);
                    transactionlist.put(new JSONObject()
                            .put("transaction_id", tDTO.getId())
                            .put("sender", tDTO.getSender())
                            .put("receiver", tDTO.getReceiver())
                            .put("note", tDTO.getNote())
                            .put("total", tDTO.getTotal())
                            .put("date", tDTO.getDate()));
                }
                json.put("transactionList", transactionlist);
                break;
            }

            case "getTransactionsDate" -> {

                List list = tBLL.LoadTransactionDate(json.getString("email"), json.getString("date"));
                JSONArray transactionlist = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    TransactionDTO tDTO = (TransactionDTO) list.get(i);
                    transactionlist.put(new JSONObject()
                            .put("transaction_id", tDTO.getId())
                            .put("sender", tDTO.getSender())
                            .put("receiver", tDTO.getReceiver())
                            .put("note", tDTO.getNote())
                            .put("total", tDTO.getTotal())
                            .put("date", tDTO.getDate()));
                }
                json.put("transactionList", transactionlist);
                break;
            }

            case "getTransactionsBetween" -> {

                List list = tBLL.LoadTransactionBetweenDate(json.getString("email"), json.getString("fromDate"), json.getString("toDate"));
                JSONArray transactionlist = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    TransactionDTO tDTO = (TransactionDTO) list.get(i);
                    transactionlist.put(new JSONObject()
                            .put("transaction_id", tDTO.getId())
                            .put("sender", tDTO.getSender())
                            .put("receiver", tDTO.getReceiver())
                            .put("note", tDTO.getNote())
                            .put("total", tDTO.getTotal())
                            .put("date", tDTO.getDate()));
                }
                json.put("transactionList", transactionlist);
                break;
            }

            case "transferMoney" -> {
                UserDTO receiver = uBLL.getUserByID(json.getString("receiver"));

                int money_rev = receiver.getMoney() + json.getInt("money");

                if (uBLL.addMoney(json.getString("receiver"), money_rev) == 0) {
                    json.put("status", false);
                } else {

                    if (uBLL.addMoney(json.getString("email"), json.getInt("balance")) == 0) {
                        json.put("status", false);
                    } else {
                        if (tBLL.TransferMoney(json.getString("email"), json.getString("date"), json.getString("note"), json.getInt("money"), json.getString("receiver")) == 0) {
                            json.put("status", false);
                        } else {
                            json.put("status", true);
                        }
                    }

                }
                

//                if (uBLL.insertUser(json.getString("email"), json.getString("password")) == 0) {
//                    json.put("status", false);
//                } else {
//                    json.put("status", true);
//                    json.put("money", 0);
//
//                }
//                
//                for (int i = 0; i < list.size(); i++) {
//                    TransactionDTO tDTO = (TransactionDTO) list.get(i);
//                    transactionlist.put(new JSONObject()
//                            .put("transaction_id", tDTO.getId())
//                            .put("sender", tDTO.getSender())
//                            .put("receiver", tDTO.getReceiver())
//                            .put("note", tDTO.getNote())
//                            .put("total", tDTO.getTotal())
//                            .put("date", tDTO.getDate()));
//                }
//                json.put("transactionList", transactionlist);
                break;
            }

//            case "user" -> {
//                UserDTO user = uBLL.getUserByUsername(json.getString("username"));
//                if (user == null) {
//                    json.put("status", false);
//                } else {
//                    json.put("userID", user.getUserID());
//                    json.put("fullname", user.getFullname());
//                    json.put("birth", user.getDateofBirth());
//                    json.put("blockTakeExam", user.isBlockTakeExam());
//                    json.put("blockAddExam", user.isBlockAddExam());
//                    json.put("gender", user.isGender());
//                    json.put("status", true);
//                }
//                break;
//            }
//            case "otp" -> {
//                if (!json.getString("otpData").equals(json.getString("correctOtp"))) {
//                    json.put("status", false);
//                } else {
//                    json.put("status", true);
//                }
//                break;
//            }
//
//            case "changePass" -> {
//                UserDTO user = uBLL.getUser(json.getString("username"));
//                if (user == null) {
//                    json.put("status", false);
//                    json.put("message", "Sai mật khẩu!");
//                } else if (json.getString("password_old").equals(json.getString("password_new"))) {
//                    json.put("status", false);
//                    json.put("message", "Mật khẩu mới phải khác mật khẩu cũ!");
//                } else {
//                    uBLL.changePassword(json.getString("username"), json.getString("password_new"));
//                    json.put("status", true);
//                }
//                break;
//            }
//
//            case "editUserInfor" -> {
//                if (uBLL.updateUser(json.getString("username"), json.getString("fullname"), json.getBoolean("gender"),
//                        json.getString("birth")) == 0) {
//                    json.put("status", false);
//                } else {
//                    json.put("blockLogin", false);
//                    json.put("status", true);
//                }
//            }
//
//            case "getBlockStatus" -> {
//                UserDTO user = uBLL.getBlockStatus(json.getString("username"));
//                json.put("userID", user.getUserID());
//                json.put("blockTakeExam", user.isBlockTakeExam());
//                json.put("blockAddExam", user.isBlockAddExam());
//                json.put("userID", user.getUserID());
//                break;
//            }
//            case "getExamAll" -> {
//                List list = eBLL.readExam();
//                JSONArray examlist = new JSONArray();
//                for (int i = 0; i < list.size(); i++) {
//                    ExamDTO examDTO = (ExamDTO) list.get(i);
//                    int ExamID = examDTO.getExamID();
//                    examlist.put(new JSONObject().put("examID", ExamID)
//                            .put("subjectName", examDTO.getSubjectname())
//                            .put("examTitle", examDTO.getTitle())
//                            .put("numOfQuiz", examQuestionBLL.getNumOfQuiz(ExamID))
//                            .put("lowestScore", rBLL.Lowest(ExamID))
//                            .put("highestScore", rBLL.Highest(ExamID))
//                            .put("avgScore", rBLL.AvgScore(ExamID))
//                            .put("limitTime", examDTO.getTime())
//                            .put("numOfDo", rBLL.getNumOfDo(ExamID))
//                            .put("creator", examDTO.getFullname()));
//                }
//                json.put("examlist", examlist);
//                break;
//            }
//
//            case "getExamByUser" -> {
//                List list = eBLL.getExamByUser(json.getString("username"));
//                JSONArray examlist = new JSONArray();
//                for (int i = 0; i < list.size(); i++) {
//                    ExamDTO examDTO = (ExamDTO) list.get(i);
//                    int ExamID = examDTO.getExamID();
//                    examlist.put(new JSONObject().put("examID", ExamID)
//                            .put("subjectName", examDTO.getSubjectname())
//                            .put("examTitle", examDTO.getTitle())
//                            .put("numOfQuiz", examQuestionBLL.getNumOfQuiz(ExamID))
//                            .put("lowestScore", rBLL.Lowest(ExamID))
//                            .put("highestScore", rBLL.Highest(ExamID))
//                            .put("avgScore", rBLL.AvgScore(ExamID))
//                            .put("limitTime", examDTO.getTime())
//                            .put("numOfDo", rBLL.getNumOfDo(ExamID))
//                            .put("creator", examDTO.getFullname()));
//                }
//                json.put("examlist", examlist);
//                break;
//            }
//
//            case "getExamByID" -> {
//                ExamDTO examDTO = eBLL.getExamByID(json.getInt("examID"));
//                int ExamID = examDTO.getExamID();
//                json.put("subject", examDTO.getSubjectname());
//                json.put("title", examDTO.getTitle());
//                json.put("numOfQuiz", examQuestionBLL.getNumOfQuiz(ExamID));
//                json.put("lowest", rBLL.Lowest(ExamID));
//                json.put("highest", rBLL.Highest(ExamID));
//                json.put("avg", rBLL.AvgScore(ExamID));
//                json.put("time", examDTO.getTime());
//                json.put("creator", examDTO.getFullname());
//                break;
//            }
//
//            case "getExam" -> {
//                List list = eBLL.getExamBySubject(json.getInt("subjectID"));
//                JSONArray examlist = new JSONArray();
//                for (int i = 0; i < list.size(); i++) {
//                    ExamDTO examDTO = (ExamDTO) list.get(i);
//                    int ExamID = examDTO.getExamID();
//                    examlist.put(new JSONObject().put("examID", ExamID)
//                            .put("subjectName", examDTO.getSubjectname())
//                            .put("examTitle", examDTO.getTitle())
//                            .put("numOfQuiz", examQuestionBLL.getNumOfQuiz(ExamID))
//                            .put("lowestScore", rBLL.Lowest(ExamID))
//                            .put("highestScore", rBLL.Highest(ExamID))
//                            .put("avgScore", rBLL.AvgScore(ExamID))
//                            .put("limitTime", examDTO.getTime())
//                            .put("numOfDo", rBLL.getNumOfDo(ExamID))
//                            .put("creator", examDTO.getFullname()));
//                }
//                json.put("examlist", examlist);
//                break;
//            }
//
//            case "addExam" -> {
//                int result = eBLL.insertExam(json.getString("examTitle"), json.getInt("creator"),
//                        json.getInt("subjectID"), json.getInt("numOfQuiz"), json.getInt("limitTime"));
//                if (result == 0) {
//                    json.put("status", false);
//                    json.put("message", "add exam fail!");
//                } else {
//                    JSONArray questionlist = json.getJSONArray("questionlist");
//
//                    for (int i = 0; i < questionlist.length(); i++) {
//                        JSONObject jQuestion = questionlist.getJSONObject(i);
//                        if (examQuestionBLL.insertQ(result, jQuestion.getInt("number"), jQuestion.getString("question"),
//                                jQuestion.getString("choice1"), jQuestion.getString("choice2"),
//                                jQuestion.getString("choice3"), jQuestion.getString("choice4")) == 0) {
//                            json.put("status", false);
//                            json.put("message", "error when adding" + jQuestion.getInt("number"));
//                            break;
//                        } else {
//                            json.put("status", true);
//                        }
//                    }
//                }
//                break;
//            }
//
//            case "editExam" -> {
//                int examID = json.getInt("examID");
//                if (eBLL.updateExam(examID, json.getString("examTitle"), json.getInt("subjectID"),
//                        json.getInt("limitTime")) == 0) {
//                    json.put("status", false);
//                    json.put("message", "update exam fail!");
//                } else if (examQuestionBLL.deleteAllQ(examID) == 0) {
//                    json.put("status", false);
//                    json.put("message", "delete all question fail!");
//                } else {
//                    JSONArray questionlist = json.getJSONArray("questionlist");
//                    for (int i = 0; i < questionlist.length(); i++) {
//                        JSONObject jQuestion = questionlist.getJSONObject(i);
//                        if (examQuestionBLL.insertQ(examID, jQuestion.getInt("number"),
//                                jQuestion.getString("question"), jQuestion.getString("choice1"),
//                                jQuestion.getString("choice2"),
//                                jQuestion.getString("choice3"), jQuestion.getString("choice4")) == 0) {
//                            json.put("status", false);
//                            json.put("message", "error when updating" + jQuestion.getInt("number"));
//                            break;
//                        } else {
//                            json.put("status", true);
//                        }
//                    }
//                }
//                break;
//            }
//
//            case "deleteExam" -> {
//                if (eBLL.deleteExam(json.getInt("examID")) == 0) {
//                    json.put("status", false);
//                    json.put("message", "Xóa đề thi thất bại!");
//                } else {
//                    json.put("status", true);
//                    json.put("message", "Xóa đề thi thành công");
//                }
//                break;
//            }
//
//            case "getExamQuest" -> {
//
//                ArrayList<String> mylist = new ArrayList<String>();
//                mylist.add("choice1");
//                mylist.add("choice2");
//                mylist.add("choice3");
//                mylist.add("choice4");
//                Collections.shuffle(mylist);
//
//                ExamQuestionDTO examQuest = examQuestionBLL.getExamQuestion(json.getInt("examID"),
//                        json.getInt("number"));
//
//                json.put("question", examQuest.getQuestion());
//                json.put(mylist.get(0), examQuest.getChoice1());
//                json.put(mylist.get(1), examQuest.getChoice2());
//                json.put(mylist.get(2), examQuest.getChoice3());
//                json.put(mylist.get(3), examQuest.getChoice4());
//                break;
//            }
//
//            case "getExamDetailByID" -> {
//                ArrayList<String> mylist = new ArrayList<String>();
//                mylist.add("choice1");
//                mylist.add("choice2");
//                mylist.add("choice3");
//                mylist.add("choice4");
//                Collections.shuffle(mylist);
//                List list = examQuestionBLL.getExamQuestion(json.getInt("examID"));
//                JSONArray examlist = new JSONArray();
//                for (int i = 0; i < list.size(); i++) {
//                    ExamQuestionDTO examQuestionDTO = (ExamQuestionDTO) list.get(i);
//                    examlist.put(new JSONObject()
//                            .put("number", examQuestionDTO.getNumber())
//                            .put("question", examQuestionDTO.getQuestion())
//                            .put(mylist.get(0), examQuestionDTO.getChoice1())
//                            .put(mylist.get(1), examQuestionDTO.getChoice2())
//                            .put(mylist.get(2), examQuestionDTO.getChoice3())
//                            .put(mylist.get(3), examQuestionDTO.getChoice4())
//                            .put("answer", examQuestionDTO.getAnswer()));
//                }
//                json.put("data", examlist);
//                break;
//            }
//
//            case "receiveAnswer" -> {
//
//                ExamQuestionDTO eq = examQuestionBLL.getExamAnswer(json.getInt("examID"), json.getInt("number"));
//
//                int correct = checkAnswer(eq.getAnswer(), json.getString("answer"), json.getInt("correct"));
//                // int score = correct * (10 / (jsonSend.getInt("numOfQuiz")));
//                json.put("correct", correct);
//                // json.put("score", score);
//
//                break;
//            }
//
//            case "getSubject" -> {
//                List list = sBLL.readSubject();
//                JSONArray subjectlist = new JSONArray();
//                for (int i = 0; i < list.size(); i++) {
//                    SubjectDTO e = (SubjectDTO) list.get(i);
//                    subjectlist.put(
//                            new JSONObject().put("subjectID", e.getSubjectID()).put("subjectName", e.getSubjectname()));
//                }
//                json.put("subjectlist", subjectlist);
//                break;
//            }
//
//            case "addResult" -> {
//                if (rBLL.insertResult(json.getInt("examID"), json.getString("examinee"), json.getFloat("score"),
//                        json.getString("date"), json.getInt("correct"), json.getInt("wrong")) == 0) {
//                    json.put("status", false);
//                    json.put("message", "Thêm kết quả thi thất bại!");
//                } else {
//                    int rank = rBLL.getRank(json.getInt("examID"), json.getString("examinee"), json.getString("date"));
//                    json.put("status", true);
//                    json.put("rank", rank);
//                    // json.put("time", rank);
//
//                    /* long milisec = json.getLong("time");
//                    long minutes = (milisec / 1000) / 60; */
//
//                    // formula for conversion for
//                    // milliseconds to seconds
//                    //long seconds = (milisec / 1000) % 60;
//                    json.remove("time");
//                    //json.put("timeDoQuiz", String.valueOf(minutes + ":" + seconds));
//                }
//                break;
//            }
//
//            case "getResultAll" -> {
//                List list = rBLL.getResult();
//                JSONArray examlist = new JSONArray();
//                for (int i = 0; i < list.size(); i++) {
//                    ResultDTO resultDTO = (ResultDTO) list.get(i);
//                    examlist.put(new JSONObject().put("resultID", resultDTO.getResultID())
//                            .put("examID", resultDTO.getExamID())
//                            .put("examinee", resultDTO.getFullname())
//                            .put("score", resultDTO.getScore())
//                            .put("correct", resultDTO.getCorrectQuiz())
//                            .put("wrong", resultDTO.getWrongQuiz())
//                            .put("date", resultDTO.getDate()));
//                }
//                json.put("data", examlist);
//                break;
//            }
            case "logout" -> {
                if (uBLL.Logout(json.getString("email")) == 0) {
                    json.put("status", false);
                } else {
                    json.put("status", true);
                }
                break;
            }

            default -> {

            }
        }
        return json;
    }

    public int checkAnswer(String answer, String receive, int correct) {
        if (answer.equals(receive)) {
            correct++;
        }
        return correct;
    }
}
