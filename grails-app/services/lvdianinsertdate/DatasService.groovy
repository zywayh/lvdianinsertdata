package lvdianinsertdate

import grails.gorm.transactions.Transactional

@Transactional
class DatasService {

    def readExcelUtilsService
    def xrIntegralService
    def emojiFilterService

    def init(){
        readExcelUtilsService.readExcelToPath().each {
            String nickName = emojiFilterService.filterEmoji(String.valueOf(it.nickName))
            String phone = it.phone
            String headimg = it.headimg
            new Datas(nickname:nickName, phone:phone, headimgurl:headimg).save(flush:true)
        }
        def res = [:]
        return res
    }

    def create(Long offset, Long max){
        Datas.findAll([offset:offset, max:max]).each {
            if(XrConsumer.countByPhone(it.phone) == 0){
                XrConsumer xrConsumer = new XrConsumer(nickname: it.nickname, headimgurl: it.headimgurl, phone: it.phone).save(flush: true)
                xrIntegralService.create(xrConsumer.openid, new Random().nextInt(30) + 1)
            }
        }
    }

    /**
     * 定时执行任务
     */
    def renwu(){
//        int max = new Random().nextInt(200) + 100
//        int max = new Random().nextInt(20) + 10
        int max = 5
        Config config = Config.findByTitle("datas")
        int offset = Integer.valueOf(config.content)
        config.content = String.valueOf(Integer.valueOf(config.content) + max)
        config.save(flush:true)

        create(offset, max)
    }
}
