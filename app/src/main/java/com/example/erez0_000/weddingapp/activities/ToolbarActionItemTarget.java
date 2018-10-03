package com.example.erez0_000.weddingapp.activities;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.widget.Toolbar;

import uk.co.deanwild.materialshowcaseview.target.Target;
import uk.co.deanwild.materialshowcaseview.target.ViewTarget;

public class ToolbarActionItemTarget implements Target {

    private final Toolbar toolbar;
    private final int menuItemId;

    public ToolbarActionItemTarget(Toolbar toolbar, @IdRes int itemId) {
        this.toolbar = toolbar;
        this.menuItemId = itemId;
    }

    @Override
    public Point getPoint() {
        return new ViewTarget(toolbar.findViewById(menuItemId)).getPoint();
    }

    @Override
    public Rect getBounds() {
        return null;
    }
}
