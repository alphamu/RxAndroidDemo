package com.alimuzaffar.rxjava.observable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ali on 5/04/2015.
 */
public class HelloWorldObservable {

  public static Observable<String> getObservable() {
    return Observable.create(
        new Observable.OnSubscribe<String>() {
          @Override
          public void call(Subscriber<? super String> sub) {
            try {
              Thread.sleep(1000);
              sub.onNext("Hello");
              Thread.sleep(1000);
              sub.onNext(".");
              Thread.sleep(1000);
              sub.onNext("World");
              Thread.sleep(1000);
              sub.onNext("!!!");
              sub.onCompleted();
            } catch (Exception e) {
              sub.onError(e);
            }
          }
        }
    );
  }

  public static Observable<String> getObservableUsingLambda() {
    return Observable.create(sub -> {
          try {
            Thread.sleep(1000);
            sub.onNext("Hello");
            Thread.sleep(1000);
            sub.onNext(".");
            Thread.sleep(1000);
            sub.onNext("World");
            Thread.sleep(1000);
            sub.onNext("!!!");
            sub.onCompleted();
          } catch (Exception e) {
            sub.onError(e);
          }

        }
    );
  }

  public static Observable<String> getObservableBackgroundThread() {
    return getObservable()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
