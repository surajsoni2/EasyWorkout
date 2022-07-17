package com.example.easyworkout

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easyworkout.databinding.ActivityExerciseBinding
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding : ActivityExerciseBinding? =null

    private var countDownTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Setting up viewBinding
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // the above below code is for toolbar
        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        // This is for speakup code
        tts = TextToSpeech(this,this)

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }



        //Below Code will be for timer
        setupRestView()

    }

    private fun setupRestView() {

        try{
            val soundURI = Uri.parse("android.resource://com.example.easyworkout/" + R.raw.press_start)

            player = MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping = false
            player?.start()
            speakOut("Get ready for"+exerciseList!![currentExercisePosition+1].getName())

//            Toast.makeText(this,exerciseList!![currentExercisePosition+1].getName(),Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            e.printStackTrace()
        }
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE

        if (countDownTimer != null){
            countDownTimer!!.cancel()
            countDownTimer = null
            restProgress = 0
        }

        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition+1].getName()

        setProgressTimer()


    }
    private fun setProgressTimer()  {


        binding?.progressBar?.progress = restProgress

        countDownTimer = object : CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10-restProgress
                binding?.time?.text = (10 - restProgress).toString()

                if(10-restProgress<4.9 && 10-restProgress!=0){
                    speakOut((10 - restProgress).toString())
                }
            }
            override fun onFinish() {
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView(){
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        if (exerciseTimer !=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        speakOut("Start "+exerciseList!![currentExercisePosition].getName()+"For thirty seconds")

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgress()
    }
    private fun setExerciseProgress() {

        binding?.progressBar?.progress = restProgress

        exerciseTimer = object : CountDownTimer(30000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                var exerciseLeftTime = 30-exerciseProgress
                binding?.exerciseProgressBar?.progress = exerciseLeftTime
                binding?.exerciseTime?.text = exerciseLeftTime.toString()
                if(exerciseLeftTime<6.7 && exerciseLeftTime!=0){
                    speakOut((30 - exerciseProgress).toString())
                }
            }
            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!!-1){
                    setupRestView()
                }else{
                    speakOut("Congratulations! You have completed all exercises")
                    Toast.makeText(this@ExerciseActivity,"Congratulations! You have completed all exercises",Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null){
            countDownTimer!!.cancel()
            countDownTimer = null
            restProgress = 0
        }
        if (exerciseTimer !=null){
            exerciseProgress = 0
            exerciseTimer = null
        }
        if (tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null){
            player!!.stop()
        }
        binding =null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.ENGLISH)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The Language specified is not selected")
            }
            }else{
                Log.e("TTS","Intialiazation Failed")
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }


}
