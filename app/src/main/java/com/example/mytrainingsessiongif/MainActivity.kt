package com.example.mytrainingsessiongif

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var toolbarMain: Toolbar
    private lateinit var exercisesET: EditText
    private lateinit var startExercisesBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarMain = findViewById(R.id.toolbarMain)
        exercisesET = findViewById(R.id.exercisesET)
        startExercisesBTN = findViewById(R.id.startExercisesBTN)

        setSupportActionBar(toolbarMain)
        title = getString(R.string.toolbarMain)
        toolbarMain.subtitle = getString(R.string.by_rocky)

        startExercisesBTN.setOnClickListener {
            if (exercisesET.text.isEmpty()) return@setOnClickListener

            val exercise = exercisesET.text.toString()

            val intent = Intent(this, TrainingActivity::class.java)
            intent.putExtra("exercise", exercise)
            startActivity(intent)

            exercisesET.text.clear()
        }

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