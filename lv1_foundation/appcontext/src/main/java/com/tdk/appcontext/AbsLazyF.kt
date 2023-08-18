package com.luckand.mobile.ui.lazy

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class AbsLazyF : Fragment() {
    open val TAG = this.javaClass.simpleName
    private var viewRoot: View? = null
    private var isViewCreated = false
    private var iscurrentvisible = false

    private val lazyLoadFragmentImpl by lazy {
        LazyLoadFragmentImpl(this)
    }
    init {
        lifecycle.addObserver(lazyLoadFragmentImpl)
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        if (viewRoot == null) {
            viewRoot = inflater.inflate(getLayoutRes(), container, false);
        }
        initView(viewRoot!!)
        isViewCreated = true

        if (userVisibleHint) {
            dispatchUserVisibleHint(true)
        }

        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
//        !iscurrentvisible
        Log.d(TAG, "onResume: ")
        if (!iscurrentvisible && userVisibleHint) {
            dispatchUserVisibleHint(true)
        }
    }

    override fun onPause() {
        super.onPause()
        if (iscurrentvisible && !userVisibleHint) {
            dispatchUserVisibleHint(false)
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d(TAG, "setUserVisibleHint() called with: isVisibleToUser = $isVisibleToUser")
        if (isViewCreated) {
            if (!iscurrentvisible && isVisibleToUser) {
                dispatchUserVisibleHint(true)
            } else if (iscurrentvisible && !isVisibleToUser) {
                dispatchUserVisibleHint(false)
            }
        }

    }

    fun dispatchUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser == iscurrentvisible) {
            return
        }
        iscurrentvisible = isVisibleToUser
        if (isVisibleToUser) {
            onFragmentLoad()
            dispatchChildVisibleState(true)
        } else {
            onFragmentStop()
            dispatchChildVisibleState(false)
        }
    }

    fun dispatchChildVisibleState(visible: Boolean) {
        if (childFragmentManager != null) {
            childFragmentManager.fragments.forEach {
                if (it is AbsLazyF && !it.isHidden && it.userVisibleHint) {
                    it.dispatchUserVisibleHint(visible)
                }
            }
        }
    }

    abstract fun initView(view: View)
    abstract fun getLayoutRes(): Int
    abstract fun onFragmentLoad()
    abstract fun onFragmentStop()

}