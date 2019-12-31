import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost",8000);

            /*

            System.out.println(msg);

           */
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(s.getInputStream()));
            new Thread(new AsyncReader(in)).start();
            PrintWriter out =
                    new PrintWriter(s.getOutputStream(),true);

            Scanner keyboard = new Scanner(System.in);

            System.out.print("votre nom : ");
            String msgToSend = keyboard.nextLine();
            out.println(msgToSend);

            String msg;
            do{

                msg = keyboard.nextLine();
                out.println(msg);


            }while(!msg.equals("bye"));
            out.println("Disconnected !");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class AsyncReader implements  Runnable{
    BufferedReader in;
    AsyncReader(BufferedReader in){
        this.in = in;
    }
    @Override
    public void run() {
        try{
            while(true){
                String incomingMsg = in.readLine();
                System.out.println("\n"+incomingMsg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
