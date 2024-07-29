import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyModel extends AbstractListModel<String> {

    private List<Score> scoreArrayList;

    public MyModel() {
        scoreArrayList = new ArrayList<>();

        try (InputStream fileIn = getClass().getResourceAsStream("/data/highscore.ser");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            scoreArrayList = (ArrayList<Score>) objectIn.readObject();

            Collections.sort(scoreArrayList, new Comparator<Score>() {
                @Override
                public int compare(Score o1, Score o2) {
                    return Integer.parseInt(o2.getName().split("---")[1]) - Integer.parseInt(o1.getName().split("---")[1]);
                }
            });

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getSize() {
        return scoreArrayList.size();
    }

    @Override
    public String getElementAt(int index) {
        return scoreArrayList.get(index).getName();
    }
}
