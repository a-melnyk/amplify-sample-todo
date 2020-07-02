package com.example.todo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.Priority
import com.amplifyframework.datastore.generated.model.Todo
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        saveTodo3()
//        observe()
        queryTodos()
    }

    fun saveTodo1() {
        val item: Todo = Todo.builder()
            .name("Build Android application")
            .description("Build an Android application using Amplify")
            .build()

        Amplify.DataStore.save(
            item,
            { success -> Log.i("Tutorial", "Saved item: " + success.item().name) },
            { error -> Log.e("Tutorial", "Could not save item to DataStore", error) }
        )
    }

    fun saveTodo2() {
        val item = Todo.builder()
            .name("Finish quarterly taxes")
            .priority(Priority.HIGH)
            .description("Taxes are due for the quarter next week")
            .build()

        Amplify.DataStore.save(
            item,
            { success -> Log.i("Tutorial", "Saved item: " + success.item().name) },
            { error -> Log.e("Tutorial", "Could not save item to DataStore", error) }
        )
    }

    fun saveTodo3() {
        val item = Todo.builder()
            .name("Test if AWSTime works")
            .priority(Priority.HIGH)
            .description("It must work")
            .duration(Temporal.Time(Calendar.getInstance().time))
            .build()

        Amplify.DataStore.save(
            item,
            { success -> Log.i("Tutorial", "Saved item: " + success.item().name) },
            { error -> Log.e("Tutorial", "Could not save item to DataStore", error) }
        )
    }

    fun queryTodos() {
        Amplify.DataStore.query(
            Todo::class.java,
            { todos ->
                while (todos.hasNext()) {
                    val todo = todos.next()
                    val name = todo.name
                    val priority: Priority? = todo.priority
                    val description: String? = todo.description

                    Log.i("Tutorial", "==== Todo ====")
                    Log.i("Tutorial", "Name: $name")

                    if (priority != null) {
                        Log.i("Tutorial", "Priority: $priority")
                    }

                    if (description != null) {
                        Log.i("Tutorial", "Description: $description")
                    }
                }
            },
            { failure -> Log.e("Tutorial", "Could not query DataStore", failure) }
        )
    }

    fun observe() {
        Amplify.DataStore.observe(Todo::class.java,
            { Log.i("Tutorial", "Observation began.") },
            { Log.i("Tutorial", it.item().toString()) },
            { Log.e("Tutorial", "Observation failed.", it) },
            { Log.i("Tutorial", "Observation complete.") }
        )
    }
}
