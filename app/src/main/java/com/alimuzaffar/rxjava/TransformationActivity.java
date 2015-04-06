package com.alimuzaffar.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.alimuzaffar.rxjava.observable.HelloWorldObservable;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;


public class TransformationActivity extends Activity {

  TextView mMessage;
  Observable<String> mObservable;
  Observable<Integer> mIntObservable;
  Subscription mSubscription;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transformation);
    mMessage = (TextView) findViewById(R.id.message);
    mObservable = HelloWorldObservable.getObservableBackgroundThread();
    mMessage.setText("ORIGINAL: ");
    mSubscription = mObservable.subscribe(sub -> mMessage.append(sub),
        err -> {},
        () -> doTransform());

  }

  public void doTransform() {
    mMessage.append("\nUPPERCASE: ");
    mObservable = mObservable.map(new Func1<String, String>() {
      @Override
      public String call(String s) {
        return s.toUpperCase();
      }
    });
    mSubscription = mObservable.subscribe(sub -> mMessage.append(sub),
        err -> {},
        () -> doMore());
  }
  public void doMore() {
    mMessage.append("\nNO DOTS: ");

    mObservable = mObservable.map(new Func1<String, String>() {
      @Override
      public String call(String s) {
        return s.replace(".", "-");
      }
    });
    mSubscription = mObservable.subscribe(sub -> mMessage.append(sub),
        err -> {},
        () -> hashIt());
  }

  @Override
  protected void onStop() {
    super.onStop();
    mSubscription.unsubscribe();
  }

  public void hashIt() {
    mMessage.append("\nHASH: ");
    //The output type does not have to be the same
    //as the input type.
    mIntObservable = mObservable.map(new Func1<String, Integer>() {
      @Override
      public Integer call(String s) {
        return s.hashCode();
      }
    });
    mSubscription = mIntObservable.subscribe(sub -> mMessage.append(sub.toString()));
  }
}
