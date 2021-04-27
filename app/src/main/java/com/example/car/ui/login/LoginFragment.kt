package com.example.car.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.car.R
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }


    private fun bindUI() {
        btLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_usersListFragment)
        }

        val items = listOf("India", "Singapore", "Malaysia", "Australia")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (textCountry.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        //TODO: validate filed to enable button
        btLogin.isEnabled = true
    }

}