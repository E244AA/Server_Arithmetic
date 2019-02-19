import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static int PORT = 8081;
    private static ServerSocket SERVER_SOCKET;
    private ExecutorService executeIt = Executors.newFixedThreadPool(1000);

    public void setPORT(int PORT) {
        Server.PORT = PORT;
    }

    public void startServer() throws java.io.IOException{
        SERVER_SOCKET = new ServerSocket(PORT);
        System.out.println("Server start");
        while(!SERVER_SOCKET.isClosed()){
            Socket client = SERVER_SOCKET.accept();
            executeIt.execute(new MultiThread(client));
        }

    }

    public void shutdownServer() throws IOException{
        SERVER_SOCKET.close();
        executeIt.shutdownNow();
    }

    public void restartServer() throws IOException{
        shutdownServer();
        startServer();
    }

}

class MultiThread implements Runnable{

    private Socket sock;
    private InetAddress ClientInetAddress;

    private DataInputStream in;
    private DataOutputStream out;

    //private PrintWriter out;
    //private Scanner in;

    public MultiThread(Socket client) throws IOException{
        sock = client;
        ClientInetAddress = sock.getInetAddress();
    }

    @Override
    public void run() {

        System.out.println(ClientInetAddress + " is connected");
        try {
            out = new DataOutputStream(sock.getOutputStream());
            in = new DataInputStream(sock.getInputStream());
        }catch (Exception ignored){}

        while(!sock.isClosed()){

            String message = null;

            try {
                message = in.readUTF();
                System.out.println(sock.getInetAddress().getCanonicalHostName() + " : " + message);
                out.writeUTF(Integer.toString(ans(message)));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        System.out.println(ClientInetAddress + " is disconnected");

        try {
            in.close();
            out.close();
            sock.close();
        }catch (IOException e){

        }

    }

    public static int ans(String s){
        s.replaceAll("[ ]", " ");
        ArrayList<Integer> n = new ArrayList<>();
        ArrayList<Character> whatdo = new ArrayList<>();
        String k = "";
        int ret = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == ' '){
                continue;
            }
            if(s.charAt(i) >= '0' && s.charAt(i) <= '9'){

                k += s.charAt(i);
            }
            else{
                n.add(Integer.parseInt(k));
                k = "";
                whatdo.add(s.charAt(i));
            }
        }
        n.add(Integer.parseInt(k));

        while(n.size() > 1){
            int a = n.get(0);
            int b = n.get(1);

            int c = 0;

            switch(whatdo.get(0)){
                case '+':
                    c = a + b;
                    break;
                case '-':
                    c = a - b;
                    break;
                case '*':
                    c = a * b;
                    break;
                case '/':
                    c = a / b;
                    break;
                case '%':
                    c = a % b;
                    break;
            }

            n.remove(0);
            n.remove(0);
            n.add(0, c);

            whatdo.remove(0);

            ret = c;
        }

        return ret;
    }
}
