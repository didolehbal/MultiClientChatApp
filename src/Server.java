import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final int PORT = 8000;
    static Map<String,Socket> clients = new HashMap();
    public static void main(String[] args) {
        try{
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("server listenting on " + PORT +"...");
            while(true){
                Socket s = server.accept();
                new Worker(s).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

class Worker extends Thread{
    Socket s;
    public Worker(Socket s){
        this.s = s;
    }
    @Override
    public void run() {
        try{
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(s.getInputStream()));
            String name = in.readLine();
            System.out.println("new Connection established name :" + name );
            Server.clients.put(name,s);

            String msg;
            do{
                 msg = in.readLine();
                 for(Map.Entry<String,Socket> client : Server.clients.entrySet()){
                     if(client.getValue() != s){
                         PrintWriter out = new PrintWriter(client.getValue().getOutputStream());
                         out.println(name + ": " + msg);
                         out.flush();
                     }
                 }
                 System.out.println( name+": "+ msg);
            }while(!msg.equals("bye"));
            System.out.println("end connection");
            s.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
