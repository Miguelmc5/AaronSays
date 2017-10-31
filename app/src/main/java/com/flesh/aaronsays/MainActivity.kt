package com.flesh.aaronsays

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ButtonView, View.OnClickListener {


    private val TAG = "[${MainActivity::class.java.simpleName}]"
    private var tones: ArrayList<SoundUtils> = arrayListOf()

    private var hasPaused = false
    private var gameCount = 0
    private var userCount = 0
    private var listOfColors = arrayListOf<GameButton.ButtonEnum>()
    private val unselectedBg = R.color.material_grey500


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startGame()
        btn_red.setOnClickListener(this)
        btn_blue.setOnClickListener(this)
        btn_yellow.setOnClickListener(this)
        btn_green.setOnClickListener(this)
    }

    private fun startGame() {
        val h = Handler()
        Thread({
            Thread.sleep(2.SECOND)
            h.post({
                chooseRandomButton()
            })
        }).start()
    }

    override fun onPause() {
        hasPaused = true
        stopAllTones()
        super.onPause()
    }

    private fun stopAllTones() {
        for (tone in tones) {
            tone.stopSound()
        }
        tones.clear()
    }

    override fun onResume() {
        super.onResume()
        hasPaused = false
    }

    override fun highlightRedButton(isCorrect: Boolean) {
        //Highlight Button
        setButtonBackGroundColor(GameButton.ButtonEnum.RED)
        //Play tone
        playTone(GameButton.ButtonEnum.RED,isCorrect)
    }

    override fun highlightGreenButton(isCorrect: Boolean) {
        //Highlight Button
        setButtonBackGroundColor(GameButton.ButtonEnum.GREEN)
        //Play tone
        playTone(GameButton.ButtonEnum.GREEN,isCorrect)
    }

    override fun highlightBlueButton(isCorrect: Boolean) {
        //Highlight Button
        setButtonBackGroundColor(GameButton.ButtonEnum.BLUE)
        //Play tone
        playTone(GameButton.ButtonEnum.BLUE,isCorrect)
    }

    override fun highlightYellowButton(isCorrect: Boolean) {
        //Highlight Button
        setButtonBackGroundColor(GameButton.ButtonEnum.YELLOW)
        //Play tone
        playTone(GameButton.ButtonEnum.YELLOW,isCorrect)
    }


    private val GameButton.ButtonEnum.getView: GameButton
        get() {
            when (this) {
                GameButton.ButtonEnum.GREEN -> return btn_green
                GameButton.ButtonEnum.RED -> return btn_red
                GameButton.ButtonEnum.BLUE -> return btn_blue
                GameButton.ButtonEnum.YELLOW -> return btn_yellow
            }
        }

    private val GameButton.ButtonEnum.getBgColor: Int
        get() = when (this) {
            GameButton.ButtonEnum.GREEN -> com.flesh.aaronsays.R.color.material_green500
            GameButton.ButtonEnum.RED -> com.flesh.aaronsays.R.color.material_red500
            GameButton.ButtonEnum.BLUE -> com.flesh.aaronsays.R.color.material_blue500
            GameButton.ButtonEnum.YELLOW -> com.flesh.aaronsays.R.color.material_yellow500
        }


    private fun GameButton.ButtonEnum.highlightButton(isCorrect: Boolean = true) {
        when (this) {
            GameButton.ButtonEnum.GREEN -> highlightGreenButton(isCorrect)
            GameButton.ButtonEnum.RED -> highlightRedButton(isCorrect)
            GameButton.ButtonEnum.BLUE -> highlightBlueButton(isCorrect)
            GameButton.ButtonEnum.YELLOW -> highlightYellowButton(isCorrect)
        }
    }

    override fun onClick(v: View?) {
        stopAllTones()
        deselectAllButtons()
        if (checkIfCorrectButton(v)) {
            userCount++
            if (userCount == gameCount) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                val h = Handler()
                Thread({
                    h.postDelayed({
                        continueGame()
                    }, 2.SECOND)
                }).start()
            }
        } else {
            userCount = 0
            Toast.makeText(this, "You Lose", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deselectAllButtons() {
        btn_red.setBackgroundResource(unselectedBg)
        btn_blue.setBackgroundResource(unselectedBg)
        btn_yellow.setBackgroundResource(unselectedBg)
        btn_green.setBackgroundResource(unselectedBg)
    }

    private fun continueGame() {
        userCount = 0
        val h = Handler()
        Thread({
            for (color: GameButton.ButtonEnum in listOfColors) {
                Thread.sleep(1.SECOND)
                h.post({
                    color.highlightButton()
                })
            }
            Thread.sleep(1.SECOND)
            h.post({
                chooseRandomButton()
            })
        }).start()
    }


    private fun checkIfCorrectButton(v: View?): Boolean {
        when (v) {
            btn_red -> {
                val isCorrect = listOfColors[userCount] == GameButton.ButtonEnum.RED
                GameButton.ButtonEnum.RED.highlightButton(isCorrect)
                return isCorrect
            }
            btn_blue -> {
                val isCorrect = listOfColors[userCount] == GameButton.ButtonEnum.BLUE
                GameButton.ButtonEnum.BLUE.highlightButton(isCorrect)
                return isCorrect
            }
            btn_yellow -> {
                val isCorrect = listOfColors[userCount] == GameButton.ButtonEnum.YELLOW
                GameButton.ButtonEnum.YELLOW.highlightButton(isCorrect)
                return isCorrect
            }
            btn_green -> {
                val isCorrect = listOfColors[userCount] == GameButton.ButtonEnum.GREEN
                GameButton.ButtonEnum.GREEN.highlightButton(isCorrect)
                return isCorrect
            }
            else -> return false
        }
    }

    @SuppressLint("NewApi") private fun setButtonBackGroundColor(color: GameButton.ButtonEnum) {
        val view = color.getView
        val selectedBg = color.getBgColor
        Log.d(TAG, "${color.name} Selected")
        view.setBackgroundResource(selectedBg)
        val handler = Handler()
        Thread({
            handler.postDelayed({
                Log.d(TAG, "${color.name} UnSelected")
                view.setBackgroundResource(unselectedBg)
            }, 1.SECOND)
        }).start()
    }


    private fun playTone(color: GameButton.ButtonEnum, isCorrect: Boolean = true) {
        var tu = SoundUtils()
        if (isCorrect) {
            when (color) {
                GameButton.ButtonEnum.GREEN -> tu.playTone(SoundUtils.Tones.E)
                GameButton.ButtonEnum.RED -> tu.playTone(SoundUtils.Tones.A)
                GameButton.ButtonEnum.BLUE -> tu.playTone(SoundUtils.Tones.E_MINUS_OCTAVE)
                GameButton.ButtonEnum.YELLOW -> tu.playTone(SoundUtils.Tones.C_SHARP)
            }
        } else {
            tu.playError(this)
        }
        tones.add(tu)
    }


    private fun chooseRandomButton() {
        val h = Handler()
        Thread({
            h.postDelayed({
                val rand = Random().nextInt(4)
                val color = GameButton.ButtonEnum.values()[rand]
                when (color) {
                    GameButton.ButtonEnum.GREEN -> highlightGreenButton()
                    GameButton.ButtonEnum.RED -> highlightRedButton()
                    GameButton.ButtonEnum.BLUE -> highlightBlueButton()
                    GameButton.ButtonEnum.YELLOW -> highlightYellowButton()
                }
                listOfColors.add(color)
                gameCount++
            }, .1.SECOND)
        }).start()
    }

}

fun atleastSdkLevel(sdkLevel: Int): Boolean = Build.VERSION.SDK_INT >= sdkLevel






