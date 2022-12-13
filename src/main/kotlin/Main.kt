import bean.EventDetail
import bean.EventList
import bean.PushPlus
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

var mEventList = mutableListOf<EventDetail.Content>()
var mEventListOdd = mutableListOf<EventDetail.Content>()
var mEventIdList = mutableListOf<Int>()
private val executorService: ExecutorService = Executors.newCachedThreadPool()


//设置最大请求页数(一页10个活动)
const val maxPage = 8

//设置请求间隔(分钟)
const val duration: Long = 120

//设置年级
const val grade = "2022"

//设置院系
const val department = ""

//设置需要过滤的活动分类
val categoryArray = arrayOf("", "")

//设置已加入的部落
val groupArray = arrayOf("", "")


//设置UA，不设置无法获取数据
val headerMap = mutableMapOf<String, String>().apply {
    this["User-Agent"] = "client:Android version:6.9.80 Product:null OsVersion:12"
}
val simpleDateFormat = SimpleDateFormat("yy-MM-dd HH:mm")

fun main() {
    println("开始获取活动列表，当前时间：${simpleDateFormat.format(Date())}")
    getEvent()
}

fun handleData() {
    if (mEventListOdd.isEmpty()) {
        mEventListOdd = mEventList.toMutableList()
        mEventList.clear()
        mEventIdList.clear()
        println("获取完成，共获取到${mEventListOdd.size}个符合的活动")
        var content = ""
        var i = 1
        for (data in mEventListOdd) {
            data.apply {
                content += "**第${i++}个活动**  \n"
                content += ">标题：${name}  \n"
                content += "类型：${category.name} ${credit}分 ${joinNum}/${limitNum}人  \n"
                content += "报名时间：${getTime(regStartTimeStr)} ~ ${getTime(regEndTimeStr)}  \n"
                content += "活动时间：${getTime(startTime)} ~ ${getTime(endTime)}  \n"
                content += "地点：${location}  已签到：${sign_in_num} ${if (is_need_sign_out == "1") "  ***需签退***" else ""}  \n"
                content += "\n---\n"
            }
        }
        if (i > 1) {//有可参加的活动
            println(content)
            sendPush("PU可参与活动推送", content)//发送通知到微信PushPlus
        }
    } else {//todo
        var content = ""
        for (data in mEventList) {

        }
//        sendPush("PU活动变化推送", content)
    }

    //暂时没做PU活动变化推送，所以清除数据
    mEventListOdd.clear()
    //刷新活动
    println("下次获取时间：${simpleDateFormat.format(Date().apply { time += 1000 * 60 * duration })}")
    refresh()
}

fun getEvent(page: Int = 1) {
    HttpUtil.postWithHeader(
        Constants.URL_EventList,
        "oauth_token=${Constants.oauth_token}&oauth_token_secret=${Constants.oauth_token_secret}&page=$page",
        headerMap,
        EventList::class.java
    ) { t ->
        t.content.let {
            for (data in it) data.apply {
                for (cate in categoryArray) if (category == cate && isAllowEvent == 1) mEventIdList.add(id.toInt())
            }
        }
        if (page < maxPage) getEvent(page + 1)
        else if (mEventIdList.isEmpty()) refresh()//若没有活动则等待后继续获取，一般不会遇到
        else getEventDetail(mEventIdList[0])
    }
}

fun getEventDetail(id: Int) {
    HttpUtil.postWithHeader(
        Constants.URL_EventDetail,
        "oauth_token=${Constants.oauth_token}&oauth_token_secret=${Constants.oauth_token_secret}&actiId=$id",
        headerMap,
        EventDetail::class.java
    ) { t ->
        t.content.apply {
            if (allow_school.isEmpty() || allow_school.contains(department))//院系筛选
                if (allow_year.isEmpty() || allow_year.contains(grade))//年级筛选
                    if (allow_group.isEmpty() || allow_group.contains(groupArray))//部落筛选
                        mEventList.add(this)
        }
        mEventIdList.apply {
            if (id == last()) handleData()
            else getEventDetail(this[indexOf(id) + 1])
        }
    }
}

fun refresh() {
    executorService.submit {
        Runnable {
            Thread.sleep(1000 * 60 * duration)
            println("开始获取活动列表，当前时间：${simpleDateFormat.format(Date())}")
            getEvent()
        }.run()
    }
}

fun List<String>.contains(another: Array<String>): Boolean {
    for (i in this) for (j in another) return i == j
    return false
}

fun getTime(timestamp: String): String = simpleDateFormat.format(Date("${timestamp}000".toLong()))


fun sendPush(title: String, msg: String) {
    JsonObject().apply {
        addProperty("token", Constants.pushPlusToken)
        addProperty("title", title)
        addProperty("template", "markdown")
        addProperty("content", msg)
    }.also {
        HttpUtil.post(Constants.URL_PushPlus, it, PushPlus::class.java) { t ->
            when (t.code) {
                900 -> println("用户账号使用受限!!!")
                200 -> println("消息推送成功!!!")
                else -> println(t.msg)
            }
        }
    }
}
