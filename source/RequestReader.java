package source;
public class RequestReader {
    /**
 * A class for reading the request 
 * 
 */
public String methode ="";
public String filetype ="";
public String path ="";
public String extension ="";
public RequestReader(String r){
    boolean ok = false;
    String[] resulte = {"","","",""};
    int size =0;
    if (r.endsWith("HTTP/1.0") ||r.endsWith("HTTP/1.1")){size = r.length()-8;}else if (r.endsWith("HTTP/3") ||r.endsWith("HTTP/2")){size = r.length()-6;}
    
    char[] requestchar = r.toCharArray();
    int startpos = 0;
    String m ="";
    String body = "";
    boolean entersjloop = true;
    for (int i = 0; i <2; i++) {
        for (int j = 0; j != size; j++) {
            if (entersjloop){entersjloop = false;j = startpos;}
            if (requestchar[j] == ' '){startpos=j+1;entersjloop = true;break;}
            if (i == 0){m += requestchar[j];}else if (i == 1){body += requestchar[j];}
        }
        resulte[0] = m;
        resulte[2] = body;
    };
   
         if (r.contains("script")|| r.contains("js")){
            resulte[1] = "script";
            
        }else if (r.contains("style")|| r.contains("css")){
            resulte[1] = "style";
           
        }else if (r.contains(".png") || r.contains(".jpg") || r.contains(".jpeg")|| r.contains(".svg")|| r.contains(".gif")||r.contains(".bmp")||r.contains(".webp")||r.contains(".ico")){
            resulte[1] ="img";
            if (r.contains(".png")) {
                resulte[3] = "png";
            }else if(r.contains(".jpg")){
                resulte[3] = "jpg";
            }else if (r.contains(".jpeg")) {
                resulte[3] = "jpg";
            }else if ( r.contains(".svg")) {
                resulte[3] = "svg+xml";
            }else if (r.contains(".gif")) {
                resulte[3] = "gif";
            }else if (r.contains(".bmp")) {
                resulte[3] = "bmp";
            }else if (r.contains(".webp")) {
                resulte[3] = "webp";
            }else if (r.contains(".ico")) {
                resulte[3] = "ico";
            }
            
        }else{resulte[1]="indexfile";}
        try {
            
        } catch (Exception e) {
            
        }
        if (resulte[1].isEmpty()){
            resulte[0] = "redirect";
        }
        this.methode =resulte[0];
        this.filetype = resulte[1];
        this.path = "files/" + resulte[2];
        this.extension = resulte[3];
    }

  
   
}