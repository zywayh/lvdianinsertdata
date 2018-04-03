package lvdianinsertdate

import grails.gorm.transactions.Transactional

@Transactional
class XrIntegralService {


    def create(String openid, int count){

        int surplusIntegral = 0

        XrIntegral integral = new XrIntegral()
        integral.openid = openid
        integral.state = 1
        integral.record = 600
        integral.integralDetail = "赠送积分"
        integral.surplusIntegral = 0
        integral.surplusIntegral += integral.record
        integral.allIntegral = integral.surplusIntegral
//        integral.currentTime = new BigDecimal(String.valueOf(System.currentTimeMillis())).divide(new BigDecimal("1000"),0,BigDecimal.ROUND_HALF_UP)

        String code = ""
        def random = new Random()
        for(def i=0;i<10;i++){
            code += (random.nextInt(10))
        }
        integral.orderNum = "xr" + code
        integral.save(flush:true)
        surplusIntegral = integral.surplusIntegral

        for (int i = 0; i < count; i++) {
            XrIntegral integralx = new XrIntegral()
            integralx.openid = openid
            integralx.state = 1
            integralx.record = 1000
            integralx.integralDetail = "赠送积分"
            integralx.surplusIntegral = surplusIntegral
            integralx.surplusIntegral += integralx.record
            integralx.allIntegral = integralx.surplusIntegral
//            Long time = System.currentTimeMillis() - new Random().nextInt(1000 * 60 * 60 * 24 * 2)
//            integralx.currentTime = new BigDecimal(String.valueOf(time)).divide(new BigDecimal("1000"),0,BigDecimal.ROUND_HALF_UP)

            String code1 = ""
            def random1 = new Random()
            for(int j=0; j<10; j++){
                code1 += (random1.nextInt(10))
            }
            integralx.orderNum = "xr" + code1
            integralx.save(flush:true)
            surplusIntegral = integralx.surplusIntegral
        }
    }
}
