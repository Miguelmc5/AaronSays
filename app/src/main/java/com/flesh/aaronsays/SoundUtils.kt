package com.flesh.aaronsays

import android.content.Context
import android.media.AudioFormat
import android.os.Handler
import kotlin.experimental.and
import android.media.AudioTrack
import android.media.AudioManager
import android.media.MediaPlayer


/**
 * Created by aaronfleshner on 10/23/17.
 */
class SoundUtils {

    private val duration = .75 // seconds
    private val sampleRate = 8000
    private var numSamples = Math.floor(duration * sampleRate).toInt()
    private val sample = DoubleArray(numSamples)
    private val generatedSnd = ByteArray(2 * numSamples)
    private var handler : Handler = Handler()
    private val audioTrack : AudioTrack = AudioTrack(AudioManager.STREAM_MUSIC,
            sampleRate, AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT, generatedSnd.size,
            AudioTrack.MODE_STATIC)

    private var mp : MediaPlayer? = null

    class Tones {
        companion object {
            val A = 440f
            val E = 659.255f
            val E_MINUS_OCTAVE = 329.628f
            val C_SHARP = 554.37f
        }
    }

    fun playTone(tone : Float){
        val thread = Thread(Runnable {
            generateTone(tone)
            handler.post({ startTone() })

        })
        thread.start()
    }

    private fun generateTone(tone: Float) {
            // fill out the array
            for (i in 0 until numSamples) {
                sample[i] = Math.sin(2.0 * Math.PI * i.toDouble() / (sampleRate / tone))
            }

            // convert to 16 bit pcm sound array
            // assumes the sample buffer is normalised.
            var idx = 0
            for (dVal in sample) {
                // scale to maximum amplitude
                val mVal = (dVal * 32767).toShort()
                // in 16 bit wav PCM, first byte is the low order byte
                generatedSnd[idx++] = (mVal and 0x00ff).toByte()
                generatedSnd[idx++] = (mVal and 0xff00.toShort()).toInt().ushr(8).toByte()
            }
    }

    fun playError(context:Context){
        val mp = MediaPlayer.create(context,R.raw.wrong_ans)
        mp.start()
        mp.setOnCompletionListener {
            mp.stop()
            mp.release()
        }
    }


    private fun startTone() {
        try {
            audioTrack.write(generatedSnd, 0, generatedSnd.size)
            audioTrack.play()
            audioTrack.flush()
        }catch (e : IllegalStateException){

        }
    }

    fun stopSound(){
        //stop tone.
        try {
            audioTrack.stop()
        }catch (e : IllegalStateException){

        }
        //stop error sound
        if (mp?.isPlaying == true){
            mp?.stop()
            mp?.release()
        }

    }
}