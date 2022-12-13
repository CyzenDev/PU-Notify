package bean

data class EventDetail(
    val code: Int, // 0
    val content: Content,
    val message: String // success
) {
    data class Content(
        val actiIcon: String,
        val actiId: String,
        val actiNotice: ActiNotice,
        val actiPoster: String,
        val allow: String, // 0
        val allow_area: List<Any>,
        val allow_area_obj: List<Any>,
        val allow_group: List<String>,
        val allow_group_obj: List<AllowGroupObj>,
        val allow_school: List<String>,
        val allow_school_objs: List<Any>,
        val allow_user_type: String, // 1
        val allow_year: List<String>,
        val announce: Announce,
        val approval: String, // 0
        val audit_uid: String,
        val audit_user: String,
        val button_status: Int, // 1
        val cTime: String,
        val category: Category,
        val checkoutFlag: Int, // 1
        val collectFlag: Int, // 0
        val contact_user: String,
        val contact_user_phone: String,
        val cost: String, // 免费
        val createrId: String,
        val createrName: String,
        val credit: String, // 8
        val creditName: String, // 得分
        val current_time: Int,
        val descs: String,
        val descsUrl: String,
        val endTime: String,
        val eventStatus: Int, // 3
        val event_user_status: List<EventUserStatu>,
        val free_attend: String, // 0
        val gid: String,
        val hours: String, // 0
        val input_list: List<Any>,
        val isAllow: Int, // 1
        val isExpire: Int, // 0
        val isJoin: Int, // 0
        val isTicket: String, // 0
        val isVote: String, // 0
        val is_evaluate: Int, // 0
        val is_gps_sign: String, // 0
        val is_need_sign_out: String, // 0
        val is_outside: String, // 0
        val is_school_event: String,
        val joinNum: Int,
        val leftNum: Int,
        val levelId: String, // 0
        val limitNum: Int,
        val location: String,
        val name: String,
        val needTel: String, // 0
        val notRegSignIn: String, // 0
        val orga_name: String,
        val permission: Int,
        val pu_amount: String,
        val regEndTimeStr: String,
        val regStartTimeStr: String,
        val regStatus: Int, // 2
        val schoolArea: List<Any>,
        val score: String, // 0
        val seriesName: String,
        val show_event_photo_button: String, // 0
        val show_hours: String, // 0
        val sid: String,
        val signTips: String, // 只需签到
        val sign_in_num: String, // 1
        val sign_in_start_time: String,
        val sign_in_status: SignInStatus,
        val sign_out_num: Int, // 0
        val sign_out_start_time: String,
        val sign_out_status: SignOutStatus,
        val startTime: String,
        val status: Int, // 2
        val tags: List<Any>,
        val thinAssn: ThinAssn,
        val type: Int, // 0
        val type2_name: String,
        val typeId2: String, // 0
        val uid: String,
        val xm_id: String, // 0
        val xm_name: String
    ) {
        class ActiNotice

        data class AllowGroupObj(
            val id: String,
            val name: String
        )

        class Announce

        data class Category(
            val categoryId: String,
            val name: String
        )

        data class EventUserStatu(
            val desc: String, // 等待报名
            val status: Int, // 1
            val title: String // 报名
        )

        class SignInStatus

        class SignOutStatus

        data class ThinAssn(
            val assnId: String,
            val icon: String,
            val name: String
        )
    }
}