package pl.ipebk.setsolver.presentation.ui.solver

import io.reactivex.subjects.PublishSubject
import pl.ipebk.setsolver.domain.SetCardThreePack
import pl.ipebk.setsolver.presentation.ui.base.AbstractViewModel
import pl.ipebk.setsolver.presentation.ui.misc.SubjectDelegate

class SetViewModel(val setPack: SetCardThreePack) : AbstractViewModel() {
  private val clicks = PublishSubject.create<Unit>()
  val clickStream by SubjectDelegate(clicks)
  val card1 = setPack.card1
  val card2 = setPack.card2
  val card3 = setPack.card3
  var selected = false

  fun onClick() {
    clicks.onNext(Unit)
  }
}