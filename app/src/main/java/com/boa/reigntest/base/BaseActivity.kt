@file:Suppress("DEPRECATION", "MemberVisibilityCanBePrivate")
package com.boa.reigntest.base

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.boa.reigntest.R
import com.boa.reigntest.util.build
import kotlin.reflect.KCallable

/**
 * Base Fragment for using in Model-View-ViewModel architecture. Must be specified ViewState and ViewModel classes.
 */
abstract class BaseActivity<VS, VM : BaseViewModel<VS>> : AppCompatActivity() {
    lateinit var viewModel: VM
    private var progressDialog: ProgressDialog? = null
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewModel()
        viewModel.resourceViewStatus.observe(this, viewStatusObserver)
        setContentView(getLayoutResource())
    }

    private val viewStatusObserver = Observer<VS> {
        onViewStatusUpdated(it)
    }

    fun showLoading() {
        hideLoading()
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage(getString(R.string.loading))
        progressDialog?.show()
    }

    fun hideLoading() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }

    fun showDialog(
        title: String,
        okText: String,
        cancelText: String = "",
        cancelable: Boolean = false,
        positiveCallBack: KCallable<Unit>? = null
    ) {
        closeDialog()
        dialog = Dialog(this)
        dialog?.build(
            R.layout.view_dialog,
            cancelable,
            title,
            okText,
            cancelText,
            this::closeDialog,
            positiveCallBack
        )
        dialog?.show()
    }

    fun closeDialog() {
        if (dialog != null) {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
                dialog = null
            }
        }
    }

    abstract fun initViewModel(): VM
    abstract fun getLayoutResource(): Int
    abstract fun onViewStatusUpdated(viewStatus: VS)
}
