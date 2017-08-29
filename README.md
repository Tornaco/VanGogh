# VanGogh

[![](https://jitpack.io/v/Tornaco/VanGogh.svg)](https://jitpack.io/#Tornaco/VanGogh)

```
compile 'com.github.Tornaco:VanGogh:v0.1-alpha'
```

```java
 Vangogh.with(InstrumentationRegistry.getContext())
                .load(Uri.EMPTY)
                .placeHolder(R.drawable.ic_home_black_24dp)
                .fallback(R.drawable.ic_dashboard_black_24dp)
                .applier(new FadeInApplier())
                .effect(new CircleImageEffect())
                .skipDiskCache(false)
                .skipMemoryCache(true)
                .usingLoader(new CustomLoader())
                .into(imageView);
```
