package com.example.snake

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.snake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val mViewModel = ViewModelProvider(this).get(SnakeViewModel::class.java)
        mViewModel.body.observe(this, Observer {
            binding.gameLayout.gameView.snakeBody = it
            binding.gameLayout.gameView.invalidate()
        })

        mViewModel.score.observe(this, Observer {
            binding.gameLayout.score.text = it.toString()
        })
        mViewModel.gameState.observe(this, Observer {gameState ->
            if (gameState == GameState.GAMEOVER) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Game")
                    .setMessage("Game Over")
                    .setPositiveButton("OK", null)
                    .show()
            }
        })

        mViewModel.apple.observe(this, Observer {
            binding.gameLayout.gameView.apple = it
            binding.gameLayout.gameView.invalidate()
        })

        binding.fab.setOnClickListener { view ->
            mViewModel.reset()
        }
        //開始遊戲
        mViewModel.start()
        binding.gameLayout.topBtn.setOnClickListener { mViewModel.move(Direction.TOP)}
        binding.gameLayout.downBtn.setOnClickListener { mViewModel.move(Direction.DOWN)}
        binding.gameLayout.leftBtn.setOnClickListener { mViewModel.move(Direction.LEFT)}
        binding.gameLayout.rightBtn.setOnClickListener { mViewModel.move(Direction.RIGHT)}
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}