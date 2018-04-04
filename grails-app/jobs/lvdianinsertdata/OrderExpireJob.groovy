package lvdianinsertdata

import lvdianinsertdate.DatasService

class OrderExpireJob {

    DatasService datasService

    static triggers = {
        simple repeatInterval: 3 * 60 * 1000l // 1分钟
    }

    def execute() {
        int hours = new Date().hours
        if(hours >= 8 && hours < 18){
            if(hours == 9 || hours == 10 || hours == 11 || hours == 12 || hours == 13 || hours == 14){
                datasService.renwu(Long.valueOf(new Random().nextInt(50)))
            }else{
                datasService.renwu(Long.valueOf(new Random().nextInt(30)))
            }
        }
    }
}
