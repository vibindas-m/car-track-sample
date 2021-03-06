package com.example.car.ui.pages.details

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.car.R
import com.example.car.ui.UserViewModel
import com.example.car.ui.model.UserDataModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.user_details.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MapsFragment : Fragment() {

    private val viewModel: UserViewModel by sharedViewModel()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        viewModel.selectedUser.observe(viewLifecycleOwner, {
            it?.let {
                googleMap.addMarker(
                    MarkerOptions().position(it.latLang).title(it.userName).icon(
                        bitmapFromVector(
                            requireContext(),
                            R.drawable.ic_car_pointer
                        )
                    ).visible(true).draggable(false)
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(it.latLang))
                showBottomSheet(it)
            }
        })

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f))
        googleMap.uiSettings.isScrollGesturesEnabled = false

    }

    private fun showBottomSheet(userDataModel: UserDataModel) {
        useName.text = userDataModel.userName
        userAddress.text = userDataModel.address
        userCompany.text = userDataModel.company
        webBt.apply {
            text = userDataModel.web
            setOnClickListener {
                openURL(userDataModel.web)
            }
        }

        mailBt.apply {
            text = userDataModel.mail
            setOnClickListener {
                openMail(userDataModel.mail)
            }
        }
        callBt.apply {
            text = userDataModel.mobile
            setOnClickListener {
                openDial(userDataModel.mobile)
            }
        }
    }

    private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            72,
            72
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            72,
            72,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun openURL(url: String) {
        val finalUrl = if (!url.startsWith("https://") && !url.startsWith("http://")){
            "http://$url"
        } else url
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(finalUrl)
        activity?.startActivity(intent)
    }

    private fun openDial(mobile: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$mobile")
        activity?.startActivity(intent)
    }

    private fun openMail(mail: String) {
        val emailIntent = Intent(Intent.ACTION_SEND);
        emailIntent.type = "plain/text"
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "to@email.com")
        activity?.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        bottomSheetBehavior = BottomSheetBehavior.from(userDetailBottomSheet)
    }
}