package uz.abubakir_khakimov.fragment_lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import uz.abubakir_khakimov.fragment_lifecycle.databinding.ActivityMainBinding
import uz.abubakir_khakimov.fragment_lifecycle.fragments.MainFragment
import uz.abubakir_khakimov.fragment_lifecycle.fragments.SecondFragment

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding
    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentViewCreated(fm: FragmentManager, fragment: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, fragment, v, savedInstanceState)

            if (fragment is MainFragment) updateFragmentNumber()
        }
    }
    private var id = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = savedInstanceState?.getInt(MainFragment.ID_KEY) ?: 1

        if (savedInstanceState == null){
            openMainFragment()
            openSecondFragment()
        }
    }

    override fun openMainFragment(){
        openFragment(binding.mainContainerView.id, MainFragment.newInstance(id++))
    }

    private fun openSecondFragment(){
        openFragment(binding.secondContainerView.id, SecondFragment::class.java)
    }

    private fun openFragment(container: Int, fragmentClass: Class<out Fragment>){
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(container, fragmentClass, null)
            .commit()
    }

    private fun openFragment(container: Int, fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(container, fragment, null)
            .commit()
    }

    private fun updateFragmentNumber(){
        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_container_view)
        if (currentFragment is FragmentNumberCounter) currentFragment
            .numberChangedListener(supportFragmentManager.backStackEntryCount - 1)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(MainFragment.ID_KEY, id)
    }
}