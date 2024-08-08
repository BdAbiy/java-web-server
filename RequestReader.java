public class RequestReader {
    public static void main(String[] args) {
        ReadRequest("POST var HTTP/1.1");
    }
    public static String[] ReadRequest(String r){
        boolean ok = false;
        String[] resulte = {"","",""};
        int size =0;
        if (r.endsWith("HTTP/1.0") ||r.endsWith("HTTP/1.1")){size = r.length()-8;}else if (r.endsWith("HTTP/3") ||r.endsWith("HTTP/2")){size = r.length()-6;}
        
        char[] requestchar = r.toCharArray();
        int startpos = 0;
        String methode ="";
        String path = "";
        boolean entersjloop = true;
        for (int i = 0; i <2; i++) {
            for (int j = 0; j != size; j++) {
                if (entersjloop){entersjloop = false;j = startpos;}
                if (requestchar[j] == ' '){startpos=j+1;entersjloop = true;break;}
                if (i == 0){methode += requestchar[j];}else if (i == 1){path += requestchar[j];}
            }
            resulte[0] = methode;
            resulte[2] = path;
        }System.err.println("methode :"+ methode + "\npath:" + path);
       
             if (r.contains("script")|| r.contains("js")){
                resulte[1] = "script";
                
            }else if (r.contains("style")|| r.contains("css")){
                resulte[1] = "style";
               
            }else if (r.contains("png") || r.contains("jpg") || r.contains("jpeg")){
                resulte[1] ="img";
                
            }else if (r.contains("ico")){
                resulte[1] ="ico";
                
            }else{resulte[1]="indexfile";}
            try {
                
            } catch (Exception e) {
                // TODO: handle exception
            }
            if (resulte[1].isEmpty()){
                resulte[0] = "redirect";
            }
       
        return resulte;
        }}

