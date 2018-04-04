package lvdianinsertdate

class BootStrap {

    def init = { servletContext ->

        String title = "datas"
        if(Config.countByTitle(title) == 0){
            new Config(title:title, content: "0").save(flush:true)
        }

    }
    def destroy = {
    }
}
