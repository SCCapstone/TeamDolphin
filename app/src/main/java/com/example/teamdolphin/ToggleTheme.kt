package com.example.teamdolphin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate

class ToggleTheme : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toggle_theme, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toggleThemeButton = view.findViewById<Switch>(R.id.button_toggleTheme)
        toggleThemeButton.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                toggleThemeButton.setText("Dark")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                toggleThemeButton.setText("Light")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}