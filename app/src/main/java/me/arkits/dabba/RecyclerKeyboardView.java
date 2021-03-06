package me.arkits.dabba;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;

/**
 * Created by archi on 4/10/2016.
 */

// Keyboard border color modifier


public class RecyclerKeyboardView extends RecyclerView {
    private Paint mPaint;
    public RecyclerKeyboardView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.argb(128, 255, 255, 255));
    }
    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec*2);
    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawLine(getLeft(), getTop(), getRight(), getTop(), mPaint);
        super.dispatchDraw(canvas);
        canvas.drawLine(getLeft(), getBottom(), getRight(), getBottom(), mPaint);
    }
}
