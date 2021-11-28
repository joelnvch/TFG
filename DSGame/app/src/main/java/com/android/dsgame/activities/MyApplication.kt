package com.android.dsgame.activities

import android.app.Application
import com.android.dsgame.model.Board
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MyApplication : Application(){
    companion object{
        lateinit var PACKAGE_NAME: String

        lateinit var pyBoard: PyObject
        lateinit var board: Board
    }

    override fun onCreate(){
        super.onCreate()
        initPython()

        PACKAGE_NAME = packageName

        val pythonFile = Python.getInstance().getModule("board")
        pyBoard = pythonFile.callAttr("init_board", "cardData")
        board = Board(pyBoard)
    }

    private fun initPython(){
        if (!Python.isStarted()){
            Python.start(AndroidPlatform(this))
        }
    }
}