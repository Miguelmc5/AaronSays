package com.flesh.aaronsays

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import com.flesh.aaronsays.interfaces.ButtonView
import com.flesh.aaronsays.interfaces.GAME
import com.flesh.aaronsays.utils.*
import com.flesh.aaronsays.views.GameButton
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * GameActivity is where all of the magic happens.
 * Created by aaronfleshner on 10/30/17.
 */
class GameActivity : AppCompatActivity(), ButtonView, View.OnClickListener, GAME {

    private val TAG = "[${GameActivity::class.java.simpleName}]"
    private var tones: ArrayList<SoundUtils> = arrayListOf()

    private var hasPaused = false
    private var gameCount = 0
    private var userCount = 0
    private var listOfColors = arrayListOf<GameButton.ButtonEnum>()

    private val onTime = 1.SECOND
    private val offTime = .2.SECOND


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setHighScoreText()
        deselectAllButtons()
        initListeners()
        disableUserInteraction()
    }

    private fun setHighScoreText() {
        tvHighScore.text = getString(R.string.high_score, HighScoreUtils(this).getHighScore())
    }

    private fun initListeners() {
        btn_red.setOnClickListener(this)
        btn_blue.setOnClickListener(this)
        btn_yellow.setOnClickListener(this)
        btn_green.setOnClickListener(this)
        btnStart.setOnClickListener {
            startGame()
            btnStart.visibility = GONE
        }
    }

    override fun restartGame() {
        //resetAllVariables
        gameCount = 0
        userCount = 0
        listOfColors.clear()
        //start game again
        startGame()
    }


    override fun startGame() {
        disableUserInteraction()
        val h = Handler()
        Thread({
            2.SECOND.sleep
            h.post({
                chooseRandomButton()
                enableUserInteraction()
            })
        }).start()
    }


    override fun disableUserInteraction() {
        btn_red.isClickable = false
        btn_blue.isClickable = false
        btn_yellow.isClickable = false
        btn_green.isClickable = false
    }

    override fun enableUserInteraction() {
        btn_red.isClickable = true
        btn_blue.isClickable = true
        btn_yellow.isClickable = true
        btn_green.isClickable = true
    }


    override fun correctMove() {
        userCount++
        if (userCount == gameCount) {
            disableUserInteraction()
            Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
            val h = Handler()
            Thread({
                2.SECOND.sleep
                h.post({
                    continueGame()
                })
            }).start()
        }
    }

    override fun incorrectMove() {
        endGame()
    }


    override fun endGame() {
        Toast.makeText(this, getString(R.string.you_lose), Toast.LENGTH_SHORT).show()
        if (gameCount > HighScoreUtils(context = this).getHighScore()) {
            HighScoreUtils(context = this).setHighScore(gameCount)
            setHighScoreText()
        }

        val builder: AlertDialog.Builder = if (Build.VERSION_CODES.LOLLIPOP_MR1.atLeastSDKlevel) {
            AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
        } else {
            AlertDialog.Builder(this)
        }
        builder.setTitle(getString(R.string.game_over))
                .setMessage(getString(R.string.your_score, gameCount)
                        +                     "\n" + getString(R.string.wanna_play_again))
                .setPositiveButton(getString(R.string.ya)) { _, _ ->
                    restartGame()
                }
                .setNegativeButton(getString(R.string.nah)) { _, _ ->
                    finish()
                }.show()
        gameCount = 0
    }


    override fun onPause() {
        hasPaused = true
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

    override fun highlightButton(color: GameButton.ButtonEnum, isCorrect: Boolean) {
        //Highlight Button
        setButtonBackGroundColor(color)
        //Play tone
        playTone(color, isCorrect)
    }

    override fun onClick(v: View?) {
        stopAllTones()
        deselectAllButtons()
        if (checkIfCorrectButton(v)) {
            correctMove()
        } else {
            incorrectMove()
        }
    }

    private fun deselectAllButtons() {
        btn_red.setBackgroundResource(GameButton.ButtonEnum.RED.getUnBgColor)
        btn_blue.setBackgroundResource(GameButton.ButtonEnum.BLUE.getUnBgColor)
        btn_yellow.setBackgroundResource(GameButton.ButtonEnum.YELLOW.getUnBgColor)
        btn_green.setBackgroundResource(GameButton.ButtonEnum.GREEN.getUnBgColor)
    }

    private fun continueGame() {
        userCount = 0
        val h = Handler()
        Thread({
            for (color: GameButton.ButtonEnum in listOfColors) {
                onTime.sleep
                h.post({
                    highlightButton(color)
                })
            }
            onTime.sleep
            h.post({
                chooseRandomButton()
                enableUserInteraction()
            })
        }).start()
    }

    private fun chooseRandomButton() {
        val h = Handler()
        Thread({
            h.postDelayed({
                val rand = Random().nextInt(4)
                val color = GameButton.ButtonEnum.values()[rand]
                highlightButton(color)
                listOfColors.add(color)
                gameCount++
            }, offTime)
        }).start()
    }

    private fun checkIfCorrectButton(v: View?): Boolean {
        when (v) {
            btn_red -> {
                val isCorrect = listOfColors[userCount] == GameButton.ButtonEnum.RED
                highlightButton(GameButton.ButtonEnum.RED,isCorrect)
                return isCorrect
            }
            btn_blue -> {
                val isCorrect = listOfColors[userCount] == GameButton.ButtonEnum.BLUE
                highlightButton(GameButton.ButtonEnum.BLUE,isCorrect)
                return isCorrect
            }
            btn_yellow -> {
                val isCorrect = listOfColors[userCount] == GameButton.ButtonEnum.YELLOW
                highlightButton(GameButton.ButtonEnum.YELLOW,isCorrect)
                return isCorrect
            }
            btn_green -> {
                val isCorrect = listOfColors[userCount] == GameButton.ButtonEnum.GREEN
                highlightButton(GameButton.ButtonEnum.GREEN,isCorrect)
                return isCorrect
            }
            else -> return false
        }
    }

    private fun setButtonBackGroundColor(color: GameButton.ButtonEnum) {
        Log.d(TAG, "${color.name} Selected")
        color.getView.setBackgroundResource(color.getBgColor)
        val handler = Handler()
        Thread({
            .75.SECOND.sleep
            handler.post({
                Log.d(TAG, "${color.name} UnSelected")
                color.getView.setBackgroundResource(color.getUnBgColor)
            })
        }).start()
    }

    //Color Extensions
    private val GameButton.ButtonEnum.getView: GameButton
        get() = when (this) {
                GameButton.ButtonEnum.GREEN -> btn_green
                GameButton.ButtonEnum.RED -> btn_red
                GameButton.ButtonEnum.BLUE -> btn_blue
                GameButton.ButtonEnum.YELLOW -> btn_yellow
        }

    private val GameButton.ButtonEnum.getBgColor: Int
        get() = when (this) {
            GameButton.ButtonEnum.GREEN -> com.flesh.aaronsays.R.color.material_green500
            GameButton.ButtonEnum.RED -> com.flesh.aaronsays.R.color.material_red500
            GameButton.ButtonEnum.BLUE -> com.flesh.aaronsays.R.color.material_blue500
            GameButton.ButtonEnum.YELLOW -> com.flesh.aaronsays.R.color.material_yellow500
        }

    private val GameButton.ButtonEnum.getUnBgColor: Int
        get() = when (this) {
            GameButton.ButtonEnum.GREEN -> com.flesh.aaronsays.R.color.material_green200
            GameButton.ButtonEnum.RED -> com.flesh.aaronsays.R.color.material_red200
            GameButton.ButtonEnum.BLUE -> com.flesh.aaronsays.R.color.material_blue200
            GameButton.ButtonEnum.YELLOW -> com.flesh.aaronsays.R.color.material_yellow200
        }

    private fun playTone(color: GameButton.ButtonEnum, isCorrect: Boolean = true) {
        val tu = SoundUtils()
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

}
