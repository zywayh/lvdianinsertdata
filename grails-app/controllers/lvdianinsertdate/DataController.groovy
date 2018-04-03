package lvdianinsertdate

class DataController {

    static responseFormats = ['json']

    def readExcelUtilsService
    def emojiFilterService
    def xrIntegralService

    def init(){
//        def list = readExcelUtilsService.readExcelToPath()
//        for (int i = 0; i < 10; i++) {
//            println list[i]
//        }

        readExcelUtilsService.readExcelToPath().each {
            String nickName = emojiFilterService.filterEmoji(String.valueOf(it.nickName))
            String phone = it.phone
            String headimg = it.headimg
            new Datas(nickname:nickName, phone:phone, headimgurl:headimg).save(flush:true)
        }
        def res = [:]
        respond res
    }

    def create(Long offset, Long max){
        Datas.findAll([offset:offset, max:max]).each {
            XrConsumer xrConsumer = new XrConsumer(nickname: it.nickname, headimgurl: it.headimgurl, phone: it.phone).save(flush: true)
            xrIntegralService.create(xrConsumer.openid, new Random().nextInt(20) + 1)
        }
    }


}