package com.boa.reigntest.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.transition.TransitionManager
import com.boa.domain.model.News
import com.boa.reigntest.R
import com.boa.reigntest.base.BaseActivity
import com.boa.reigntest.base.OnSelectItem
import com.boa.reigntest.ui.detail.DetailActivity
import com.boa.reigntest.util.ARGUMENT_DETAIL
import com.boa.reigntest.util.ListAdapter
import com.boa.reigntest.util.build
import com.boa.reigntest.util.toast
import com.boa.reigntest.view.Stagger
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.ref.WeakReference

class MainActivity : BaseActivity<MainViewStatus, MainViewModel>(), OnSelectItem<News> {
    private lateinit var listAdapter: ListAdapter<News>

    override fun initViewModel(): MainViewModel = getViewModel()

    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize()
        val contextRef = WeakReference(applicationContext)
        newsList.build(contextRef)
        listAdapter = ListAdapter(contextRef, this)
        newsList.adapter = listAdapter
    }

    override fun onViewStatusUpdated(viewStatus: MainViewStatus) {
        when {
            viewStatus.isLoading -> {
                showLoading()
            }

            viewStatus.isReady -> {
                hideLoading()
            }

            viewStatus.isComplete -> {
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
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(ARGUMENT_DETAIL, item.url)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}