package com.baam.baamremote

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.baam.baamremote.databinding.ActivityHomeActiviyBinding
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC,
    GOOGLE,
    FACEBOOK
}

class HomeActiviy : AppCompatActivity() {
    private lateinit var binding:ActivityHomeActiviyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeActiviyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val email= bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email?: "", provider?:"")

        //Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()
    }

    private fun setup(email:String, provider:String) {
        title = "Inicio"
        binding.tvemail.text = email
        binding.tvproveedor.text = provider

        binding.btnlogout.setOnClickListener {

            //Borrado de daots
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            if (provider == ProviderType.FACEBOOK.name){
                LoginManager.getInstance().logOut()
            }

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}