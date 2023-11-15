package banking;
import BLL.ClientHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.util.concurrent.Future;

public class server {

    private static ServerSocket server = null;
    private static String localIP;
    private static Thread t1 = null;
    private static Thread t2 = null;
    
    
    
    public static String localIP() {
        return localIP;
    }

    @SuppressWarnings("CallToThreadStopSuspendOrResumeManager")
    public static void stopThread() throws IOException,Exception {
        Future<Void> future1 = (Future<Void>) Executors.newSingleThreadExecutor().submit(t1);
        Future<Void> future2 = (Future<Void>) Executors.newSingleThreadExecutor().submit(t2);
        future1.cancel(true);
        future2.cancel(true);
        server.close();
    }

    public static void main(String[] args) throws IOException,Exception,ClassNotFoundException {
        // Server lấy local IP bằng cách tạo socket đến 1 website tạm
        Socket socket = new Socket("thongtindaotao.sgu.edu.vn", 80);
        localIP = socket.getLocalAddress().toString().substring(1);
        // Generate API tại https://retool.com/api-generator/
        String api = "https://retoolapi.dev/FFY4oG/data/1"; // Ghi vào dòng 1 trong DB
        String jsonData = "{\"ip\":\"" + localIP + "\"}";

        // Đưa local ip lên trang tạm
        Jsoup.connect(api)
                .ignoreContentType(true).ignoreHttpErrors(true)
                .header("Content-Type", "application/json")
                .requestBody(jsonData)
                .method(Connection.Method.PUT).execute();

        System.out.println("====================================================");
        System.out.println("Server local ip: " + localIP);
        System.out.println("Server started...");
        System.out.println("Type 'help' for the command list.");
        System.out.println("====================================================");
        server = new ServerSocket(6666);

        // Chạy thread
        t1 = new Thread(new serverToClient(server));
        t2 = new Thread(new server_command(server, t1));
        t1.start();
        t2.start();
        // stopThread();

    }
}

class serverToClient implements Runnable {

    private static final int NUMBER_OF_USER = 20;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_USER);
    private ServerSocket server;

    public serverToClient(ServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                clients.add(clientHandler);
                pool.execute(clientHandler);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

}

class server_command implements Runnable {

    private Thread thread;
    private static ServerSocket s;

