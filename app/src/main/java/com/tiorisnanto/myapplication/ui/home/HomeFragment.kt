package com.tiorisnanto.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.tiorisnanto.myapplication.R
import com.tiorisnanto.myapplication.databinding.FragmentHomeBinding
import com.tiorisnanto.myapplication.ui.home.adapter.HomeViewPagerAdapter
import com.tiorisnanto.myapplication.ui.home.fragment.CartFragment
import com.tiorisnanto.myapplication.ui.home.fragment.DashboardFragment
import com.tiorisnanto.myapplication.ui.home.fragment.MessageFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HomeViewPagerAdapter(childFragmentManager)
        binding.viewPager.adapter = adapter
        adapter.addFragment(DashboardFragment(), "Dashboard")
        adapter.addFragment(CartFragment(), "Cart")
        adapter.addFragment(MessageFragment(), "Message")
        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        binding.tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_home_24)
        binding.tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_add_shopping_cart_24)
        binding.tabs.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_chrome_reader_mode_24)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onResume() {
//        super.onResume()
//        codeScanner.startPreview()
//    }
//
//    override fun onPause() {
//        codeScanner.releaseResources()
//        super.onPause()
//    }

}