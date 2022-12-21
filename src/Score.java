import java.io.Serializable;

public class Score implements Serializable {
    private String name;
    private int points;
    private String time;

    public Score(String name,int points,String time) {
        this.name = name;
        this.points = points;
        this.time = time;
    }

    public String getName(){return name + "---" + points + "---" + time;}


}
