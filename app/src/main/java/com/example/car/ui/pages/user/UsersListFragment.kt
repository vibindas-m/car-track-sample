package com.example.car.ui.pages.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.car.R
import com.example.car.domain.model.Event
import com.example.car.domain.model.Result
import com.example.car.ui.UserViewModel
import com.example.car.ui.adapter.UserListAdapter
import com.example.car.ui.model.UserDataModel
import kotlinx.android.synthetic.main.users_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UsersListFragment : Fragment() {
    private val viewModel: UserViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.userListEventTrigger.postValue(Event(Unit))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        val bookListAdapter = UserListAdapter()
        bookListAdapter.setOnItemClickListener(object :
            UserListAdapter.OnItemClickListener {
            override fun onClick(view: View, data: UserDataModel) {
                viewModel.selectedUser(data)
                view.findNavController().navigate(R.id.action_usersListFragment_to_mapsFragment)
            }
        })
        recyclerUser.layoutManager = LinearLayoutManager(activity)
        recyclerUser.adapter = bookListAdapter
        bookListAdapter.setData(listOf())
        viewModel.userList.observe(viewLifecycleOwner, {
            it?.let {
                bookListAdapter.setData(it)
            }
        })

        viewModel.userListEvent.observe(viewLifecycleOwner, userListObserver)
    }

    private val userListObserver = Observer<Result<List<UserDataModel>>> {
        if (it is Result.Loading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
            if (it is Result.Success) {
                viewModel.updateUserList(it.data)
            }
            if (it is Result.Failure) {
                showError(it.errorMsg)
            }
        }
    }

    private fun showError(errorMsg: String) {
        errorText.visibility = View.VISIBLE
        errorText.text = errorMsg
    }

}