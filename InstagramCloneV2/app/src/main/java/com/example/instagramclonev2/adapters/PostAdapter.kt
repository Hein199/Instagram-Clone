package com.example.instagramclonev2.adapters

import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.instagramclonev2.R
import com.example.instagramclonev2.databinding.InstagramPostItemBinding
import com.example.instagramclonev2.models.InstaPostsFB

class PostAdapter(private val InstaPosts: MutableList<InstaPostsFB>) :
    RecyclerView.Adapter<PostAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(val binding: InstagramPostItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding =
            InstagramPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    private fun likePost(post: InstaPostsFB, isLiked: Boolean, holder: RecyclerViewHolder) {
        val zoomInAnim = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.zoom_in)
        if (isLiked) {
            post.likes++
            holder.binding.tvLikeCount.text = post.likes.toString()
            holder.binding.heart.setImageResource(R.drawable.baseline_favorite_small_red)
            holder.binding.insideHeart.startAnimation(zoomInAnim)
        } else {
            post.likes--
            holder.binding.tvLikeCount.text = post.likes.toString()
            holder.binding.heart.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    override fun getItemCount(): Int = InstaPosts.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val post = InstaPosts[position]
        var isLiked = false
        holder.binding.tvLikeCount.text = post.likes.toString()
        holder.binding.heart.setImageResource(if (isLiked) R.drawable.baseline_favorite_small_red else R.drawable.baseline_favorite_border_24)
        holder.binding.tvPostUserName.text = post.user?.name
        holder.binding.tvUserPostName.text = post.user?.name
        holder.binding.ivUserPostImg.load(post.user?.profileImg)
        holder.binding.ivPostImg.load(post.imageUrl)
        holder.binding.tvPostCaption.text = post.caption
        holder.binding.tvRelativeTime.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMs)

        holder.binding.ivPostImg.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View?) {
                if (!isLiked) {
                    isLiked = true
                    likePost(post, isLiked, holder)
                }
            }
        })

        holder.binding.heart.setOnClickListener {
            isLiked = !isLiked
            likePost(post, isLiked, holder)
        }
    }

    abstract class DoubleClickListener : View.OnClickListener {
        private var lastClickTime: Long = 0
        companion object {
            private const val DOUBLE_CLICK_TIME_DELTA = 300
        }

        override fun onClick(v: View?) {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                onDoubleClick(v)
            }
            lastClickTime = clickTime
        }

        abstract fun onDoubleClick(v: View?)
    }
}
