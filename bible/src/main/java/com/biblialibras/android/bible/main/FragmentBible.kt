package com.biblialibras.android.bible.main

import android.os.Bundle
import android.view.View
import com.airbnb.epoxy.*
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.biblialibras.android.bible.R
import com.biblialibras.android.common.mvrx.MvRxEpoxyController
import com.biblialibras.android.common.mvrx.simpleController
import com.biblialibras.android.bible.search.FragmentSearch
import com.biblialibras.android.mscore.SharedViewModel
import com.biblialibras.android.mscore.obtainViewModel
import kotlinx.android.synthetic.main.elastic_sheet.*
import javax.inject.Inject

class FragmentBible : BaseBibleFragment() {
    lateinit var globalModel: SharedViewModel

    private val viewModel: ViewModelBible by fragmentViewModel()
    @Inject
    lateinit var bibleViewModelFactory: ViewModelBible.Factory

    override fun epoxyController(): MvRxEpoxyController = simpleController(viewModel) { state ->

        //        when (state.listOfItems) {
//            is Loading ->
//                fullPageLoadingRow { this.id("loading") }
//            else ->
//                if (state.listOfItems()?.isEmpty() == true) {
//                    emptyContent {
//                        this.id("empty")
//                        this.label("Ocorreu um erro")
//                    }
//                }
//        }
//
//        val listElements = state.listOfItems.invoke() ?: listOf()
//
//        val grouped = listElements.groupBy { it.currentBook }
//
//        Carousel.setDefaultGlobalSnapHelperFactory(null)
//
//        grouped.keys.sorted().forEach { key ->
//            // val totalDuration = grouped[key]?.sumBy { it.duration.toInt() } ?: 0
//            val totalCount = grouped[key]?.firstOrNull()?.totalCount ?: 0
//
//            val title = when (key) {
//                40 -> "Evangelhos"
//                44 -> "História da Igreja"
//                45 -> "Cartas de Paulo"
//                58 -> "Outras Cartas"
//                66 -> "Profecia"
//                else -> ""
//            }
//
//            if (title != "") {
//                bibleSeparator {
//                    this.id("$key separator")
//                    this.title(title)
//                }
//            }
//
//            bibleHeader {
//                this.id("$key header")
//                this.title(bibleBooksListed[key])
//                this.totalCount(totalCount) // TODO horas
//            }
//
//            val models = mutableListOf<DataBindingEpoxyModel>()
//
//            val size = grouped[key]?.size ?: 0
//
//            val color = when {
//                key == 66 -> 0xFFC6956C.toInt() // apocalipse..
//                key >= 58 -> 0xFF49429F.toInt() // hebreus..
//                key >= 45 -> 0xFF184110.toInt() // romanos..
//                key >= 44 -> 0xFF98429F.toInt() // atos..
//                key >= 40 -> 0xFF9F8242.toInt() // mateus..
//                else -> 0xFF9E8BE5.toInt()
//            }
//
//            val shouldDisplaySeeAll = totalCount > MAX_NUM_OF_ELEMENTS
//
//            grouped[key]?.forEachIndexed { index, task ->
//
//                models += VideoMiniBindingModel_()
//                    .id(task.id)
//                    .title(task.title)
//                    .isFirst(index == 0)
//                    .isLast(index == size - 1 && !shouldDisplaySeeAll) // avoid extra margin since there is the see all element at the end
//                    .duration(task.duration.formatTime())
//                    .imageUrl(task.id)
//                    .cardColor(color)
//                    .clickListener { v ->
////                        globalModel.bibleClickListener(task, ::dismiss)
//                    }
//                    .longClickListener { v ->
////                        task.showDetailsDialog(requireActivity(), true)
//                    }
//            }
//
//            if (shouldDisplaySeeAll) {
//                models += MiniItemSeeMoreBindingModel_()
//                    .id("$key see all")
//                    .cardColor(color)
//            }
//
//            CarouselModel_()
//                .id(key)
//                .models(models)
//                .addTo(this)
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        globalModel = obtainViewModel(requireActivity())
    }

    fun dismiss() {
        globalModel.pausedInOverlay = false
        activity?.supportFragmentManager?.popBackStack()
    }

    companion object {

        fun newInstance(kind: String): FragmentBible {
            return FragmentBible().apply {
                arguments = Bundle(1).apply {
                    putString(KIND, kind)
                }
            }
        }

        const val MAX_NUM_OF_ELEMENTS = 12
    }
}
