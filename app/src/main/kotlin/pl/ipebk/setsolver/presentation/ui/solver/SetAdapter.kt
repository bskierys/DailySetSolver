package pl.ipebk.setsolver.presentation.ui.solver

import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.ipebk.setsolver.R
import pl.ipebk.setsolver.databinding.ItemHeaderBinding
import pl.ipebk.setsolver.databinding.ItemSetBinding
import pl.ipebk.setsolver.domain.SetCardThreePack
import pl.ipebk.setsolver.presentation.ui.ActivityScope
import javax.inject.Inject

@ActivityScope
class SetAdapter @Inject constructor(
  private val drawableProvider: SetCardDrawableProvider,
  private val resources: Resources) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  companion object {
    const val TYPE_HEADER = 0
    const val TYPE_ITEM = 1
  }

  private var sets: List<SetCardThreePack> = emptyList()

  fun updateSets(sets: List<SetCardThreePack>) {
    this.sets = emptyList()
    this.sets += sets

    notifyDataSetChanged()
  }

  override fun getItemViewType(position: Int): Int =
    if (isHeaderPosition(position)) TYPE_HEADER else TYPE_ITEM

  private fun isHeaderPosition(position: Int) = position == 0

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      TYPE_HEADER -> createHeaderViewHolder(parent)
      TYPE_ITEM -> createItemViewHolder(parent)
      else -> throw IllegalArgumentException("unknown view type")
    }
  }

  private fun createHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
    val binding = DataBindingUtil.inflate<ItemHeaderBinding>(
      LayoutInflater.from(parent.context),
      R.layout.item_header,
      parent,
      false
    )

    return HeaderViewHolder(binding)
  }

  private fun createItemViewHolder(parent: ViewGroup): SetViewHolder {
    val binding = DataBindingUtil.inflate<ItemSetBinding>(
      LayoutInflater.from(parent.context),
      R.layout.item_set,
      parent,
      false
    )

    return SetViewHolder(binding)
  }

  override fun getItemCount(): Int = sets.size + 1

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (isHeaderPosition(position)) {
      bindHeaderViewHolder(holder as HeaderViewHolder)
    } else {
      bindSetViewHolder(holder as SetViewHolder, position)
    }
  }

  private fun bindSetViewHolder(holder: SetViewHolder, position: Int) {
    val binding = holder.binding
    val set = sets[position - 1]
    var viewModel = binding.viewModel

    // Unbind old  iewModel if we have one
    viewModel?.unbind()

    // Create new ViewModel, set it, and bind it
    viewModel = SetViewModel(set)
    binding.viewModel = viewModel
    viewModel.bind()

    viewModel.clickStream.subscribe {
      binding.viewModel.selected = binding.viewModel.selected.not()
      binding.invalidateAll()
    }

    binding.card1View.setImageDrawable(drawableProvider.provideCardDrawable(viewModel.card1))
    binding.card2View.setImageDrawable(drawableProvider.provideCardDrawable(viewModel.card2))
    binding.card3View.setImageDrawable(drawableProvider.provideCardDrawable(viewModel.card3))
  }

  private fun bindHeaderViewHolder(holder: HeaderViewHolder) {
    val binding = holder.binding
    var viewModel = binding.viewModel

    // Unbind old  iewModel if we have one
    viewModel?.unbind()

    // Create new ViewModel, set it, and bind it
    viewModel = HeaderViewModel(resources.getString(R.string.solver_header))
    binding.viewModel = viewModel
    viewModel.bind()
  }

  class SetViewHolder(val binding: ItemSetBinding) : RecyclerView.ViewHolder(binding.root)

  class HeaderViewHolder(val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root)
}