    public server_command(ServerSocket server, Thread t) {
        this.s = server;
        this.thread = t;
    }

//    public static void show(String... args) {
//        System.out.println("====================================================");
//        for (String arg : args) {
//            System.out.println(arg);
//        }
//        System.out.println("====================================================");
//    }
//
//    public static void showServerstats() throws SQLException {
//        show("Server local ip: " + new server().localIP(),
//                "Number of online user: " + new UserBLL().getNumberOfUser("online"),
//                "Number of user: " + new UserBLL().getNumberOfUser(""));
//    }
//
//    public static void showHelp() throws SQLException {
//        show("1.server show : Display server infomation.",
//                "2.user [all/online] : Show all user.",
//                "3.block/unblock {UserID} : Block/Unblock all features on user with ID.",
//                "4.block/unblock [login/addexam/takeexam] {UserID} : Block/Unblock login/addexam/takeexam on user with ID.",
//                "5.exam [all/id] : show all exam/exam information."
//        );
//    }

//    

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
//        while (true) {
//            System.out.print(">> ");
//            String temp = sc.nextLine();
//            if(temp.equals("bye")){
//                try {
//                    // Dừng các thread đang chạy
//                    server.stopThread();
//                    break;
//                } catch (Exception ex) {
//                    Logger.getLogger(server_command.class.getName()).log(Level.SEVERE, null, ex);
//                    break;
//                }
//
//                // Thoát khỏi vòng lặp
//               
//            }
//        }
//        while (true) {
//            System.out.print(">> ");
//            String temp = sc.nextLine();
//            String[] cmd = temp.split(" ");
//            switch (cmd[0]) {
//                case "block": {
//                    switch (cmd.length) {
//                        case 2: {
//                            try {
//                                int id = Integer.parseInt(cmd[1]);
//                                if (uBLL.getUserByID(id) != null) {
//                                    if (uBLL.blockLogin(id, true) == 1) {
//                                        System.out.println("Blocked all features in ID=" + id + " successfully!");
//                                    }
//                                } else {
//                                    System.out.println("UserID is not exist!");
//                                }
//                            } catch (Exception e) {
//                                System.out.println("Wrong command! Try 'block {UserID}' or 'block [login/addexam/takeexam] {UserID}.");
//                            }
//                            break;
//                        }
//                        case 3: {
//                            switch (cmd[1]) {
//                                case "login": {
//                                    try {
//                                        int id = Integer.parseInt(cmd[2]);
//                                        if (uBLL.getUserByID(id) != null) {
//                                            if (uBLL.blockLogin(id, true) == 1) {
//                                                System.out.println("Blocked all features in ID=" + id + " successfully!");
//                                            }
//                                        } else {
//                                            System.out.println("UserID is not exist!");
//                                        }
//                                    } catch (Exception e) {
//                                        System.out.println("Wrong command! Try 'block {UserID}' or 'block [login/addexam/takeexam] {UserID}.");
//                                    }
//                                    break;
//                                }
//                                case "addexam": {
//                                    try {
//                                        int id = Integer.parseInt(cmd[2]);
//                                        if (uBLL.getUserByID(id) != null) {
//                                            if (uBLL.blockAddExam(id, true) == 1) {
//                                                System.out.println("Blocked add Exam feature on ID=" + id + " successfully!");
//                                            }
//                                        } else {
//                                            System.out.println("UserID is not exist!");
//                                        }
//                                    } catch (Exception e) {
//                                        System.out.println("Wrong command! Try 'block {UserID}' or 'block [login/addexam/takeexam] {UserID}.");
//                                    }
//                                    break;
//                                }
//                                case "takeexam": {
//                                    try {
//                                        int id = Integer.parseInt(cmd[2]);
//                                        if (uBLL.getUserByID(id) != null) {
//                                            if (uBLL.blockTakeExam(id, true) == 1) {
//                                                System.out.println("Blocked take Exam feature on ID=" + id + " successfully!");
//                                            }
//                                        } else {
//                                            System.out.println("UserID is not exist!");
//                                        }
//                                    } catch (Exception e) {
//                                        System.out.println("Wrong command! Try 'block {UserID}' or 'block [login/addexam/takeexam] {UserID}.");
//                                    }
//                                    break;
//                                }
//                                default: {
//                                    System.out.println("Wrong command! Try 'block {UserID}' or 'block [login/addexam/takeexam] {UserID}.");
//                                }
//                            }
//                            break;
//                        }
//                        default: {
//                            System.out.println("Wrong command! Try 'block {UserID}' or 'block [login/addexam/takeexam] {UserID}.");
//                        }
//                    }
//                    break;
//                }
//                case "unblock": {
//                    switch (cmd.length) {
//                        case 2: {
//                            try {
//                                int id = Integer.parseInt(cmd[1]);
//                                if (uBLL.getUserByID(id) != null) {
//                                    if (uBLL.blockLogin(id, false) == 1) {
//                                        System.out.println("Unblocked all features in ID=" + id + " successfully!");
//                                    }
//                                } else {
//                                    System.out.println("UserID is not exist!");
//                                }
//                            } catch (Exception e) {
//                                System.out.println("Wrong command! Try 'unblock {UserID}' or 'unblock [login/addexam/takeexam] {UserID}.");
//                            }
//                            break;
//                        }
//                        case 3: {
//                            switch (cmd[1]) {
//                                case "login": {
//                                    try {
//                                        int id = Integer.parseInt(cmd[2]);
//                                        if (uBLL.getUserByID(id) != null) {
//                                            if (uBLL.blockLogin(id, false) == 1) {
//                                                System.out.println("Unblocked all features in ID=" + id + " successfully!");
//                                            }
//                                        } else {
//                                            System.out.println("UserID is not exist!");
//                                        }
//                                    } catch (Exception e) {
//                                        System.out.println("Wrong command! Try 'unblock {UserID}' or 'unblock [login/addexam/takeexam] {UserID}.");
//                                    }
//                                    break;
//                                }
//                                case "addexam": {
//                                    try {
//                                        int id = Integer.parseInt(cmd[2]);
//                                        if (uBLL.getUserByID(id) != null) {
//                                            if (uBLL.blockAddExam(id, false) == 1) {
//                                                System.out.println("Unblocked add Exam feature on ID=" + id + " successfully!");
//                                            }
//                                        } else {
//                                            System.out.println("UserID is not exist!");
//                                        }
//                                    } catch (Exception e) {
//                                        System.out.println("Wrong command! Try 'unblock {UserID}' or 'unblock [login/addexam/takeexam] {UserID}.");
//                                    }
//                                    break;
//                                }
//                                case "takeexam": {
//                                    try {
//                                        int id = Integer.parseInt(cmd[2]);
//                                        if (uBLL.getUserByID(id) != null) {
//                                            if (uBLL.blockTakeExam(id, false) == 1) {
//                                                System.out.println(
//                                                        "Unblocked take Exam feature on ID=" + id + " successfully!");
//                                            }
//                                        } else {
//                                            System.out.println("UserID is not exist!");
//                                        }
//                                    } catch (Exception e) {
//                                        System.out.println("Wrong command! Try 'unblock {UserID}' or 'unblock [login/addexam/takeexam] {UserID}.");
//                                    }
//                                    break;
//                                }
//                                default: {
//                                    System.out.println("Wrong command! Try 'unblock {UserID}' or 'unblock [login/addexam/takeexam] {UserID}.");
//                                }
//                            }
//                            break;
//                        }
//                        default: {
//                            System.out.println("Wrong command! Try 'unblock {UserID}' or 'unblock [login/addexam/takeexam] {UserID}.");
//                        }
//                    }
//                    break;
//                }
//                case "server": {
//                    if (cmd.length > 2) {
//                        System.out.println("Wrong command! Try 'server [show]'.");
//                    } else {
//                        switch (cmd[1]) {
//                            case "show": {
//                                try {
//                                    showServerstats();
//                                } catch (SQLException ex) {
//                                    Logger.getLogger(server_command.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                                break;
//                            }
//                            default: {
//                                System.out.println("Wrong command! Try 'server [show]'.");
//                            }
//                        }
//                    }
//                    break;
//                }
//                case "exam": {
//                    if (cmd.length > 2) {
//                        System.out.println("Wrong command! Try 'exam [all/{id}]'.");
//                    } else {
//                        if (cmd[1].equals("all")) {
//                            try {
//                                showExams();
//                            } catch (SQLException ex) {
//                                Logger.getLogger(server_command.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            break;
//                        }
//                        try {
//                            int eid = Integer.parseInt(cmd[1]);
//                            if (eBLL.getExamByID(eid) != null) {
//                                ExamDTO e = eBLL.getExamByID(eid);
//                                show(
//                                        "Title: " + e.getTitle(),
//                                        "Creator: " + e.getFullname(),
//                                        "Subject: " + e.getSubjectname(),
//                                        "Highest Score: " + e.getHighest(),
//                                        "Lowest Score: " + e.getLowest(),
//                                        "Average Score: " + e.getAvg()
//                                );
//                            } else {
//                                System.out.println("ExamID is not exist!");
//                            }
//                        } catch (Exception e) {
//                            System.out.println("Wrong type of ExamID! ID's type should be: Integer.");
//                        }
//                    }
//                    break;
//                }
//                case "user": {
//                    if (cmd.length > 2) {
//                        System.out.println("Wrong command! Try 'user [all/online]'.");
//                    } else {
//                        switch (cmd[1]) {
//                            case "all": {
//                                try {
//                                    showUsers();
//                                } catch (SQLException ex) {
//                                    Logger.getLogger(server_command.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                                break;
//                            }
//                            case "online": {
//                                try {
//                                    showOnlineUsers();
//                                } catch (SQLException ex) {
//                                    Logger.getLogger(server_command.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                                break;
//                            }
//                            default: {
//                                System.out.println("Wrong command! Try 'user [all/online]'.");
//                            }
//                        }
//                    }
//                    break;
//                }
//                case "help": {
//                    try {
//                        showHelp();
//                        break;
//                    } catch (SQLException ex) {
//                        Logger.getLogger(server_command.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                default: {
//                    System.out.println("Invalid command! Type 'help' for more info!");
//                }
//            }
//        }
  }

}
