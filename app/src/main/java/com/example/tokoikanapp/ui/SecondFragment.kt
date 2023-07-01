package com.example.tokoikanapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tokoikanapp.R
import com.example.tokoikanapp.application.FishApp
import com.example.tokoikanapp.databinding.FragmentSecondBinding
import com.example.tokoikanapp.model.Fish

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val fishViewModel: FishViewModel by viewModels {
        FishViewModeFactory((applicationContext as FishApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var fish: Fish? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fish = args.fish
        if (fish != null){
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(fish?.name)
            binding.addresseditText.setText(fish?.address)
            binding.numberEditText.setText(fish?.number)
        }
        val name = binding.nameEditText.text
        val address = binding.addresseditText.text
        val number = binding.numberEditText.text
        binding.saveButton.setOnClickListener {
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()) {
                Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (number.isEmpty()) {
                Toast.makeText(context, "No.Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else{
                if (fish == null) {
                    val fish = Fish(0, name.toString(), address.toString(), number.toString())
                    fishViewModel.insert(fish)
                } else {
                    val fish =
                        Fish(fish?.id!!, name.toString(), address.toString(), number.toString())
                    fishViewModel.update(fish)
                }
                findNavController().popBackStack()// untuk dismiss halaman ini
            }
        }

        binding.deleteButton.setOnClickListener{
            fish?.let { fishViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}