package com.example.daggermvvm.network.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daggermvvm.R
import com.example.daggermvvm.databinding.PostListLayoutBinding
import com.example.daggermvvm.network.model.WifiStation
import com.example.daggermvvm.network.viewmodel.RetroViewModel
import com.example.daggermvvm.network.viewmodel.RetroViewModelFactory
import kotlinx.android.synthetic.main.post_list_layout.view.*
import java.util.*
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission

class RetroFragment : Fragment() {
    lateinit var retroViewModel: RetroViewModel
    var fragmentView: View? = null
    private var listAdapter: PostListAdapter? = null
    private var postListLayoutBinding: PostListLayoutBinding? = null
    private var wifiReceiverRegistered: Boolean = false
    var wifiManager: WifiManager? = null

    companion object {
        private const val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 120
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postListLayoutBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.post_list_layout, container, false
        )
        fragmentView = postListLayoutBinding?.root
        initWifi()
        initAdapter()
        setAdapter()
        //fetchRetroInfo()
        return fragmentView
    }

    override fun onStart() {
        super.onStart()
        startScanning()
    }

    override fun onStop() {
        super.onStop()
        if (wifiReceiverRegistered) {
            activity?.unregisterReceiver(wifiReceiver)
            wifiReceiverRegistered = false
        }
    }

    private val wifiReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val results = wifiManager?.scanResults as ArrayList<ScanResult>

            if (results != null) {
                listAdapter?.setAdapterList(WifiStation.newList(Collections.unmodifiableList(results)))
            }
        }
    }

    fun initWifi() {
        wifiManager =
            context?.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (wifiManager?.isWifiEnabled == false) {
            wifiManager?.isWifiEnabled = true
        }
    }

    private fun startScanning() {
        if (checkPermissions()) {
            activity?.registerReceiver(
                wifiReceiver,
                IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
            )
            wifiReceiverRegistered = true
            wifiManager?.startScan()
        }
    }


    fun initViewModel() {
        var retroViewModelFactory = RetroViewModelFactory()
        retroViewModel =
            ViewModelProviders.of(this, retroViewModelFactory).get(RetroViewModel::class.java)
    }

    fun fetchRetroInfo() {
        /*retroViewModel.postInfoLiveData?.observe(this,object: Observer<List<PostInfo>> {
        override fun onChanged(t: List<PostInfo>?) {
            t?.apply {
                listAdapter?.setAdapterList(t)
            }
        }
    })*/
        /*val listPostInfo = listOf<PostInfo>(PostInfo("VCS2 APP NETWORK","Secure Network",""),
        PostInfo("Connections Team network","Secure Network",""),
        PostInfo("Trailer Team network","Secure Network",""),
        PostInfo("Vehicle Team network","Secure Network",""),
        PostInfo("Projection Team network","Secure Network",""),
        PostInfo("Pawan","Secure Network",""),
        PostInfo("Pratap network","Secure Network",""),
        PostInfo("Sudhanshu Team network","Secure Network",""),
        PostInfo("Chandrika Team network","Secure Network",""),
        PostInfo("Chandrakanth Team network","Secure Network",""),
        PostInfo("Harish","Secure Network","")
    );*/

        //listAdapter?.setAdapterList(listPostInfo)
    }

    private fun setAdapter() {
        fragmentView?.post_list?.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = listAdapter
        }
    }

    private fun initAdapter() {
        listAdapter = PostListAdapter(this@RetroFragment.requireActivity())
    }

    private fun checkPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PermissionChecker.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
            )
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION -> {
                startScanning()
            }
        }
    }
}
