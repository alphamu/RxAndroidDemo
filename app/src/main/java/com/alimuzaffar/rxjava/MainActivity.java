package com.alimuzaffar.rxjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

  Button mBtnHelloWorld;
  Button mBtnHelloWorldLifecycle;
  Button mBtnTransformation;
  Button mBtnTransformationFlatMap;
  Button mBtnFilter;
  Button mBtnListFragment;
  Button mBtnWidgetView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mBtnHelloWorld = (Button) findViewById(R.id.hello_world);
    mBtnHelloWorldLifecycle = (Button) findViewById(R.id.hello_world_lifecycle);
    mBtnTransformation = (Button) findViewById(R.id.transformation);
    mBtnTransformationFlatMap = (Button) findViewById(R.id.transformation_flatmap);
    mBtnFilter = (Button) findViewById(R.id.filter);
    mBtnListFragment = (Button) findViewById(R.id.list_fragment);
    mBtnWidgetView = (Button) findViewById(R.id.widget_view);

    mBtnHelloWorld.setOnClickListener(this);
    mBtnHelloWorldLifecycle.setOnClickListener(this);
    mBtnTransformation.setOnClickListener(this);
    mBtnTransformationFlatMap.setOnClickListener(this);
    mBtnFilter.setOnClickListener(this);
    mBtnListFragment.setOnClickListener(this);
    mBtnWidgetView.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    Intent intent = null;
    if (v.getId() == R.id.hello_world) {
      intent = new Intent(this, HelloWorldActivity.class);
    } else if (v.getId() == R.id.hello_world_lifecycle) {
      intent = new Intent(this, HelloWorldLifecycleActivity.class);
    } else if (v.getId() == R.id.transformation) {
      intent = new Intent(this, TransformationActivity.class);
    } else if (v.getId() == R.id.filter) {
      intent = new Intent(this, FilterActivity.class);
    } else if (v.getId() == R.id.transformation_flatmap) {
      intent = new Intent(this, TransformUsingFlatMapActivity.class);
    } else if (v.getId() == R.id.list_fragment) {
      intent = new Intent(this, ListFragmentActivity.class);
    } else if (v.getId() == R.id.widget_view) {
      intent = new Intent(this, WidgetViewObservablesActivity.class);
    }

    if (intent != null) {
      startActivity(intent);
    }
  }
}
