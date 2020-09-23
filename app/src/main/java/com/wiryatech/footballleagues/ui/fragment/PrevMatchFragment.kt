package com.wiryatech.footballleagues.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wiryatech.footballleagues.R

class PrevMatchFragment : Fragment() {

    companion object {
        const val ID = "id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prev_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("BundleFragment1", "$savedInstanceState, $arguments")
        if (arguments != null) {
            val idLeague = arguments?.getString(ID)
//            getFollower(username.toString())
            Log.d("Prev", "$arguments, $idLeague")
        }
    }
}