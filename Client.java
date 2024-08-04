import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;


public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client(){


        try{
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("connection done.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch(Exception e){
            System.out.println("Connection is closed");
        }
    }
    private void startReading() {
        
        // thread - read karke deta rahega

        Runnable r1 = () ->{
            System.out.println("reader started...");
            
            try {
            while (true){
                
                    String msg = br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Server terminated the chat");
                        break;
                    }

                    System.out.println("Server : " + msg);
                
            }

        } catch(Exception e){
            // e.printStackTrace();
            System.out.println("Connection is closed");
        }
        };
        new Thread(r1).start();
        
    }
    private void startWriting() {
        //thread - data user lega and the send karega client tak
        
        Runnable r2 = () -> {
            System.out.println("writer started...");
            
            try{
            while (true && !socket.isClosed()) {
               
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")) {
                        socket.close(); // Close the socket when server exits
                        break;
                    }

               
            }

        } catch(Exception e){
            // e.printStackTrace();
            System.out.println("Connection is closed");
        }
        System.out.println("Connection is closed");
        };
        
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("client..");
        new Client();
    }
}
