package kr.co.tjoeun.apipractice_20200615

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

//    제목을 나타내는 텍스트뷰 (제목 설정시에만 등장)
    lateinit var activityTitleTxt : TextView
//    제목에 쓸 말이 없을때 보여줄 이미지뷰 (기본 설정)
    lateinit var logoImg : ImageView

//    알림 목록에 들어가는 버튼
    lateinit var notificaionBtn : ImageView

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

//    각 화면의 setTitle 기본 기능을 => 커스텀 액션바에게 반영하도록 오버라이딩

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)

//        액션바가 있을때만 실행 => 타이틀 문구 변경
        supportActionBar?.let {

//            로고를 숨기고 => 글씨로 보여지도록
            logoImg.visibility = View.GONE
            activityTitleTxt.visibility = View.VISIBLE

            activityTitleTxt.text = title
        }

    }

    fun setCustomActionBar() {
//        액션바 관련 세팅 변경

//        액션바 커스텀 기능 활성화
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

//        커스텀 액션바 영역 확장 => 뒷단 여백 제거

//        기본 배경색 제거
        supportActionBar!!.setBackgroundDrawable(null)
//        실제 여백 제거. => 커스텀 뷰를 감싸는 Toolbar의 내부 여백값 0으로 설정
        val parent = supportActionBar!!.customView.parent as Toolbar
        parent.setContentInsetsAbsolute(0, 0)


//        XML에 있는 뷰들을 사용할 수 있도록 연결
        activityTitleTxt = supportActionBar!!.customView.findViewById(R.id.activityTitleTxt)
        logoImg = supportActionBar!!.customView.findViewById(R.id.logoImg)
        notificaionBtn = supportActionBar!!.customView.findViewById(R.id.notificaionBtn)

//        알림버튼은 눌리면 어느화면에서건 => 알림화면으로 이동.
        notificaionBtn.setOnClickListener {
            val myIntent = Intent(mContext, NotificationListActivity::class.java)
            startActivity(myIntent)
        }

    }


}