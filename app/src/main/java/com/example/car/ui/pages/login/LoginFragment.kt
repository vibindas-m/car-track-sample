package com.example.car.ui.pages.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.car.MainActivity
import com.example.car.R
import com.example.car.domain.model.Event
import com.example.car.domain.model.Result
import com.example.car.ui.UserViewModel
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {

    private val viewModel: UserViewModel by sharedViewModel()
    private lateinit var mActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity
        bindUI()
    }


    private fun bindUI() {
        viewModel.validateUserEvent.observe(viewLifecycleOwner, validateUserObserver)
        btLogin.setOnClickListener {
            validateUser(
                Pair(
                    textUserName.editText?.text?.toString() ?: "",
                    textPassword.editText?.text?.toString() ?: ""
                )
            )
        }

        val items = viewModel.getCountryList()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (textCountry.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        textUserName.editText?.onTextChangeValidate()
        textPassword.editText?.onTextChangeValidate()
        textCountry.editText?.onTextChangeValidate()
        viewModel.enableLogin.observe(viewLifecycleOwner, {
            btLogin.isEnabled = it == true
        })
        validateFields()
    }

    private fun validateUser(userNameAndPwd: Pair<String, String>) {
        viewModel.validateUserEventTrigger.postValue(Event(userNameAndPwd))
    }

    private fun validateFields() {
        viewModel.validateFields(
            textUserName.editText?.text?.toString(),
            textPassword.editText?.text?.toString(), textCountry.editText?.text?.toString()
        )
    }

    private val validateUserObserver = Observer<Event<Result<Boolean>>> {
        it.getContentIfNotHandled()?.let { result ->
            if (result is Result.Loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
                if (result is Result.Success) {
                    Navigation.findNavController(mActivity, R.id.nav_host_fragment).navigate(R.id.action_loginFragment_to_usersListFragment)
                }
                if (result is Result.Failure) {
                    showError(result.errorMsg)
                }
            }
        }
    }

    private fun showError(mes: String) {
        context?.let {
            AlertDialog.Builder(it).setTitle("Failed").setMessage(mes).setPositiveButton(
                "OK"
            ) { _, _ -> clearFields() }.show()
        }

    }

    private fun clearFields() {
        textUserName.editText?.text?.clear()
        textPassword.editText?.text?.clear()
    }

    private fun EditText.onTextChangeValidate() {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validateFields()
            }

        })
    }

}