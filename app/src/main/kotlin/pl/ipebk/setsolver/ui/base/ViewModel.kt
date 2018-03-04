package pl.ipebk.setsolver.ui.base

interface ViewModel {

  fun bind()

  fun unbind()

  fun onDestroy()
}