package com.example.githubuser.activities

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.adapters.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.models.UserModel

class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView
    private var list: ArrayList<UserModel> = arrayListOf()
    private lateinit var dataName: Array<String>
    private lateinit var dataUserName: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollower: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataAvatar: TypedArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvUser
        rvUser.setHasFixedSize(true)


        stringToArray()
        addItemRecycle()


    }

    private fun addItemRecycle() {
        rvUser.layoutManager = LinearLayoutManager(this)


        for (position in dataName.indices) {
            val user = UserModel(
                dataUserName[position],
                dataName[position],
                dataLocation[position],
                dataCompany[position],
                dataAvatar.getResourceId(position, -1),
                dataFollower[position],
                dataFollowing[position],
                dataRepository[position],

            )
            list.add(user)
        }
        val ListUserAdapter = UserAdapter(list)
        rvUser.adapter = ListUserAdapter
    }

    private fun stringToArray() {
        dataName = resources.getStringArray(R.array.name)
        dataUserName = resources.getStringArray(R.array.username)
        dataCompany = resources.getStringArray(R.array.company)
        dataLocation = resources.getStringArray(R.array.location)
        dataFollower = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
        dataRepository = resources.getStringArray(R.array.repository)
        dataAvatar = resources.obtainTypedArray(R.array.avatar)
    }




}

