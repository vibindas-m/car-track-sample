package com.example.car.ui.user

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.car.R
import com.example.car.ui.adapter.UserListAdapter
import com.example.car.ui.model.UserDataModel
import kotlinx.android.synthetic.main.users_list_fragment.*

class UsersListFragment : Fragment() {

    companion object {
        fun newInstance() = UsersListFragment()
    }

    private lateinit var viewModel: UsersListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersListViewModel::class.java)
        bindUI()
    }

    private fun bindUI() {
        val bookListAdapter = UserListAdapter()
        bookListAdapter.setOnItemClickListener(object :
            UserListAdapter.OnItemClickListener {
            override fun onClick(view: View, data: UserDataModel) {
                view.findNavController().navigate(R.id.action_usersListFragment_to_detailsFragment)
            }
        })
        recyclerUser.layoutManager = LinearLayoutManager(activity)
        recyclerUser.adapter = bookListAdapter
        bookListAdapter.setData(listOf(UserDataModel(userName = "Leanne Graham", "1-770-736-8031 x56442"),
            UserDataModel(userName = "LErvin Howell", "010-692-6593 x09125"),
            UserDataModel(userName = "Clementine Bauch", "1-463-123-4447")))
    }

}