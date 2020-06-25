package kr.co.tjoeun.apipractice_20200615.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    //    JAVA => static 에 대응되는 개념
//    어느 객체가 하던 상관 X, 기능만 잘 되면 그만인것들을 모아두는 영역
    companion object {
//        호스트가 어디인지 명시 => 가져다 사용
        val BASE_URL = "http://15.165.177.142"


//        중복체크를 get으로 요청하는 함수
        fun getRequestDuplicatedCheck(context: Context, type:String, input:String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

//            어느 기능주소로 + GET방식은 => 주소에, 파라미터를 모두 적어줘야함.
//            가공된 주소를 => newBuilder로 변환 (파라미터 첨부 준비)
            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
//            실제로 파라미터를 주소에 첨부
            urlBuilder.addEncodedQueryParameter("type", type)
            urlBuilder.addEncodedQueryParameter("value", input)

//            완성된 주소를 String으로 변경
            val urlString = urlBuilder.build().toString()

//            요청 정보를 request로 저장
            val request = Request.Builder()
                .url(urlString)
                .get()
//                .header() // 헤더 필요시 첨부
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
        //                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
        //                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
        //                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

        //                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

        //                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })

        }


        //        로그인한 사용자 정보를 get으로 요청하는 함수
        fun getRequestUserInfo(context: Context, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlBuilder = "${BASE_URL}/user_info".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addEncodedQueryParameter("type", type)
//            urlBuilder.addEncodedQueryParameter("value", input)

            val urlString = urlBuilder.build().toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context)) // 헤더 필요시 첨부
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
                    //                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
                    //                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

                    //                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

                    //                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })

        }

        //        알림 목록을 get으로 요청하는 함수
        fun getRequestNotificaionList(context: Context, needAllNotis:Boolean, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlBuilder = "${BASE_URL}/notification".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("need_all_notis", needAllNotis.toString())
//            urlBuilder.addEncodedQueryParameter("value", input)

            val urlString = urlBuilder.build().toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context)) // 헤더 필요시 첨부
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
                    //                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
                    //                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

                    //                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

                    //                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })

        }


        //        특정 주제의 상세 진행상황을 get으로 요청하는 함수
        fun getRequestTopicDetail(context: Context, topicId:Int, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlBuilder = "${BASE_URL}/topic/${topicId}".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("order_type", "NEW")
//            urlBuilder.addEncodedQueryParameter("value", input)

            val urlString = urlBuilder.build().toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context)) // 헤더 필요시 첨부
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
                    //                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
                    //                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

                    //                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

                    //                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })

        }


        //        특정 의견의 상세 진행상황을 get으로 요청하는 함수
        fun getRequestReplyDetail(context: Context, replyId: Int, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlBuilder = "${BASE_URL}/topic_reply/${replyId}".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addEncodedQueryParameter("order_type", "NEW")
//            urlBuilder.addEncodedQueryParameter("value", input)

            val urlString = urlBuilder.build().toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context)) // 헤더 필요시 첨부
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
                    //                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
                    //                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

                    //                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

                    //                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })

        }


        //        메인화면에 필요한 정보를 get으로 요청하는 함수
        fun getRequestMainInfo(context: Context, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlBuilder = "${BASE_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addEncodedQueryParameter("type", type)
//            urlBuilder.addEncodedQueryParameter("value", input)

            val urlString = urlBuilder.build().toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context)) // 헤더 필요시 첨부
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
                    //                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
                    //                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

                    //                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

                    //                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })

        }

        //        투표 기능을 post로 요청하는 함수
        fun postRequestVote(context:Context, sideId:Int, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_vote"

            val formData = FormBody.Builder()
                .add("side_id", sideId.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))  // API에서 헤더를 요구하면 여기서 첨부.
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
//                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

//                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

//                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })


        }


        //        알림을 어디까지 읽었는지 알려주는 post로 요청하는 함수
        fun postRequestNotification(context:Context, notiId:Int, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/notification"

            val formData = FormBody.Builder()
                .add("noti_id", notiId.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))  // API에서 헤더를 요구하면 여기서 첨부.
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
//                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

//                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

//                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })


        }


        //       의견/답글에 대한 좋아요/싫어요 기능을 post로 요청하는 함수
        fun postRequestReplyLikeOrDislike(context:Context, replyId:Int, isLike:Boolean, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_reply_like"

            val formData = FormBody.Builder()
                .add("reply_id", replyId.toString())
                .add("is_like", isLike.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))  // API에서 헤더를 요구하면 여기서 첨부.
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
//                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

//                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

//                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })


        }


        //        의견 등록 기능을 post로 요청하는 함수
        fun postRequestReply(context:Context, topicId:Int, content:String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_reply"

            val formData = FormBody.Builder()
                .add("topic_id", topicId.toString())
                .add("content", content)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))  // API에서 헤더를 요구하면 여기서 첨부.
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
//                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

//                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

//                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })


        }


        //        답글 등록 기능을 post로 요청하는 함수
        fun postRequestReReply(context:Context, parentReplyId:Int, content:String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_reply"

            val formData = FormBody.Builder()
                .add("parent_reply_id", parentReplyId.toString())
                .add("content", content)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))  // API에서 헤더를 요구하면 여기서 첨부.
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
//                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

//                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

//                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })


        }



        //        로그인 기능을 post로 요청하는 함수
        fun postRequestLogin(context:Context, email:String, pw:String, handler: JsonResponseHandler?) {

//            클라이언트로 동작해주는 변수
            val client = OkHttpClient()

//            어느 기능 주소로 가는지 Host와 조합해서 명시
            val urlString = "${BASE_URL}/user"

//            서버에 전달할 데이터를 담는 과정 (POST - 폼데이터)
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

//            서버에 요청할 모든 정보를 담는 request 변수 생성
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
//                .header()  // API에서 헤더를 요구하면 여기서 첨부.
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
//                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

//                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

//                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })


        }

        // 회원가입 기능을 put으로 요청하는 함수
        fun putRequestSignUp(context:Context, email: String, pw: String, nickName:String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/user"

            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nickName)
                .build()


            val request = Request.Builder()
                .url(urlString)
                .put(formData)
//                .header()  // API에서 헤더를 요구하면 여기서 첨부.
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    연결 자체에 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 성공 => 어떤 내용이던 응답은 받은 경우
//                    서버의 응답중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

//                    본문 String을 => JSON형태로 변환 => 변수에 저장
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())

//                    JSON 파싱은 => 화면에서 진행하도록 처리. (인터페이스의 역할)
                    handler?.onResponse(json)

                }

            })


        }

    }

//    서버 통신의 응답 내용을 Activity에 전달해주는 인터페이스
    interface JsonResponseHandler {
        fun onResponse(json : JSONObject)
    }

}