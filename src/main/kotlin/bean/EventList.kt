package bean

data class EventList(
    val code: Int, // 0
    val content: List<Content>,
    val message: String // success
) {
    data class Content(
        val address: String,
        val allow: String, // 0
        val area: Int, // 2
        val cName: String, // 院
        val category: String,
        val cover: String,
        val coverId: String,
        val credit_name: String, // 得分
        val credit_num: Double,
        val deadline: String,
        val description: String,
        val eTime: String,
        val eventStatus: Int, // 3
        val free_attend: String, // 0
        val friendCount: Int, // 0
        val hit: String, // 0
        val id: String,
        val isAllowEvent: Int, // 1
        val isCredit: String, // 学分
        val isTop: String,
        val is_need_sign_out: String, // 需签退：0=否 1=是
        val is_outside: String, // 线上活动：0=否 1=是
        val is_prov_event: String, // 0
        val is_school_event: String,
        val joinCount: Int,
        val limitCount: Int,
        val note: String, // 0.0
        val sTime: String,
        val school_audit: String,
        val score: String, // 0
        val startline: String,
        val status: String, // 1
        val tags: List<Tag>,
        val title: String,
        val typeId: String,
        val typeId2: String // 0
    ) {
        data class Tag(
            val color: String, // #FF0000
            val id: Int, // 0
            val name: String // 需要签退
        )
    }
}