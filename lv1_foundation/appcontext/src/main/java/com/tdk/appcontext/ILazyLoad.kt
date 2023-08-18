package com.luckand.mobile.ui.lazy

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

interface ILazyLoadLifecycle:Li {

    fun dispatchUserVisibleHint(isVisibleToUser:Boolean)

    fun dispatchChildVisibleState(visible:Boolean)

    fun onLazyLoadEvent(event: Event)
    public enum Event {
        RESUME,STOP
    }
}



class LazyLoadFragmentImpl(val fragment: Fragment):ILazyLoad, LifecycleEventObserver {
    var isViewCreated = false
    var iscurrentvisible=false

    override fun dispatchUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser == iscurrentvisible) {
            return
        }
        iscurrentvisible = isVisibleToUser
        if (isVisibleToUser) {
            onFragmentLazyLoad()
            dispatchChildVisibleState(true)
        } else {
            onFragmentStop()
            dispatchChildVisibleState(false)
        }
    }

    override fun dispatchChildVisibleState(visible: Boolean) {
        if (fragment.childFragmentManager != null) {
            fragment.childFragmentManager.fragments.forEach {
                if (it is AbsLazyF&&!it.isHidden&&it.userVisibleHint){
                    it.dispatchUserVisibleHint(visible)
                }
            }
        }
    }

    override fun onFragmentLazyLoad() {

    }

    override fun onFragmentStop() {

    }


    //fragment的setUserVisibleHint()
    fun fragmentSetUserVisibleHint(isVisibleToUser:Boolean){
        if (isViewCreated) {
            if (!iscurrentvisible && isVisibleToUser) {
                dispatchUserVisibleHint(true)
            } else if (iscurrentvisible && !isVisibleToUser) {
                dispatchUserVisibleHint(false)
            }
        }
    }

    //fragment的getUserVisibleHint()
    fun fragmentUserVisibleHint()=fragment.userVisibleHint

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event){
            Lifecycle.Event.ON_CREATE->{
                isViewCreated = true
                if (fragmentUserVisibleHint()) {
                    dispatchUserVisibleHint(true)
                }
            }
            Lifecycle.Event.ON_RESUME->{
                if (!iscurrentvisible && fragmentUserVisibleHint()) {
                    dispatchUserVisibleHint(true)
                }
            }

            Lifecycle.Event.ON_PAUSE->{
                if (iscurrentvisible && !fragmentUserVisibleHint()) {
                    dispatchUserVisibleHint(false)
                }
            }

            else->{

            }
        }
    }
}