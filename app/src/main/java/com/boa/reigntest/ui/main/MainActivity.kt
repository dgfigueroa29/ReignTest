package com.boa.reigntest.ui.main

import android.os.Bundle
import androidx.transition.TransitionManager
import com.boa.domain.model.News
import com.boa.reigntest.R
import com.boa.reigntest.base.BaseActivity
import com.boa.reigntest.base.OnSelectItem
import com.boa.reigntest.util.ListAdapter
import com.boa.reigntest.util.build
import com.boa.reigntest.util.toast
import com.boa.reigntest.view.Stagger
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.ref.WeakReference


class MainActivity : BaseActivity<MainViewStatus, MainViewModel>(), OnSelectItem<News> {
    private lateinit var listAdapter: ListAdapter<News>
    private var selectItem: News = News()

    override fun initViewModel(): MainViewModel = getViewModel()

    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLoading()
        viewModel.initialize()
        val contextRef = WeakReference(applicationContext)
        newsList.build(contextRef)
        listAdapter = ListAdapter(contextRef, this)
        newsList.adapter = listAdapter
        swipeContainer.setOnRefreshListener { viewModel.initialize() }
    }

    override fun onViewStatusUpdated(viewStatus: MainViewStatus) {
        when {
            viewStatus.isLoading -> {
                showLoading()
            }

            viewStatus.isReady -> {
                swipeContainer.isRefreshing = false
                hideLoading()
                TransitionManager.beginDelayedTransition(newsList, Stagger())
                listAdapter.setData(viewStatus.newsList)
                hideLoading()

                if (listAdapter.itemCount == 0) {
                    this@MainActivity.toast(getString(R.string.no_internet))
                }
            }

            viewStatus.isError && viewStatus.errorMessage.isNotEmpty() -> {
                hideLoading()
                this@MainActivity.toast(viewStatus.errorMessage)
            }
        }
    }

    override fun onSelectItem(item: News) {
        selectItem = item
        showDialog(
            getString(R.string.delete_news_title),
            getString(R.string.delete),
            getString(R.string.cancel),
            false,
            this::deleteSelectItem
        )
        /*val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(ARGUMENT_DETAIL, item.url)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())*/
    }

    fun deleteSelectItem() {
        viewModel.delete(selectItem.objectID)
        selectItem = News()
    }
}