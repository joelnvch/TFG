package src

import android.app.Application
import view.BoardView
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MyApplication : Application(){
    companion object{
        lateinit var pyBoard: PyObject
        lateinit var board: BoardView
    }

    override fun onCreate(){
        super.onCreate()
        initPython()

        var pythonFile = Python.getInstance().getModule("board")
        pyBoard = pythonFile.callAttr("init_board", "cardData")

        board = BoardView(pyBoard)
    }

    private fun initPython(){
        if (!Python.isStarted()){
            Python.start(AndroidPlatform(this))
        }
    }
}