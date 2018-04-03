package lvdianinsertdate

import java.time.LocalDateTime

class XrConsumer {

    String openid
    String nickname
    String headimgurl
    String phone
    Integer gameType = 1
    String endTime
    String intoTime
    String ifPay = "0|0|0"
    Integer ifWait = 0
    Integer level = 0
    Integer gameNums = 0
    Integer helpNums = 2

    static constraints = {
        endTime nullable: true
        intoTime nullable: true
        openid nullable: true
    }

    static mapping = {
        version false
    }

    def beforeInsert() {
        Long time = System.currentTimeMillis() - new Random().nextInt(1000 * 60 * 60 * 24 * 2)
        intoTime = new BigDecimal(String.valueOf(time)).divide(new BigDecimal("1000"),0,BigDecimal.ROUND_HALF_UP)
        endTime = new BigDecimal(String.valueOf(System.currentTimeMillis())).divide(new BigDecimal("1000"),0,BigDecimal.ROUND_HALF_UP)
        openid = phone
    }

    def beforeUpdate() {

    }


}
