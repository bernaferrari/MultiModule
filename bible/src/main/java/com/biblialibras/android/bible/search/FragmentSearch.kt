package com.biblialibras.android.bible.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import com.airbnb.mvrx.fragmentViewModel
import com.biblialibras.android.common.extensions.*
import com.biblialibras.android.common.mvrx.MvRxEpoxyController
import com.biblialibras.android.common.mvrx.simpleController
import com.biblialibras.android.mscore.SharedViewModel
import com.biblialibras.android.mscore.obtainViewModel
import kotlinx.android.synthetic.main.frag_search.*

class FragmentSearch : BaseSearchFragment() {
    lateinit var globalModel: SharedViewModel
    lateinit var kind: String
    private val viewModel: ViewModelSearch by fragmentViewModel()

    override fun epoxyController(): MvRxEpoxyController = simpleController(viewModel) { state ->
        //        when {
//            state.isSearchFieldEmpty -> searchEmptyItem {
//                this.id("first open")
//                this.title("Pesquisar na Bíblia")
//                this.subtitle("Pesquise por livros, versículos, capítulos ou nomes. Exemplo: Rm 5, João 3:16")
//            }
//            state.isLoading -> loadingRow { this.id("loading") }
//            else -> {
//                val list = state.listOfItems()
//
//                if (list?.isEmpty() == true) {
//                    searchNoResults {
//                        this.id("empty")
//                        this.label("Nenhum resultado encontrado")
//                    }
//                }
//
//                var key = -1
//
//                list?.forEach { task ->
//
//                    if (key != task.currentBook) {
//                        key = task.currentBook
//
//                        bibleHeader {
//                            this.id("$key header")
//                            this.title(bibleBooksListed[key])
//                            this.totalCount(-1)
//                        }
//                    }
//
//                    SearchItemBindingModel_()
//                        .id(task.id)
//                        .title(task.title)
//                        .subtitle(
//                            generateTitle(
//                                bibleBooksListed[key],
//                                task.rangeStart,
//                                task.rangeEnd
//                            )
//                        )
//                        .duration(task.duration.formatTime())
//                        .imageUrl(task.id)
//                        .cardColor(selectColor(key))
//                        .clickListener { v ->
////                            globalModel.bibleClickListener(task, ::dismiss)
//                        }
//                        .longClickListener { v ->
////                            task.showDetailsDialog(requireActivity(), true)
//                        }.addTo(this)
//                }
//            }
//        }
    }

    fun selectColor(key: Int): Int {
        return when {
            key == 66 -> 0xFFC6956C.toInt() // apocalipse..
            key >= 58 -> 0xFF49429F.toInt() // hebreus..
            key >= 45 -> 0xFF184110.toInt() // romanos..
            key >= 44 -> 0xFF98429F.toInt() // atos..
            key >= 40 -> 0xFF9F8242.toInt() // mateus..
            else -> 0xFF9E8BE5.toInt()
        }
    }

    override fun onDestroyView() {
        activity?.hideKeyboard()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        globalModel = obtainViewModel(requireActivity())

        queryInput.hint = "Pesquisar..."

        queryInput.onTextChanged { search ->
            queryClear.isInvisible = search.isEmpty()
            viewModel.parseSearch(search)
            recycler.smoothScrollToPosition(0)
        }

        queryClear.setOnClickListener { queryInput.setText("") }

        queryInput.showKeyboardOnView()

        close.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    fun dismiss() {
        globalModel.pausedInOverlay = false
        activity?.supportFragmentManager?.popBackStack()
    }

    companion object {

        fun newInstance(kind: String): FragmentSearch {
            return FragmentSearch()
        }
    }
}
