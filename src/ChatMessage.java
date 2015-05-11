import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* NAME: ChatMessage
 * PROJECT: Chattie - https://github.com/demiurgosoft/chattie
 * AUTHOR: demiurgosoft
 * DESCRIPTION: Message trasmitted by chattie
 */
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = -5185462978942891292L;
    private String author;
    private String content;
    private String time;

    public ChatMessage(String author,String content) {
        super();
        this.author=author;
        this.content=content.trim();
        this.time=getTime();
    }
    public String getMessage() {
        return ("["+time+"]"+author+":"+content);
    }
    public String getAuthor() {
        return author;
    }
    public boolean isValid(){
    	if(author==null || author.replace(" ","").isEmpty()) return false;
    	else if(content==null || content.replace(" ","").isEmpty()) return false;
    	else if(time==null || time.replace(" ","").isEmpty()) return false;
     	else return true;    	
    }
    private String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(cal.getTime());
    }

}
