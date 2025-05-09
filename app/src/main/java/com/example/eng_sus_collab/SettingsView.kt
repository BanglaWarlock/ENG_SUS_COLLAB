package com.example.eng_sus_collab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsView.newInstance] factory method to
 * create an instance of this fragment.
 */
interface onAdminClicks {
    fun onBecomeAdmin()
    fun onLogout()
    fun onSupport()
}

class SettingsView : Fragment() {
    // TODO: Rename and change types of parameters
    private var is_admin: Boolean? = null
    private var param2: String? = null

    private lateinit var listener: onAdminClicks
    private lateinit var logout: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            is_admin = it.getBoolean(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_settings_view, container, false)

        listener = context as onAdminClicks

        var support = view.findViewById<FrameLayout>(R.id.rowSupport)
        support.setOnClickListener {
            listener.onSupport()
        }

        var admin = view.findViewById<FrameLayout>(R.id.rowAdmin)
        admin.setOnClickListener {
            listener.onBecomeAdmin()
        }

        var logout = view.findViewById<FrameLayout>(R.id.rowLogout)
        logout.setOnClickListener {
            listener.onLogout()
        }

        // if not admin
        if (!is_admin!!) {
            logout.visibility = View.GONE
        }

        return view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Boolean, param2: String) =
            SettingsView().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}