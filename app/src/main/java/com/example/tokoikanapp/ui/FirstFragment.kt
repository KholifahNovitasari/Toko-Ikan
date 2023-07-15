package com.example.tokoikanapp.ui

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokoikanapp.R
import com.example.tokoikanapp.application.FishApp
import com.example.tokoikanapp.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val fishViewModel: FishViewModel by viewModels {
        FishViewModeFactory((applicationContext as FishApp).repository)
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

        val adapter = FishListAdapter{ fish ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(fish)
            findNavController().navigate(action)
        }
        binding.dataRecyclerView.adapter = adapter
        binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
        fishViewModel.allFish.observe(viewLifecycleOwner){ fish ->
            fish.let {
                if (fish.isEmpty()) {
                    binding.emptytextView.visibility = View.VISIBLE
                    binding.ilustrationImageView.visibility = View.VISIBLE
                } else {
                    binding.emptytextView.visibility = View.GONE
                    binding.ilustrationImageView.visibility = View.GONE
                }
                adapter.submitList(fish)
            }
        }

       binding.addFAB.setOnClickListener {
         val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
           findNavController().navigate(action)
        }
        binding.contactFAB.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ContactFragment)
        }
        binding.catalogFAB.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_CatalogFragment)
        }
        binding.aboutFAB.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_AboutFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}