package lvdianinsertdata

import lvdianinsertdate.DatasService

class OrderExpireJob {

    DatasService datasService

    static triggers = {
        simple repeatInterval: 5 * 60 * 1000l // 1分钟
    }

    def execute() {
        int hours = new Date().hours
        if(hours >= 8 && hours < 16){
            datasService.renwu(5L)
        }
    }
}
