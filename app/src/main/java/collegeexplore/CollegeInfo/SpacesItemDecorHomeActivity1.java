package collegeexplore.CollegeInfo;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by DANDE on 17-07-2016.
 */

public class SpacesItemDecorHomeActivity1 extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecorHomeActivity1(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.right = space;

        outRect.bottom = space;
        outRect.top = space;
        outRect.centerX();
        outRect.centerY();

        // Add top margin only for the first item to avoid double space between items
      /*  if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }*/
    }
}