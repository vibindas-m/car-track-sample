package com.example.car.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.findNavController
import com.example.car.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        carMotionLayout.apply {
            setTransitionListener(object : MotionLayout.TransitionListener{
                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
//                    TODO("Not yet implemented")
                }

                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
//                    TODO("Not yet implemented")
                }

                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                    view?.findNavController()?.navigate(R.id.action_mainFragment_to_loginFragment)
                }

                override fun onTransitionTrigger(
                    p0: MotionLayout?,
                    p1: Int,
                    p2: Boolean,
                    p3: Float
                ) {
//                    TODO("Not yet implemented")
                }

            })
        }

    }

}