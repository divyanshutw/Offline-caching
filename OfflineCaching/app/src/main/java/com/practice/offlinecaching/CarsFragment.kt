package com.practice.offlinecaching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.practice.offlinecaching.databinding.FragmentCarsBinding


class CarsFragment : Fragment() {

    private var type:String?=null

    private lateinit var binding: FragmentCarsBinding
    private lateinit var viewModel:CarsViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cars,
            container,
            false
        )
        binding.lifecycleOwner = this


        viewModel=ViewModelProvider(this, CarsViewModelFactory(requireActivity().application, type, "token"))
            .get(CarsViewModel::class.java)

        initObservers()

        binding.save.setOnClickListener { onClickSave() }


        return binding.root
    }

    private fun initObservers() {
        //observer for cars loading
        viewModel.carsTypeList.observe(viewLifecycleOwner, Observer {
            if (viewModel.carsTypeList.value == null || viewModel.carsTypeList.value!!.isEmpty()) {
                viewModel.isRepositoryEmpty.value = true
                loadData()
            }
            else
            {
                viewModel.isRepositoryEmpty.value = false
                addCarsToUI()
            }

        })

        viewModel.isSavingToDatabase.observe(viewLifecycleOwner,Observer{
            if(!it) {
                if (viewModel.isCarSavedToDatabase)
                {
                    Toast.makeText(requireContext(), "Successfully updated in the remote server", Toast.LENGTH_LONG).show()
                    loadData()
                }
                else //Some error occurred while uploading to the remote database
                    Toast.makeText(requireContext(), "Something went wrong. Try again after sometime", Toast.LENGTH_LONG).show()
            }
            //If you want you may update the local room database as well after this
        })
    }

    private fun loadData() {
        if (AppNetworkStatus.getInstance(requireContext()).isOnline) {
            showLoadingDialog()
            viewModel.refresh()
        }
        else{
            showInternetNotConnectedDialog()
        }
    }



    private fun addCarsToUI()
    {
        //show the data
    }

    private fun onClickSave() {
        val cars=Cars(1,"","","")
        viewModel.isSavingToDatabase.value=true
        showUploadingDialog()
        viewModel.uploadData(cars)
    }




    private fun showInternetNotConnectedDialog() {
        //show Internet not connected dialog
    }
    private fun showUploadingDialog() {
        //show Uploading dialog
        viewModel.isSavingToDatabase.observe(viewLifecycleOwner, Observer {
            if (!it) {
                //dismissDialog()
            }
        })

    }

    private fun showLoadingDialog() {
        //show Loading dialog
        viewModel.isRepositoryEmpty.observe(viewLifecycleOwner, Observer {
            if (!it) {
                //dismissDialog()
            }
        })
    }
}