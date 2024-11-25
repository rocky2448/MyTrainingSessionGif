package com.example.mytrainingsessiongif

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TrainingActivity : AppCompatActivity() {

    private lateinit var toolbarMain: Toolbar
    val exercises = ExerciseDataBase.exercise
    private var exerciseIndex = 0
    private var selectedExercise = 0
    private lateinit var currentExercise: Exercise
    private lateinit var timer: CountDownTimer
    private lateinit var nameExerciseTV: TextView
    private lateinit var startTrainingBTN: Button
    private lateinit var descriptionExerciseTV: TextView
    private lateinit var timerTV: TextView
    private lateinit var nextExerciseBTN: Button
    private lateinit var imageExerciseIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_training)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarMain = findViewById(R.id.toolbarMain)
        nameExerciseTV = findViewById(R.id.nameExerciseTV)
        startTrainingBTN = findViewById(R.id.startTrainingBTN)
        descriptionExerciseTV = findViewById(R.id.descriptionExerciseTV)
        timerTV = findViewById(R.id.timerTV)
        nextExerciseBTN = findViewById(R.id.nextExerciseBTN)
        imageExerciseIV = findViewById(R.id.imageExerciseIV)

        setSupportActionBar(toolbarMain)
        title = getString(R.string.toolbarMain)
        toolbarMain.subtitle = getString(R.string.by_rocky)

        val exercise = intent.getStringExtra("exercise")

        for (i in exercises.indices) { //находит нужное упражнение из базы данных
            if (exercises[i].name.uppercase() == exercise!!.uppercase()) {
                selectedExercise = i
            }
        }

        nameExerciseTV.text = exercises[selectedExercise].name //вывод на экран названия тренировки
        descriptionExerciseTV.text = exercises[selectedExercise].description //вывод на экран описание тренировки
        timerTV.text = formatTime((exercises[selectedExercise].durationInSeconds).toInt())

        if (selectedExercise == 0) { //если выбранного упражнения нет в базе данных
            imageExerciseIV.setImageResource(exercises[selectedExercise].gifImage)
            startTrainingBTN.isEnabled = false
            timerTV.text = ""
        }

        startTrainingBTN.setOnClickListener {
            startWorkout()
        }

        nextExerciseBTN.setOnClickListener {
            nextExercise()
        }

    }

    private fun startWorkout() {
        exerciseIndex = selectedExercise
        startTrainingBTN.isEnabled = false
        startTrainingBTN.text = "Процесс тренировки"
        startExercise()
    }

    private fun nextExercise() {
        timer.cancel()
        finish()
    }

    private fun startExercise() {
        nextExerciseBTN.isEnabled = false
        currentExercise = exercises[exerciseIndex]
        nameExerciseTV.text = currentExercise.name
        descriptionExerciseTV.text = currentExercise.description
        imageExerciseIV.setImageResource(exercises[exerciseIndex].gifImage)
        timerTV.text = formatTime(currentExercise.durationInSeconds)
        timer = object : CountDownTimer(
            currentExercise.durationInSeconds * 1000L,
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                timerTV.text = formatTime((millisUntilFinished / 1000).toInt())
            }
            override fun onFinish() {
                timerTV.text = "0:00"
                imageExerciseIV.visibility = View.VISIBLE
                nextExerciseBTN.isEnabled = true
                startTrainingBTN.text = "Начать тренировку заново"
                startTrainingBTN.isEnabled = true
                imageExerciseIV.setImageResource(0)
            }
        }.start()
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}