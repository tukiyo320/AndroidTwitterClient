package jp.co.tukiyo.twitter.ui.fragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.disposables.CompositeDisposable
import jp.co.tukiyo.twitter.application.Application
import jp.co.tukiyo.twitter.ui.activity.MainActivity
import jp.co.tukiyo.twitter.viewmodel.FragmentViewModel
import jp.co.tukiyo.twitter.viewmodel.ViewModel

abstract class BaseFragment<BINDING: ViewDataBinding> : RxFragment() {

    abstract val layoutResourceId: Int
    var disposables: CompositeDisposable? = null
    val application: Application
        get() = Application.from(context)
    val mainActivity: MainActivity
        get() = activity as MainActivity
    var binding: BINDING? = null
    private var bindingViewModel : ViewModel? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (bindingViewModel as? FragmentViewModel)?.lifeCycleEvent?.onNext(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (bindingViewModel as? FragmentViewModel)?.lifeCycleEvent?.onNext(FragmentEvent.CREATE)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        disposables = CompositeDisposable()
        (bindingViewModel as? FragmentViewModel)?.lifeCycleEvent?.onNext(FragmentEvent.CREATE_VIEW)
        binding = DataBindingUtil.inflate<BINDING>(inflater, layoutResourceId, container, false)
        return binding?.root
    }

    override fun onStart() {
        (bindingViewModel as? FragmentViewModel)?.lifeCycleEvent?.onNext(FragmentEvent.START)
        super.onStart()
    }

    override fun onResume() {
        (bindingViewModel as? FragmentViewModel)?.lifeCycleEvent?.onNext(FragmentEvent.RESUME)
        super.onResume()
    }

    override fun onPause() {
        (bindingViewModel as? FragmentViewModel)?.lifeCycleEvent?.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        (bindingViewModel as? FragmentViewModel)?.lifeCycleEvent?.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        (bindingViewModel as? FragmentViewModel)?.lifeCycleEvent?.onNext(FragmentEvent.DESTROY_VIEW)
        disposables?.dispose()
        bindingViewModel?.destroy()
        super.onDestroyView()
    }

    override fun onDestroy() {
        (bindingViewModel as? FragmentViewModel)?.lifeCycleEvent?.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
    }

    override fun onDetach() {
        (bindingViewModel as? FragmentViewModel)?.lifeCycleEvent?.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }

    fun bindViewModel(viewModel: ViewModel) {
        bindingViewModel = viewModel
    }
}