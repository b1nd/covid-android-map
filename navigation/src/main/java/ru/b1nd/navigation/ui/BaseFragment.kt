package ru.b1nd.navigation.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {

    abstract val layoutRes: Int

    abstract val shouldShowNavigationBar: Boolean

    private var instanceStateSaved: Boolean = false

    private val viewHandler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutRes, container, false)

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewHandler.removeCallbacksAndMessages(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
    }

    open fun onBackPressed() {}
}
