package lvdianinsertdate

class XrConsumerController {

    static responseFormats = ['json']

    def xrConsumerService
    def xrIntegralService

    def create() {
        XrConsumer xrConsumer = new XrConsumer(nickname: "nickname", headimgurl: "headimgurl", phone: "phone").save(flush: true)
        xrIntegralService.create(xrConsumer.openid, new Random().nextInt(4) + 2)
        respond xrConsumer
    }
}