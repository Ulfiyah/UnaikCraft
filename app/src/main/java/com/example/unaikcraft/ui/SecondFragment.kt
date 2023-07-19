package com.example.unaikcraft.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unaikcraft.R
import com.example.unaikcraft.application.UnaikApp
import com.example.unaikcraft.databinding.FragmentSecondBinding
import com.example.unaikcraft.model.CraftModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext : Context
    private val unaikViewModel : UnaikViewModel by viewModels {
        UnaikViewModelFactory((applicationContext as UnaikApp).repository)
    }

    private val args: SecondFragmentArgs by navArgs()
    private var craft: CraftModel? = null
   //var untuk map
    private lateinit var mMap: GoogleMap
    private var currentLatlng : LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
// ambil text yang dimasukkan di edit text
        craft = args.craft
        if (craft != null) {
            binding.deleteButton.visibility= View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.editTextText.setText(craft?.nama)
            binding.editTextText2.setText(craft?.alamat)
            binding.editTextText3.setText(craft?.nohp)
        }
        //binding google map edit text
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()
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
                    val craft = CraftModel(0, nama.toString(), alamat.toString(), nohp.toString(),currentLatlng?.latitude,currentLatlng?.longitude)
                    unaikViewModel.insert(craft)
                } else {
                    val craft = CraftModel(craft?.id!!, nama.toString(), alamat.toString(), nohp.toString(),currentLatlng?.latitude,currentLatlng?.longitude)
                    unaikViewModel.update(craft)
                }
                findNavController().popBackStack()
            }
        }
// binding delete button
        binding.deleteButton.setOnClickListener {
            craft?.let { unaikViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // google map
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //implement drag marker
        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerDragListener(this)
        val sydney = LatLng(-34.0,151.0)
        val markerOption = MarkerOptions()
            .position(sydney)
            .title("test")
            .draggable(true)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_craft))
        mMap.addMarker(markerOption)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f))

    }

    override fun onMarkerDrag(p0: Marker) {

    }

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatlng = LatLng(newPosition.latitude,newPosition.longitude)
        Toast.makeText(context, currentLatlng.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {

    }

    private fun checkPermission(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ){
            getCurrentLocation()
        }else{
            Toast.makeText(applicationContext,"akses lokasi ditolak",Toast.LENGTH_SHORT).show()
        }
    }
    private fun getCurrentLocation(){
        // ngecek jika permission tidak disetujui maka akan berhenti dikondisi if
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    var latLang = LatLng(location.latitude, location.longitude)
                    currentLatlng = latLang
                    var title = "Marker"

                    if (craft!=null){
                        title=craft?.nama.toString()
                        val newCurrentLocation = LatLng(craft?.latitude!!,craft?.longitude!!)
                        latLang=newCurrentLocation
                    }
                    val markerOption =MarkerOptions()
                        .position(latLang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_craft))
                    mMap.addMarker(markerOption)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang,15f))
                }
            }
    }
}
