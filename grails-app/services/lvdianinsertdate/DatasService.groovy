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
                xrIntegralService.create(xrConsumer, new Random().nextInt(13))
            }
        }
    }

    /**
     * 定时执行任务
     */
    def renwu(Long max){
        Config config = Config.findByTitle("datas")
        long offset = Long.valueOf(config.content)
        println offset
        println new Date()
        config.content = String.valueOf(Integer.valueOf(config.content) + max)
        config.save(flush:true)

        create(offset, max)
    }
}
