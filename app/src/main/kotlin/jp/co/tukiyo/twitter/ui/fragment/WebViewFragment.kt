package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebViewClient
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.databinding.FragmentWebViewBinding

@FragmentWithArgs
class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_web_view

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            webView?.run {
                setWebViewClient(WebViewClient())
                loadUrl("https://google.com")
                settings.javaScriptEnabled = true
                setOnKeyListener { view, code, keyEvent ->
                    when(code) {
                        KeyEvent.KEYCODE_BACK -> {
                            if (canGoBack()) goBack()
                        }
                    }
                    return@setOnKeyListener true
                }
            }
            mainActivity.setToolbar(toolbar)
        }
    }
}