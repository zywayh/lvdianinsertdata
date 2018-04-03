package lvdianinsertdate


class XrIntegralController {

    def xrIntegralService

    def create(){
        XrIntegral integral = new XrIntegral()
        integral.openid = "openid"
        integral.state = 1
        integral.record = 600
        integral.integralDetail = "赠送积分"
        integral.surplusIntegral = integral.record
        integral.allIntegral = integral.surplusIntegral
//        integral.currentTime = new BigDecimal(String.valueOf(System.currentTimeMillis())).divide(new BigDecimal("1000"),0,BigDecimal.ROUND_HALF_UP)

        String code = ""
        def random = new Random()
        for(def i=0;i<10;i++){
            code += (random.nextInt(10))
        }
        integral.orderNum = "xr" + code
        return integral.save(flush:true)

//        xrIntegralService.create("test", 5)

        def res = [:]
        respond res
    }
}
