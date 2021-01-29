package collegeexplore.CollegeInfo;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by DANDE on 13-11-2017.
 */


public class SpaceItemDecorationrecyc extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecorationrecyc(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;

        if(position%2==0){
            outRect.left = 0;
        }

    }
}