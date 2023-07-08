package com.example.unaikcraft.ui

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
import com.example.unaikcraft.R
import com.example.unaikcraft.application.UnaikApp
import com.example.unaikcraft.databinding.FragmentSecondBinding
import com.example.unaikcraft.model.CraftModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding ge t() = _binding!!
    private lateinit var applicationContext : Context
    private val unaikViewModel : UnaikViewModel by viewModels {
        UnaikViewModelFactory((applicationContext as UnaikApp).repository)
    }
    private val args: SecondFragmentArgs by navArgs()
    private var craft: CraftModel? = null
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

        craft = args.craft
        if (craft != null) {
            binding.deleteButton.visibility= View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.editTextText.setText(craft?.nama)
            binding.editTextText2.setText(craft?.alamat)
            binding.editTextText3.setText(craft?.nohp)
        }
        val nama = binding.editTextText.text
        val alamat  = binding.editTextText2.text
        val nohp = binding.editTextText3.text
        binding.saveButton.setOnClickListener {
            if (nama.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (alamat.isEmpty()){
                Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (nohp.isEmpty()){
                Toast.makeText(context, "Nomer Hp/WA tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (craft == null) {
                    val craft = CraftModel(0, nama.toString(), alamat.toString(), nohp.toString())
                    unaikViewModel.insert(craft)
                } else {
                    val craft = CraftModel(craft?.id!!, nama.toString(), alamat.toString(), nohp.toString())
                    unaikViewModel.update(craft)
                }
                findNavController().popBackStack()
            }
        }

        binding.deleteButton.setOnClickListener {
            craft?.let { unaikViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}