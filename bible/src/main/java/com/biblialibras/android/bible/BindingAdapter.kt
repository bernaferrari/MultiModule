package com.biblialibras.android.bible

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("fetchYTPreviewFromGlide")
fun loadYTImageFromUrl(view: ImageView, id: String?) {
    glideLoader(view, id != null, "https://i.ytimg.com/vi/$id/mqdefault.jpg")
}

fun glideLoader(view: ImageView, notNull: Boolean, url: String) {
    if (notNull) {
        GlideApp.with(view)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.error_glide)
            .into(view)
    } else {
        GlideApp.with(view).clear(view)
    }
}

@BindingAdapter("boxedGifSize")
fun customBoxSize(view: FrameLayout, width: Int) {
    val params = ViewGroup.LayoutParams(width, width)
    view.layoutParams = params
}

@BindingAdapter("strFromTotalCount")
fun loadStrFromTotalCount(view: TextView, totalCount: Int) {
    view.text = view.resources.getQuantityString(R.plurals.video_quantity, totalCount, totalCount)
}

@BindingAdapter("isItemVisible")
fun isItemVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("srcRes")
fun imageViewSrcRes(view: ImageView, drawableRes: Int) {
    if (drawableRes != 0) {
        view.setImageResource(drawableRes)
    } else {
        view.setImageDrawable(null)
    }
}

@BindingAdapter("visibilityFromTotalCount")
fun visibilityFromTotalCount(view: Button, totalCount: Int) {
    view.visibility = if (totalCount > 8 || totalCount == -1) View.VISIBLE else View.GONE
}

fun dp(value: Int, resources: Resources) = (resources.displayMetrics.density * value).toInt()

@BindingAdapter("customMargin")
fun customMargin(view: FrameLayout, index: Int) {

    if (index >= 0) {
        val dp8 = dp(4, view.resources)
        val dp16 = dp(16, view.resources)
        val dp128 = dp(128, view.resources)

        val lp = FrameLayout.LayoutParams(
            dp128,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )

        when (index) {
            0 -> lp.setMargins(dp16, 0, dp8, 0)
            else -> lp.setMargins(0, 0, dp8, 0)
        }

        view.layoutParams = lp
    } else {
        val dp4 = dp(4, view.resources)

        val lp = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )

        lp.setMargins(dp4, dp4, dp4, dp4)
        view.layoutParams = lp
    }
}

@BindingAdapter(value = ["isFirstElement", "isLastElement"])
fun customCardMargin(view: FrameLayout, isFirst: Boolean, isLast: Boolean) {

    val dp8 = dp(4, view.resources)
    val dp16 = dp(16, view.resources)
    val dp128 = dp(128, view.resources)

    val lp = FrameLayout.LayoutParams(
        dp128,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )

    when {
        isFirst -> lp.setMargins(dp16, 0, dp8, 0)
        isLast -> lp.setMargins(0, 0, dp16, 0)
        else -> lp.setMargins(0, 0, dp8, 0)
    }

    view.layoutParams = lp
}
