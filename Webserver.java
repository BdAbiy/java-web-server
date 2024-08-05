import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Webserver {
    public static int port = 80;
    public static boolean exit = false;

    public static void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("HTTP Server started on port " + port);
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
        try {
            BufferedReader reqReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            
            String request = reqReader.readLine();
            System.out.println("------------   ------------\n"+clientSocket.getInetAddress().getHostAddress() + " :: " + request);
            String response = "";
            if (request  != null && !request.isEmpty()){
                String[] thereq = GetReq(request);
                System.out.println(thereq[0]+" : "+thereq[1]);
             if (thereq[0].startsWith("Ghtml")){
                if ((response = ReturnFileString("files" + thereq[1]))== null){throw new Exception("err");}
                out.println("HTTP/1.1 200 OK");
                r = "200 OK";
                out.println("Content-Type: text/html");
                out.println("Content-Length: " + response.length());
                out.println();
                out.print(response);
                out.flush();
                
             }else if (thereq[0].equals("Gstyle")){
                if ((response = ReturnFileString("files" + thereq[1]))== null){throw new Exception("err");}
                System.out.println(thereq[0]+" : "+thereq[1]);
                response = ReturnFileString("files"+thereq[1]);
                out.println("HTTP/1.1 200 OK");
                r = "200 OK";
                out.println("Content-Type: style/css");
                out.println("Content-Length: " + response.length());
                out.println();
                out.print(response);
                out.flush();
                
             }else if(thereq[0].equals("Gico")){
                out.println("HTTP/1.1 200 OK");
                r = "200 OK";
                out.println("Content-Type: image/x-icon");
                File icon =new File("files" + thereq[1]);
                out.println("Content-Length: " + icon.length());
                out.println();
                out.flush();
                
                clientSocket.getOutputStream().write(getbuffer(icon.getPath()));
                clientSocket.getOutputStream().flush();

             }else if(thereq[0].equals("Gimg")){
                out.println("HTTP/1.1 200 OK");
                r = "200 OK";
                out.println("Content-Type: image/png");
                File img =new File("files" + thereq[1]);
                out.println("Content-Length: " + img.length());
                out.println();
                out.flush();
                clientSocket.getOutputStream().write(getbuffer(img.getPath()));
                clientSocket.getOutputStream().flush();
              }else if(thereq[0].equals("no")){
                out.println("HTTP/1.1 302 Found");
                r = "302 REDIRECT";
                out.println("Location: /index.html");
                out.println();
                out.flush();

             }else {
                out.println("HTTP/1.1 404  NOTFOUND");
                r = "404 Not Found";
             }
        
            }
              
            
          } catch (Exception e) {
            e.printStackTrace();
           
            
           
        }finally{
            try{System.out.println(r);
            
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
        e.printStackTrace();
        return null;
    }
    return res.toString();
}

public static String[] GetReq(String r){
    String[] re = {"",""};
    int size = r.length()-8;
    char[] req = r.toCharArray();
    if (r.startsWith("GET")){
        if (r.contains("index") || r.contains("html")){
        re[0] ="Ghtml";
        for (int i = 4; i != size; i++) {
            re[1] += req[i];
        }
        }else if (r.contains("style")){
            re[0] = "Gstyle";
            for (int i = 4; i != size; i++) {
                re[1] += req[i];
            }
        }else if (r.contains("png") || r.contains("jpg") || r.contains("jpeg")){
            re[0] ="Gimg";
            for (int i = 4; i != size; i++) {
                re[1] += req[i];
            }
        }else if (r.contains("ico")){
            re[0] ="Gico";
            for (int i = 4; i != size; i++) {
                re[1] += req[i];
            }
        }
        if (re[1].isEmpty()){
            re[0] = "no";
        }
    }else if (r.startsWith("POST")){

    }
    return re;
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
