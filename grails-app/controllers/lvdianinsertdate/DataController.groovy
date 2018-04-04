package lvdianinsertdate

class DataController {

    static responseFormats = ['json']

    def datasService

    /**
     * 整理数据
     * @return
     */
    def init(){
        respond datasService.init()
    }

    /**
     * 根据整理的数据插入到数据库中
     * @param offset
     * @param max
     * @return
     */
    def create(Long offset, Long max){
        datasService.create(offset, max)
    }

    def renwu(){
        datasService.renwu()
        def res = [aaa:"aaa"]
        respond res
    }


}