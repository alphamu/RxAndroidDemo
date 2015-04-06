# RxAndroidDemo
Sample app using [RxJava](https://github.com/ReactiveX/RxJava), [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
and [gradle-retrolambda](https://github.com/evant/gradle-retrolambda) for Java 8 Lambda support

## Hello World Example

How to create an `Observer` and `Subscription`. 
Source code includes examples of using Java 6 compatible code and code using Java 8 lambdas. 

## Hello World Lifecycle Example

Same as Hello World example, however, it shows how to retain subscription through the activity lifecycle.

## Transformation Example

Shows how to use operators to transform data after it is emitted by the Observer and before it's received by a Subscription.

## Transformation Example using FlatMap

Shows how to use `flatMap` to simplify code.

## Filter Example

Operators can be used to filter emitted data. Meaning, certain emitted data will not be propogated to the Subscriber.

## List Fragment Example & Widget and View Example

Shows how to create custom operators and bind events on views.

