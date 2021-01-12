package com.ortizzakarie.dealfinder.viewModel.searchResult.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ortizzakarie.dealfinder.R
import com.ortizzakarie.dealfinder.databinding.ItemGameListingBinding
import com.ortizzakarie.dealfinder.model.dataModels.GameListLookup

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
class GameListAdapter : PagingDataAdapter<GameListLookup, GameListAdapter.GameViewHolder>(
    GAME_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding =
            ItemGameListingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }


    }

    class GameViewHolder(private val binding: ItemGameListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: GameListLookup) {
            binding.apply {

                Glide.with(itemView)
                    .load(game.thumb)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_cloud_off_24)
                    .into(ivGameThumbnail)

                tvGameTitle.text = game.external
                tvGameDiscountPrice.text = game.cheapest

                //TODO: Change the two below when I figure out how to update and get that specific data.
                tvGameRetailPrice.text = "DEV 404"
                tvGameDiscountPercentage.text = "DEV 404"
            }
        }

    }

    companion object {
        private val GAME_COMPARATOR = object : DiffUtil.ItemCallback<GameListLookup>() {

            override fun areItemsTheSame(oldItem: GameListLookup, newItem: GameListLookup) =
                oldItem.gameID == newItem.gameID

            override fun areContentsTheSame(oldItem: GameListLookup, newItem: GameListLookup) =
                oldItem == newItem
        }
    }


}