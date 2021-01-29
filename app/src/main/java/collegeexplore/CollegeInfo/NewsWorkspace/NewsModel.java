package collegeexplore.CollegeInfo.NewsWorkspace;

import java.io.Serializable;

/**
 * Created by sab99r
 */
public class NewsModel implements Serializable {
    String title;
    String rating;
    String type;

    public NewsModel(String type) {
        this.type = type;
    }
}
