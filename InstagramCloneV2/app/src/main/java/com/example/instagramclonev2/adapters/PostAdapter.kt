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

class PostAdapter(private val InstaPosts: MutableList<InstaPostsFB>)
    : RecyclerView.Adapter<PostAdapter.RecyclerViewHolder>() {
    class RecyclerViewHolder ( val binding: InstagramPostItemBinding) : RecyclerView.ViewHolder(binding.root)
    private var isLiked = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = InstagramPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        val zoomInAnim = AnimationUtils.loadAnimation(parent.context, R.anim.zoom_in)

        binding.ivPostImg.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View?) {
                binding.heart.setImageResource(R.drawable.baseline_favorite_small_red)
                binding.insideHeart.startAnimation(zoomInAnim)
                isLiked = true
            }
        })
        binding.heart.setOnClickListener {
            val posts = InstaPosts[position]

            if (isLiked) {
                binding.heart.setImageResource(R.drawable.baseline_favorite_border_24)
            } else {
                binding.heart.setImageResource(R.drawable.baseline_favorite_small_red)
                binding.insideHeart.startAnimation(zoomInAnim)
            }

            isLiked = !isLiked
        }
        return RecyclerViewHolder(binding)
    }

    abstract class DoubleClickListener : View.OnClickListener {
        private var lastClickTime : Long = 0
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

    override fun getItemCount(): Int = InstaPosts.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        Log.i("Post adapter", "Hi")
        val post = InstaPosts[position]
        holder.binding.tvPostUserName.text = post.user?.name
        holder.binding.tvUserPostName.text = post.user?.name
        holder.binding.ivUserPostImg.load(post.user?.profileImg)
        holder.binding.ivPostImg.load(post.imageUrl)
        holder.binding.tvLikeCount.text = post.likes.toString()
        holder.binding.tvPostCaption.text = post.caption
        holder.binding.tvRelativeTime.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMs)
    }
}