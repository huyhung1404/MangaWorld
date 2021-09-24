package com.example.mangaworld.Extension;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

public class StartSnapHelper extends LinearSnapHelper {

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView)
            throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {
        int[] out = new int[2];

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToStart(targetView, OrientationHelper.createVerticalHelper(layoutManager));
            return out;
        }
        return out;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        return getStartView(layoutManager, OrientationHelper.createVerticalHelper(layoutManager));
    }

    private int distanceToStart(View targetView, OrientationHelper helper) {
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    private View getStartView(RecyclerView.LayoutManager layoutManager,
                              OrientationHelper helper) {

        if (layoutManager instanceof LinearLayoutManager) {
            int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

            boolean isLastItem = ((LinearLayoutManager) layoutManager)
                    .findLastCompletelyVisibleItemPosition()
                    == layoutManager.getItemCount() - 1;

            if (firstChild == RecyclerView.NO_POSITION || isLastItem) {
                return null;
            }

            View child = layoutManager.findViewByPosition(firstChild);

            if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2 && helper.getDecoratedEnd(child) > 0) {
                return child;
            }

            if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                return null;
            }
            return layoutManager.findViewByPosition(firstChild + 1);
        }
        return super.findSnapView(layoutManager);
    }

}
