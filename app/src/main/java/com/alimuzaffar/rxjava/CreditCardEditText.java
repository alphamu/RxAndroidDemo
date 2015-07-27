package com.alimuzaffar.rxjava;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreditCardEditText extends EditText implements TextWatcher {

  private SparseArray<Pattern> mCCPatterns = null;

  private final char mSeparator = ' ';

  private final int mDefaultDrawableResId = R.drawable.creditcard; //default credit card image
  private int mCurrentDrawableResId = 0;
  private Drawable mCurrentDrawable;

  public CreditCardEditText(Context context) {
    super(context);
    init();
  }

  public CreditCardEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CreditCardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CreditCardEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    if (mCCPatterns == null) {
      mCCPatterns = new SparseArray<>();
      // With spaces for credit card masking
      mCCPatterns.put(R.drawable.visa, Pattern.compile("^4[0-9\\s]{2,15}(?:[0-9]{3})?$"));
      mCCPatterns.put(R.drawable.mastercard, Pattern.compile("^5[1-5][0-9\\s]{1,17}$"));
      mCCPatterns.put(R.drawable.amex, Pattern.compile("^3[47][0-9\\s]{1,15}$"));

      // Without spaces for credit card masking
//      mCCPatterns.put(R.drawable.visa, Pattern.compile("^4[0-9]{2,12}(?:[0-9]{3})?$"));
//      mCCPatterns.put(R.drawable.mastercard, Pattern.compile("^5[1-5][0-9]{1,14}$"));
//      mCCPatterns.put(R.drawable.amex, Pattern.compile("^3[47][0-9]{1,13}$"));

      //if we want a space after every 4 characters.
      addTextChangedListener(this);
    }
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {

    if (mCCPatterns == null) {
      init();
    }

    int mDrawableResId = 0;
    for (int i = 0; i < mCCPatterns.size(); i++) {
      int key = mCCPatterns.keyAt(i);
      // get the object by the key.
      Pattern p = mCCPatterns.get(key);

      Matcher m = p.matcher(text);
      if (m.find()) {
        mDrawableResId = key;
        break;
      }
    }
    if (mDrawableResId > 0 && mDrawableResId != mCurrentDrawableResId) {
      mCurrentDrawableResId = mDrawableResId;
    } else if (mDrawableResId == 0) {
      mCurrentDrawableResId = mDefaultDrawableResId;
    }

    mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);

  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mCurrentDrawable == null) {
      return;
    }

    int rightOffset = 0;
    if (getError() != null && getError().length() > 0) {
      rightOffset = (int) getResources().getDisplayMetrics().density * 32;
    }

    int right = getWidth() - getPaddingRight() - rightOffset;

    int top = getPaddingTop();
    int bottom = getHeight() - getPaddingBottom();
    float ratio = (float) mCurrentDrawable.getIntrinsicWidth() / (float) mCurrentDrawable.getIntrinsicHeight();
    //int left = right - mCurrentDrawable.getIntrinsicWidth(); //If images are correct size.
    int left = (int) (right - ((bottom - top) * ratio)); //scale image depeding on height available.
    mCurrentDrawable.setBounds(left, top, right, bottom);

    mCurrentDrawable.draw(canvas);

  }

  @Override
  public void afterTextChanged(Editable s) {

    if (mCurrentDrawableResId != R.drawable.amex) {
      defaultCardSpacing(s);
    } else {
      amexSpacing(s);
    }
  }

  private void defaultCardSpacing(Editable s) {
    // Remove spacing char
    if (s.length() > 0 && (s.length() % 5) == 0) {
      final char c = s.charAt(s.length() - 1);
      if (mSeparator == c) {
        s.delete(s.length() - 1, s.length());
      }
    }
    // Insert char where needed.
    if (s.length() > 0 && (s.length() % 5) == 0) {
      char c = s.charAt(s.length() - 1);
      // Only if its a digit where there should be a space we insert a space
      if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(mSeparator)).length <= 3) {
        s.insert(s.length() - 1, String.valueOf(mSeparator));
      }
    }
  }

  private void amexSpacing(Editable s) {
    if (s.length() == 5 || s.length() == 12) {
      final char c = s.charAt(s.length() - 1);
      if (mSeparator == c) {
        s.delete(s.length() - 1, s.length());
      }
    }

    if (s.length() == 5 || s.length() == 12) {
      char c = s.charAt(s.length() - 1);
      // Only if its a digit where there should be a space we insert a space
      if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(mSeparator)).length <= 3) {
        s.insert(s.length() - 1, String.valueOf(mSeparator));
      }
    }
  }

}
