package com.alimuzaffar.rxjava;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

public class SeparatorTextViewFull extends TextView {

  private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

  private Drawable mDivider;
  private Paint mDividerPaint;
  int mPadding = 16;
  int mDividerColor = -1;
  int mDividerHeight = 2;
  boolean isNinePatch = false;

  public SeparatorTextViewFull(Context context) {
    super(context);
    init(context, null);
  }

  public SeparatorTextViewFull(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public SeparatorTextViewFull(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public SeparatorTextViewFull(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  @Override
  protected void onDraw(Canvas c) {
    super.onDraw(c);
    int left = getPaddingLeft();
    int top = getPaddingTop();
    int bottom = getHeight() - getPaddingBottom();
    if (isNinePatch || mDivider == null) {
      top = (getHeight() + getPaddingTop()) / 2;
      if (mDivider != null) {
        bottom = top + mDivider.getIntrinsicHeight();
      } else {
        bottom = top + mDividerHeight;
        if (mDividerHeight > 1) {
          bottom -= mDividerHeight / 2;
          top -= mDividerHeight / 2;
        }
      }
    }

    int right = getWidth() - getPaddingRight();
    int textWidth = (int) getTextSize();

    int gravity = getGravity();

    if ((gravity & Gravity.LEFT) == Gravity.LEFT) {
      if (mDivider != null) {
        mDivider.setBounds(left + textWidth + mPadding, top, right, bottom);
        mDivider.draw(c);
      } else {
        c.drawRect(left + textWidth + mPadding, top, right, bottom, mDividerPaint);
      }

    } else if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) {
      if (mDivider != null) {
        mDivider.setBounds(left, top, right - textWidth - mPadding, bottom);
        mDivider.draw(c);
      } else {
        c.drawRect(left, top, right - textWidth - mPadding, bottom, mDividerPaint);
      }

    } else {
//      int w = c.getWidth();
      int w = getWidth() - getPaddingLeft() - getPaddingRight();
      int xPos = (w / 2) - textWidth / 2;

      if (mDivider != null) {
        mDivider.setBounds(left, top, xPos - mPadding, bottom);
        mDivider.draw(c);

        mDivider.setBounds(xPos + textWidth + mPadding, top, right, bottom);
        mDivider.draw(c);
      } else {
        c.drawRect(left, top, xPos - mPadding, bottom, mDividerPaint);
        c.drawRect(xPos + textWidth + mPadding, top, right, bottom, mDividerPaint);
      }
    }

  }

  private void init(Context context, AttributeSet attrs) {
    mDividerPaint = new Paint();
    mDividerPaint.setColor(getPaint().getColor());

    if (attrs == null) {
      final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
      mDivider = styledAttributes.getDrawable(0);
      styledAttributes.recycle();
    } else {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SeparatorTextViewFull, 0, 0);

      final int N = a.getIndexCount();
      for (int i = 0; i < N; i++) {
        int attr = a.getIndex(i);
        switch (attr) {
          case R.styleable.SeparatorTextViewFull_dividerResId:
            int dividerResId = a.getResourceId(attr, 0);
            if (dividerResId != 0) {
              mDivider = context.getResources().getDrawable(dividerResId);
              isNinePatch = mDivider instanceof NinePatchDrawable;
            } else {
              mDivider = null;
            }
            break;

          case R.styleable.SeparatorTextViewFull_dividerTextPadding:
            int textPadding = a.getDimensionPixelSize(attr, mPadding);
            if (textPadding != mPadding) {
              mPadding = textPadding;
            }
            break;

          case R.styleable.SeparatorTextViewFull_dividerHeight:
            int dividerHeight = a.getDimensionPixelSize(attr, -1);
            if (dividerHeight != -1) {
              mDividerHeight = dividerHeight;
            } else {
              mDividerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mDividerHeight, getResources().getDisplayMetrics());
            }
            break;

          case R.styleable.SeparatorTextViewFull_dividerColor:
            ColorStateList dividerColor = a.getColorStateList(attr);
            if (dividerColor != null) {
              mDividerColor = dividerColor.getColorForState(getDrawableState(), getPaint().getColor());
              mDividerPaint.setColor(mDividerColor);
            }

            break;
        }

      }

      a.recycle();
    }

  }
}
