package com.alimuzaffar.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.alimuzaffar.rxjava.observable.HelloWorldObservable;

import rx.Observable;
import rx.functions.Func1;


public class FilterActivity extends Activity {

  TextView mMessage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter);
    mMessage = (TextView) findViewById(R.id.message);

    Observable<String> observable = HelloWorldObservable.getObservableBackgroundThread();
    observable.filter(new Func1<String, Boolean>() {
      @Override
      public Boolean call(String s) {
        return !(s.contains(".") || s.contains("!"));
      }
    }).subscribe(mMessage::append, err->{}, ()->mMessage.append("\nDONE!"));

  }



}
