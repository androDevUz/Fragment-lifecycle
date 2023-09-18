package uz.abubakir_khakimov.fragment_lifecycle

import androidx.fragment.app.Fragment

interface Navigator {
    fun openMainFragment()
}

fun Fragment.navigator(): Navigator = requireActivity() as Navigator