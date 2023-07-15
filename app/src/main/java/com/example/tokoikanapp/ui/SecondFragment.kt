package com.example.tokoikanapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkPermission
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tokoikanapp.R
import com.example.tokoikanapp.application.FishApp
import com.example.tokoikanapp.databinding.FragmentSecondBinding
import com.example.tokoikanapp.model.Fish
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
 class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {


    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val fishViewModel: FishViewModel by viewModels {
        FishViewModeFactory((applicationContext as FishApp).repository)
    }
    private val args: SecondFragmentArgs by navArgs()
    private var fish: Fish? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLng: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val cameraRequestCode = 2

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
        if (fish != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(fish?.name)
            binding.addresseditText.setText(fish?.address)
            binding.numberEditText.setText(fish?.number)
        }

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

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
            } else {
                if (fish == null) {
                    val fish = Fish(
                        0,
                        name.toString(),
                        address.toString(),
                        number.toString(),
                        currentLatLng?.latitude,
                        currentLatLng?.longitude
                    )
                    fishViewModel.insert(fish)
                } else {
                    val fish =
                        Fish(
                            fish?.id!!,
                            name.toString(),
                            address.toString(),
                            number.toString(),
                            currentLatLng?.latitude,
                            currentLatLng?.longitude
                        )
                    fishViewModel.update(fish)
                }
                findNavController().popBackStack()
            }
        }

        binding.deleteButton.setOnClickListener {
            fish?.let { fishViewModel.delete(it) }
            findNavController().popBackStack()
        }

        binding.camerabutton.setOnClickListener {
            checkCameraPermission()
        }
    }

            override fun onDestroyView() {
                super.onDestroyView()
                _binding = null
            }

            override fun onMapReady(googleMap: GoogleMap) {
                mMap = googleMap

                val uiSettings = mMap.uiSettings
                uiSettings.isZoomControlsEnabled = true
                val sydney = LatLng(-34.0, 151.0)
                mMap.setOnMarkerDragListener(this)
            }

           override fun onMarkerDrag(p0: Marker) {}

            override fun onMarkerDragEnd(marker: Marker) {
                val newPosition = marker.position
                currentLatLng = LatLng(newPosition.latitude, newPosition.longitude)
                Toast.makeText(context, currentLatLng.toString(), Toast.LENGTH_SHORT).show()
            }

           override fun onMarkerDragStart(p0: Marker) {}


           private fun checkPermission() {

                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(applicationContext)
                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getCurrentLocation()
                }else {
                    Toast.makeText(applicationContext, "Akses lokasi ditolak", Toast.LENGTH_SHORT)
                        .show()
                }
            }

           private fun getCurrentLocation() {
                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        if (location != null) {
                            var latLng = LatLng(location.latitude, location.longitude)
                            currentLatLng = latLng
                            var title = "Marker"

                            if (fish != null) {
                                title = fish?.name.toString()
                                val newCurrentLocation = LatLng(fish?.latitude!!, fish?.longitude!!)
                                latLng = newCurrentLocation
                            }
                            val markerOption = MarkerOptions()
                                .position(latLng)
                                .title(title)
                                .draggable(true)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_fish_30))
                            mMap.addMarker(markerOption)
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        }
                    }
            }

           private fun checkCameraPermission() {
                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        android.Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED) {
                    activity?.let {
                        ActivityCompat.requestPermissions(
                            it,
                            arrayOf(android.Manifest.permission.CAMERA),
                            cameraRequestCode
                        )
                    }
                } else {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, cameraRequestCode)
                }
            }

           override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
                if (requestCode == cameraRequestCode) {
                    val photo: Bitmap = data?.extras?.get("data") as Bitmap
                    binding.photoimageView.setImageBitmap(photo)
        }
    }
}