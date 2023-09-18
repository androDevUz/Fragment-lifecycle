package uz.abubakir_khakimov.fragment_lifecycle.fragments

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import uz.abubakir_khakimov.fragment_lifecycle.FragmentNumberCounter
import uz.abubakir_khakimov.fragment_lifecycle.R
import uz.abubakir_khakimov.fragment_lifecycle.databinding.FragmentMainBinding
import uz.abubakir_khakimov.fragment_lifecycle.navigator
import kotlin.random.Random

class MainFragment : Fragment(), FragmentNumberCounter {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    private var currentColor: Int? = null
    private var currentId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentColor = savedInstanceState?.getInt(CURRENT_COLOR_KEY, -1).let {
            if (it == -1) null else it
        } ?: getRandomColor()

        Log.d("testLifecycle", "${currentId}_MainFragment_onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("testLifecycle", "${currentId}_MainFragment_onCreateView()")

        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("testLifecycle", "${currentId}_MainFragment_onViewCreated()")

        updateUI()

        binding.openNewFragment.setOnClickListener {
            navigator().openMainFragment()
        }

        binding.openDialogActivity.setOnClickListener {
            cameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun updateUI(){
        binding.root.setBackgroundColor(currentColor!!)
        binding.id.text = getString(R.string.id, currentId)
    }

    private fun getRandomColor(): Int = -Random.nextInt(0xFFFFFF)

    override fun numberChangedListener(number: Int) {
        binding.fragmentsNumber.text = getString(R.string.fragments_number, number)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d("testLifecycle", "${currentId}_MainFragment_onSaveInstanceState()")

        outState.putInt(CURRENT_COLOR_KEY, currentColor!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d("testLifecycle", "${currentId}_MainFragment_onDestroyView()")

        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        currentId = arguments?.getInt(ID_KEY)
        Log.d("testLifecycle", "${currentId}_MainFragment_onAttach()")
    }

    override fun onStart() {
        super.onStart()

        Log.d("testLifecycle", "${currentId}_MainFragment_onStart()")
    }

    override fun onResume() {
        super.onResume()

        Log.d("testLifecycle", "${currentId}_MainFragment_onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.d("testLifecycle", "${currentId}_MainFragment_onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.d("testLifecycle", "${currentId}_MainFragment_onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("testLifecycle", "${currentId}_MainFragment_onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()

        Log.d("testLifecycle", "${currentId}_MainFragment_onDetach()")
    }

    companion object {
        const val CURRENT_COLOR_KEY = "current_color"
        const val ID_KEY = "id"

        @JvmStatic
        fun newInstance(id: Int) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ID_KEY, id)
                }
            }
    }
}