package com.alimuzaffar.rxjava.observable;

import android.os.SystemClock;

import com.alimuzaffar.rxjava.util.EnglishNumberToWords;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ali on 5/04/2015.
 */
public class RandomNumberArrayObservable {

  public static Observable<Integer[]> getObservable() {
    return Observable.create(new Observable.OnSubscribe<Integer[]>() {
      @Override
      public void call(Subscriber<? super Integer[]> subscriber) {
        Integer[] numbers = new Integer[10];
        for (int i = 0; i < numbers.length; i++) {
          numbers[i] = (int) (Math.random() * 100);
        }
        subscriber.onNext(numbers);
        subscriber.onCompleted();
      }
    });
  }

  public static Observable<Integer[]> getObservableOnBackgroundThread() {
    return getObservable()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static Observable<String> getEmitNumbersObservable() {
    return Observable.range(1, 20).map(new Func1<Integer, String>() {
      @Override
      public String call(Integer integer) {
        return EnglishNumberToWords.convert(integer);
      }
    }).doOnNext(new Action1<String>() {
      @Override
      public void call(String s) {
        SystemClock.sleep(500);
      }
    }).subscribeOn(Schedulers.newThread());
  }

  public static Observable<String> getEmitNumbersObservableOnBackgroundThread() {
    return getEmitNumbersObservable()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

}
