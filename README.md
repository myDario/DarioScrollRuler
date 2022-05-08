# DarioScrollRuler
Android scrolling ruler view

![preview](art/screen0.gif)

## Install Dependency
If you haven't already, add the jitpack repository to the root `buil.gradle` file.

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency:
```gradle
implementation 'com.github.myDario:DarioScrollRuler:1.0.0'
```

## Usage
Add the view in a layout:

```xml
<com.labstyle.darioscrollruler.DarioScrollRuler
    android:id="@+id/darioScrollRuler"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="60dp"
    app:minValue="100"
    app:maxValue="300"
    app:initialValue="200"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintBottom_toBottomOf="parent"/>
```

### Scroll listener
Add a scroll listener to get the value on scroll:
```kotlin
ruller.scrollListener = object: ScrollRulerListener {
    override fun onRulerScrolled(value: Float) {
        //...
    }
}
```

### Set min, max, initial value
Set the minimum value, maximum value, and initial value through code:
```kotlin
ruller.minValue = 0f
ruller.maxValue = 100f
ruller.initialValue = 50f
```