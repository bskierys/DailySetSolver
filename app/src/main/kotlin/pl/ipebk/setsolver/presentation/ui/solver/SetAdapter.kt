package pl.ipebk.setsolver.presentation.ui.solver

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.ipebk.setsolver.R
import pl.ipebk.setsolver.databinding.ItemSetBinding
import pl.ipebk.setsolver.domain.SetCardThreePack
import pl.ipebk.setsolver.presentation.ui.ActivityScope
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class SetAdapter @Inject constructor(private val drawableProvider: SetCardDrawableProvider)
  : RecyclerView.Adapter<SetAdapter.SetViewHolder>() {

  private var sets: List<SetCardThreePack> = emptyList()

  fun updateSets(sets: List<SetCardThreePack>) {
    this.sets = emptyList()
    this.sets += sets

    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
    val binding = DataBindingUtil.inflate<ItemSetBinding>(
      LayoutInflater.from(parent.context),
      R.layout.item_set,
      parent,
      false
    )

    return SetViewHolder(binding)
  }

  override fun getItemCount(): Int = sets.size

  override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
    val binding = holder.binding
    val set = sets[position]
    var viewModel = binding.viewModel

    // Unbind old  iewModel if we have one
    viewModel?.unbind()

    // Create new ViewModel, set it, and bind it
    viewModel = SetViewModel(set)
    binding.viewModel = viewModel
    viewModel.bind()

    binding.card1View.setImageDrawable(drawableProvider.provideCardDrawable(viewModel.card1))
    binding.card2View.setImageDrawable(drawableProvider.provideCardDrawable(viewModel.card2))
    binding.card3View.setImageDrawable(drawableProvider.provideCardDrawable(viewModel.card3))

    holder.setClickListener {
      Timber.d("clicked")
    }
  }

  class SetViewHolder(val binding: ItemSetBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setClickListener(callback: (SetCardThreePack) -> Unit?) {
      binding.viewModel.clickStream.subscribe {
        callback.invoke(binding.viewModel.setPack)
      }
    }
  }
}