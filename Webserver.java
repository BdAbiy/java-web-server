import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Webserver {
    public static int port = 80;
    public static boolean exit = false;

    public static void run() {
        try {
            ServerSocket server = new ServerSocket(port); 
            System.out.println("|| HTTP Server started on port " + port+" ||");
            while (true) {
                Socket client = server.accept();
                new Thread(() -> handleRequest(client)).start();;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while starting the server");
        }
    }

    public static void handleRequest(Socket clientSocket) {
        String r ="";
        String threadresult = "";
        
        try {
            BufferedReader reqReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            
            String request = reqReader.readLine();
            threadresult += "===================================\n"+"Client  :: "+clientSocket.getInetAddress().getHostAddress() + "\n" +"Request :: " + request +"\n";
            String response = "";
            if (request  != null && !request.isEmpty()){
                String[] requestarray = new RequestReader().ReadRequest(request);
                threadresult +="Methode :: "+requestarray[0]+"\nPath    :: "+requestarray[2] +"\n";
                if(requestarray[0].equals("GET")){
                    if (requestarray[1].equals("indexfile") || requestarray[1].equals("style") || requestarray[1].equals("script")){
                        if(requestarray[1].equals("indexfile")){
                            if ((response = ReturnFileString("files" + requestarray[2] + "index.html"))== null){throw new Exception("FileNull");};
                        }else{
                            if ((response = ReturnFileString("files" + requestarray[2] ))== null){throw new Exception("FileNull");};
                        }
                        
                        out.println("HTTP/1.1 200 OK");
                        r = "200 OK";
                        switch (requestarray[1]) {
                            case "indexfile":
                                out.println("Content-Type: text/html");
                                break;
                            case "style":
                                out.println("Content-Type: style/css");
                                break;
                            case "script":
                                out.println("Content-Type: text/javascript");break; }
                        
                        out.println("Content-Length: " + response.length());
                        out.println();
                        out.print(response);
                        out.flush();
                    }else if (requestarray[1].equals("img") || requestarray[1].equals("ico")){
                        byte[] buffer;
                        if ((buffer= getbuffer("files/" + requestarray[2]))== null){throw new Exception("FileNull");};
                        out.println("HTTP/1.1 200 OK");
                        r = "200 OK";
                        out.println("Content-Type: image/"+requestarray[3]);
                        out.println("Content-Length: " + buffer.length);
                        out.println();
                        clientSocket.getOutputStream().write(buffer);
                        out.flush();
                    }

                }else if (requestarray[0].equals("POST")){

                }
             
             }else {
                throw new Exception("BadRequest");
             }
        
            
              
            
          } catch (Exception e) {
            if (e.getMessage().equals("FileNull")){
                r = "404 NotFound";
                try {
                    clientSocket.getOutputStream().write("HTTP/1.1 404 Not Found".getBytes());
                } catch (Exception ex) {
                    
                }
                
            }else if (e.getMessage().equals("BadRequest")){
                try {
                    clientSocket.getOutputStream().write("400 Bad Request".getBytes());
                } catch (Exception ex) {
                    
                }
            }
           
            
           
        }finally{
            try{System.out.println(threadresult+"Status  :: "+r);
            
            clientSocket.close();  }catch(Exception e){e.printStackTrace();}
        }
    }
    
public static String ReturnFileString(String path){
    StringBuilder res = new StringBuilder() ;
    try {
    File f = new File(path);
    BufferedReader r =new BufferedReader(new FileReader(f));
    String l ;
    
    while ((l = r.readLine()) != null) {
        res.append(l).append("\n");
        
    }r.close();
}catch(Exception e){
        return null;
    }
    return res.toString();
}




public static byte[] getbuffer(String p) {
    File file = new File(p);
    try (FileInputStream fis = new FileInputStream(file);
         ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        return bos.toByteArray();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    
}
