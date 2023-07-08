package com.example.unaikcraft.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unaikcraft.R
import com.example.unaikcraft.application.UnaikApp
import com.example.unaikcraft.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val unaikViewModel : UnaikViewModel by viewModels {
        UnaikViewModelFactory((applicationContext as UnaikApp).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UnaikListAdapter { craft ->
            val action= FirstFragmentDirections.actionFirstFragmentToSecondFragment(craft)
            findNavController().navigate(action)
        }
        binding.recyclerView2.adapter = adapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(context)
        unaikViewModel.allcraft.observe(viewLifecycleOwner) { crafter ->
            crafter.let{
                if (crafter.isEmpty()) {
                    binding.welcome.visibility = View.VISIBLE
                    binding.imageView3.visibility = View.VISIBLE
                } else {
                    binding.welcome.visibility = View.GONE
                    binding.imageView3.visibility = View.GONE
                }
                adapter.submitList(crafter)
            }
        }

        binding.arrow.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
           findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}