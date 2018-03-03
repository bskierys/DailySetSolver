package pl.ipebk.setsolver.ui.misc

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlin.reflect.KProperty

/**
 * Delegate class that transforms subject to property
 */
class SubjectDelegate<T>(private var type: Subject<T>) {
  operator fun getValue(thisRef: Any?, property: KProperty<*>): Observable<T> {
    return type.hide()
  }

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Observable<T>) {
    type = PublishSubject.create()
    value.subscribe(type)
  }
}