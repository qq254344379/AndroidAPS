package info.nightscout.androidaps.plugins.bus

import info.nightscout.androidaps.events.Event
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RxBus {

    companion object {
        @JvmStatic
        @Deprecated("Get via Dagger. Will be removed once fully transitioned to Dagger")
        var INSTANCE: RxBus = RxBus()//TODO: remove as soon as Dagger is fully set up
    }

    private val publisher = PublishSubject.create<Event>()

    fun send(event: Event) {
        publisher.onNext(event)
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> toObservable(eventType: Class<T>): Observable<T> =
            publisher
                    .subscribeOn(Schedulers.io())
                    .ofType(eventType)
}
