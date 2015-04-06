package com.alimuzaffar.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;


public class WidgetViewObservablesActivity extends Activity {
  EditText mEditText;
  Button mButton;
  TextView mMessage;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_text_example);
    mEditText = (EditText) findViewById(R.id.text);
    mMessage = (TextView) findViewById(R.id.message);
    mButton = (Button) findViewById(R.id.button);

    ViewObservable.clicks(mButton).subscribe(new Action1<OnClickEvent>() {
      @Override
      public void call(OnClickEvent onClickEvent) {
        Toast.makeText(WidgetViewObservablesActivity.this, "Click", Toast.LENGTH_SHORT).show();
      }
    });

    AppObservable.bindActivity(this, WidgetObservable.text(mEditText, false))
        .debounce(2, TimeUnit.SECONDS) //buffers input for 2 seconds before calling the subscriber
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<OnTextChangeEvent>() {
          @Override
          public void call(OnTextChangeEvent onTextChangeEvent) {
            mMessage.append(mEditText.getText() + "\n");
          }
        });
  }



}
