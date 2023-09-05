package com.botirov.ibrohim.counterapp.ui

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.botirov.ibrohim.counterapp.manager.PreferenceManager
import com.botirov.ibrohim.counterapp.ui.dialog.MenuBottomSheetDialog
import com.botirov.ibrohim.counterapp.ui.dialog.ZikrBottomSheetDialog
import com.botirov.ibrohim.model.Mock.zikrs
import com.botirov.ibrohim.model.Zikr
import com.botirov.ibrohim.tasbihapp.R
import com.botirov.ibrohim.tasbihapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ZikrBottomSheetDialog.OnZikrSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferenceManager: PreferenceManager

    private lateinit var mediaPlayer: MediaPlayer
    private var soundEnabled = true

    private lateinit var vibrator: Vibrator
    private var isVibrationOn = true

    private var currentZikr: Zikr? = null //  to store the currently selected zikr.
    private var counter = 0

    private var lightModeColor:Int = 0// todo: [done] colors.xml'dan kelsin
    private var darkModeColor :Int = 0 // todo:[done] colors.xml'dan kelsin
    private var isLightModeEnabled = false
    private var isThemeEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // prepareUI
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        // assignObjects
        preferenceManager = PreferenceManager(this)
        mediaPlayer = MediaPlayer.create(this, R.raw.click_sound)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // region setListeners
        binding.ibSoundOnAndOff.setOnClickListener { turnSoundOnAndOff() }
        binding.ibVibrate.setOnClickListener { vibrateOnAndOff() }

        lightModeColor = ContextCompat.getColor(binding.root.context, R.color.white)
        darkModeColor = ContextCompat.getColor(binding.root.context, R.color.black)

        binding.ibCounter.setOnClickListener {
            increment()
            vibrate()
            makeSound()
        }
        binding.ibDecrement.setOnClickListener { decrement() }
        binding.ibReset.setOnClickListener { resetCounter() }

        binding.ibLightMode.setOnClickListener { lightModeOnAndOff() }
        binding.ibTheme.setOnClickListener { themeOnAndOff() }

        binding.ibMenu.setOnClickListener {
            MenuBottomSheetDialog().show(supportFragmentManager, "")
        }
        binding.btnList.setOnClickListener {
            ZikrBottomSheetDialog(zikrs, this).show(supportFragmentManager, "")
        }

        // endregion
    }

    override fun onZikrSelected(zikr: Zikr) {
        // todo: [done] learn what `let`, `run`, `with`, `also`, daftaringga yozib qo'y, imtixonda chiqadi aniq
        currentZikr?.let {
            // calls previous zikr name and the current count, and saves  the count in the shared preferences
            preferenceManager.saveCountForZikr(it.name, counter)
        }

        currentZikr = zikr // It sets the currentZikr to the selected zikr,
        binding.tasbihText.text = zikr.name //updates the mainText with the name of the selected zikr
        counter = preferenceManager.getCountForZikr(zikr.name) // is called to retrieve the saved count from the shared preferences
        updateCountView() // calls the updateCountView function to update the count view.
    }

    private fun updateCountView() {
        // todo: [done] rename to human understandable name
        val seniorTasbihCounterFormat = String.format("%06d", counter)
        val juniorTasbihCounterFormat = String.format("%3d", counter)

        val spannable = SpannableString(seniorTasbihCounterFormat)
        spannable.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0,
            seniorTasbihCounterFormat.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvCounter.text = spannable
        binding.currentCount.text = currentZikr?.let { juniorTasbihCounterFormat }
    }

    private fun turnSoundOnAndOff() {
        soundEnabled = !soundEnabled
        if (soundEnabled) {
            binding.ibSoundOnAndOff.setImageResource(R.drawable.ic_sound)
            binding.ibSoundOnAndOff.setBackgroundResource(R.drawable.circle_button_selector)
        } else {
            binding.ibSoundOnAndOff.setImageResource(R.drawable.ic_sound_off)
            binding.ibSoundOnAndOff.setBackgroundResource(R.drawable.circle_button_grey_selector)
        }
    }

    private fun vibrateOnAndOff() {
        isVibrationOn = !isVibrationOn
        if (isVibrationOn) {
            binding.ibVibrate.setImageResource(R.drawable.ic_vibrate)
            binding.ibVibrate.setBackgroundResource(R.drawable.circle_button_selector)
        } else {
            binding.ibVibrate.setImageResource(R.drawable.ic_vibrate_off)
            binding.ibVibrate.setBackgroundResource(R.drawable.circle_button_grey_selector)
        }
    }

    // todo: NOTE:[done] methodlar logically grouped bo'lishi kerak, misol increment va decrement, virate and makeSound

    private fun increment() {
        counter++
        updateCountView()
    }

    private fun decrement() {
        if (counter > 0) {
            counter--
            updateCountView()
        }
    }

    private fun resetCounter() {
        counter = 0
        updateCountView()
    }

    private fun vibrate() {
        if (isVibrationOn) {
            // Vibrate for 500 milliseconds
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(70, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(70)
            }
        }
    }

    private fun makeSound() {
        if (soundEnabled) {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.seekTo(0)
            }
            mediaPlayer.start()
        }
    }

    private fun lightModeOnAndOff() {
        if (isLightModeEnabled) {
            // Change back to the default background color
            binding.clRoot.setBackgroundColor(darkModeColor)
            binding.ibLightMode.setImageResource(R.drawable.ic_light_mode)
        } else {
            // Change to light mode
            binding.clRoot.setBackgroundColor(lightModeColor)
            binding.ibLightMode.setImageResource(R.drawable.ic_dark_mode)
        }
        // Toggle the light mode state
        isLightModeEnabled = !isLightModeEnabled
    }

    private fun themeOnAndOff() {
        if (isThemeEnabled) {
            binding.clRoot.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_blue))
            binding.tasbihView.setBackgroundResource(R.drawable.tasbih_black_shape)
        } else {
            binding.clRoot.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            binding.tasbihView.setBackgroundResource(R.drawable.tasbih_shape)
        }
        isThemeEnabled = !isThemeEnabled
    }
}
