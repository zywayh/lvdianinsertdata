package lvdianinsertdate

import grails.gorm.transactions.Transactional

@Transactional
class XrIntegralService {


    def create(XrConsumer xrConsumer, int count){

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


    static void main(String[] ages){
        for (int i = 0; i < 100; i++) {
            println new Random().nextInt(2)
        }
    }
}
