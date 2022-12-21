import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyModel extends AbstractListModel {

    List<Score> scoreArrayList = null;
    FileInputStream fileIn;
    ObjectInputStream objectIn;

    public MyModel() {

        try {
            fileIn = new FileInputStream("data/highscore.ser");
            objectIn = new ObjectInputStream(fileIn);
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
    public Object getElementAt(int index) {
        return scoreArrayList.get(index).getName();
    }
}
