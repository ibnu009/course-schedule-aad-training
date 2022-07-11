package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.ViewModelFactory
import com.dicoding.courseschedule.util.*
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener, AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: AddCourseViewModel

    private val dialogFragment = TimePickerFragment()
    private var startTime = ""
    private var endTime = ""
    private var selectedDay = 0

    private lateinit var courseNameEditText: EditText
    private lateinit var lecturerEditText: EditText
    private lateinit var noteEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

        courseNameEditText = findViewById(R.id.ed_course_name)
        lecturerEditText = findViewById(R.id.ed_lecturer_name)
        noteEditText = findViewById(R.id.ed_note)

        val spinnerDay = findViewById<Spinner>(R.id.spinner_day)
        ArrayAdapter.createFromResource(
            this,
            R.array.day,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDay.adapter = adapter
        }
        spinnerDay.onItemSelectedListener = this

        viewModel.saved.observe(this, Observer(this::onInsertCourse))
    }

    private fun onInsertCourse(isSavedEvent: Event<Boolean>){
        val isSaved = isSavedEvent.getContentIfNotHandled() ?: return
        if (isSaved){
            showOkBackDialog("Success", "Course is added!", true)
        } else {
            showOkBackDialog("Failed", "Failed to add Course!", false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                viewModel.insertCourse(
                    courseName = courseNameEditText.text.toString(),
                    day = selectedDay,
                    startTime = startTime,
                    endTime = endTime,
                    lecturer = lecturerEditText.text.toString(),
                    note = noteEditText.text.toString()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showStartTimePicker(view: View) {
        dialogFragment.show(supportFragmentManager, START_TIME_PICKER)
    }

    fun showEndTimePicker(view: View) {
        dialogFragment.show(supportFragmentManager, END_TIME_PICKER)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(0, 0, 0, hour, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateText = dateFormat.format(calendar.time)

        if (tag?.equals(START_TIME_PICKER) == true){
            startTime = dateText
            findViewById<TextView>(R.id.tv_start_time).text = dateText
        } else {
            endTime = dateText
            findViewById<TextView>(R.id.tv_end_time).text = dateText
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        println("selected item is $p2 and day name is ${DayName.getByNumber(p2 + 1)}")
        selectedDay = p2
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}