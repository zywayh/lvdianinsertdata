package lvdianinsertdate

import grails.gorm.transactions.Transactional

@Transactional
class DatasService {

    def readExcelUtilsService
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
        println new Date()
        println offset
        println max
        Datas.findAll([offset:offset, max:max]).each {
            println "Datas id: " + it.id
            //验证手机号是否存在
            if(XrConsumer.countByPhone(it.phone) == 0){
                XrConsumer xrConsumer = new XrConsumer(nickname: it.nickname, headimgurl: it.headimgurl, phone: it.phone).save(flush: true)
                println "XrConsumer: " + it.id
                createXrConsumer(xrConsumer, new Random().nextInt(13))
            }
        }
    }

    /**
     * 定时执行任务
     */
    def renwu(Long max){
        Config config = Config.findByTitle("datas")
        long offset = Long.valueOf(config.content)
        config.content = String.valueOf(Integer.valueOf(config.content) + max)
        config.save(flush:true)
        create(offset, max)
    }


    def createXrConsumer(XrConsumer xrConsumer, int count){

        String openid = xrConsumer.openid

        int surplusIntegral = 0

        XrIntegral integral = new XrIntegral()
        integral.openid = openid
        integral.state = 1
        integral.record = 600
        integral.integralDetail = "赠送积分"
        integral.surplusIntegral = 0
        integral.surplusIntegral += integral.record
        integral.allIntegral = integral.surplusIntegral
        integral.currentTimeBak = xrConsumer.intoTime

        String code = ""
        def random = new Random()
        for(def i=0;i<10;i++){
            code += (random.nextInt(10))
        }
        integral.orderNum = "xr" + code
        integral.save(flush:true)
        surplusIntegral = integral.surplusIntegral

        Long time = Long.valueOf(integral.currentTimeBak)

        if(random.nextInt(2)){

            for (int i = 0; i < count; i++) {
                XrIntegral integralx = new XrIntegral()
                integralx.openid = openid
                integralx.state = 1
                integralx.record = 1000
                integralx.integralDetail = "完成关卡获得积分"
                integralx.surplusIntegral = surplusIntegral
                integralx.surplusIntegral += integralx.record
                integralx.allIntegral = integralx.surplusIntegral
                time = time + new Random().nextInt(60 * 60) + 60 * 10
                integralx.currentTimeBak = String.valueOf(time)

                String code1 = ""
                for(int j=0; j<10; j++){
                    code1 += (random.nextInt(10))
                }
                integralx.orderNum = "xr" + code1
                integralx.save(flush:true)
                surplusIntegral = integralx.surplusIntegral
            }

        }

    }

}
