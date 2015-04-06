package com.alimuzaffar.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.alimuzaffar.rxjava.observable.RandomNumberArrayObservable;
import com.alimuzaffar.rxjava.util.EnglishNumberToWords;

import java.util.Random;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;


public class TransformUsingFlatMapActivity extends Activity {

  TextView mMessage;
  Observable<Integer[]> mObservable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transform_using_flat_map);
    mMessage = (TextView) findViewById(R.id.message);
    mObservable = RandomNumberArrayObservable.getObservableOnBackgroundThread();
    approachOne();
  }

  public void approachOne() {
    mMessage.append("\nApproach 1:\n".toUpperCase());
    //Approach one
    mObservable.subscribe(sub -> {
      for (Integer i : sub) {
        String words = EnglishNumberToWords.convert(i);
        mMessage.append(words + "\n");
      }
    }, err -> {
    }, () -> approachTwo());
  }

  private void approachTwo() {
    mMessage.append("\nApproach 2:\n".toUpperCase());
    //Approach two (remove for loop)
    mObservable.subscribe(new Subscriber<Integer[]>() {
      @Override
      public void onCompleted() {
        approachThree();
      }

      @Override
      public void onError(Throwable e) {}

      @Override
      public void onNext(Integer[] integers) {
        Observable.from(integers)
            .map(num -> EnglishNumberToWords.convert(num))
            .subscribe(new Subscriber<String>() {
              @Override
              public void onCompleted() {}

              @Override
              public void onError(Throwable e) {}

              @Override
              public void onNext(String s) {
                mMessage.append(s + "\n");
              }
            });
      }
    });

  }

  public void approachTwoWithLambda() {
    mMessage.append("\nApproach 2 w/ Lambda:\n".toUpperCase());
    //Approach two (remove for-loop)
    mObservable.subscribe(array -> Observable.from(array)
            .map(num -> EnglishNumberToWords.convert(num))
            .subscribe(words -> mMessage.append(words + "\n")),
        err -> {
        }, () -> approachThreeWithLambda());
  }

  public void approachThree() {
    mMessage.append("\nApproach 3:\n".toUpperCase());
    //USING FLAT MAPS
    mObservable.flatMap(new Func1<Integer[], Observable<Integer>>() {
      @Override
      public Observable<Integer> call(Integer[] integers) {
        return Observable.from(integers);
      }
    }).map(num -> EnglishNumberToWords.convert(num))
        .subscribe(words -> mMessage.append(words + "\n"));
  }

  public void approachThreeWithLambda() {
    mMessage.append("\nApproach 3 w/ Lambda:\n".toUpperCase());
    //USING FLAT MAPS AND LAMDAS
    mObservable.flatMap(array -> Observable.from(array))
        .map(num -> EnglishNumberToWords.convert(num))
        .subscribe(words -> mMessage.append(words + "\n"),
            err -> {}, () -> {});
  }

}
