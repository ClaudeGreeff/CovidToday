import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class LifecycleLogger(owner: LifecycleOwner) :
    LifecycleObserver {
    private val tag: String = owner.javaClass.name

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Timber.tag(tag)
        Timber.v("Lifecycle change observed: onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Timber.tag(tag)
        Timber.v("Lifecycle change observed: onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Timber.tag(tag)
        Timber.v("Lifecycle change observed: onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Timber.tag(tag)
        Timber.v("Lifecycle change observed: onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Timber.tag(tag)
        Timber.v("Lifecycle change observed: onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Timber.tag(tag)
        Timber.v("Lifecycle change observed: onDestroy")
    }

}