# DarioScrollRuler
Android scrolling ruler view

<img src="art/screen0.gif" width="200"/>

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
implementation 'com.github.myDario:DarioScrollRuler:1.0.6'
```

## Usage
Add the view in a layout:

```xml
<com.labstyle.darioscrollruler.DarioScrollRuler
    android:id="@+id/darioScrollRuler"
    app:minValue="100"
    app:maxValue="300"
    app:initialValue="200"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
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
ruller.reload(min = 50f, max = 250f, initValue = 180f, smoothScroll = false)
```

### Scroll to a value
```kotlin
ruller.scrollToValue(value = 123f, smoothScroll = true)
```