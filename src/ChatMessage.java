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
        this.content=content;
        this.time=getTime();
    }
    public String getMessage() {
        return ("["+time+"]"+author+":"+content);
    }
    public String getAuthor() {
        return author;
    }

    private String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(cal.getTime());
    }

}
