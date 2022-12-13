object Constants {

    const val URL_EventList = "https://pocketuni.net/index.php?app=api&mod=Event&act=newEventList"
    const val URL_EventDetail = "https://pocketuni.net/index.php?app=api&mod=Event&act=queryActivityDetailById"//actiId
    const val URL_EventNotice = "https://pocketuni.net/index.php?app=api&mod=Event&act=newsList"//event_id&page
    const val URL_EventJoin = "https://pocketuni.net/index.php?app=api&mod=Event&act=join2"//id

    const val URL_PushPlus = "https://www.pushplus.plus/send"


    //填写PU的token，需自行抓包
    var oauth_token = ""

    //填写PU的secret token，需自行抓包
    var oauth_token_secret = ""

    //填写PushPlus的token
    const val pushPlusToken = ""

}