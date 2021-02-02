package com.demotask.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demotask.models.User
import com.demotask.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class UsersViewModel @ViewModelInject constructor(
    var usersRepository: UsersRepository
) : ViewModel() {

    private var _usersList = MutableLiveData<List<User>>()
    var usersList: LiveData<List<User>> = _usersList


    fun getUsersList(offset: Int, limit: Int): LiveData<List<User>> {
        viewModelScope.launch(Dispatchers.IO) {
            _usersList.postValue(usersRepository.getUsers(offset, limit)!!.users)
        }

        return usersList
    }

}