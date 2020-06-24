package kr.co.tjoeun.apipractice_20200615

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    abstract fun setupEvents()
    abstract fun setValues()

//    BaseActivity를 상속받는 모든 액티비티는 자동으로 setCustomActionBar 실행 처리.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        액션바가 있는 액티비티들만 커스텀 액션바 세팅 진행
        supportActionBar?.let {
            setCustomActionBar()
        }
    }

    fun setCustomActionBar() {
//        액션바 관련 세팅 변경

//        액션바 커스텀 기능 활성화
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

//        커스텀 액션바 영역 확장 => 뒷단 여백 제거

//        기본 배경색 제거
        supportActionBar?.setBackgroundDrawable(null)
//        실제 여백 제거. => 커스텀 뷰를 감싸는 Toolbar의 내부 여백값 0으로 설정
        val parent = supportActionBar?.customView?.parent as Toolbar
        parent.setContentInsetsAbsolute(0, 0)

    }


}