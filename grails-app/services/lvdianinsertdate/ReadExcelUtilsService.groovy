package lvdianinsertdate

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ReadExcelUtilsService {

    static void main(String[] ages){
        ReadExcelUtilsService readExcelUtilsService = new ReadExcelUtilsService()
        List<Map<String,Object>> list =  readExcelUtilsService.readExcelToPath("D:\\\\data.xlsx")
        list.each {
            println it.toString()
        }
    }

    List<Map<String,Object>> readExcelToPath(){
        return readExcelToPath("D:\\\\data1.xlsx")
    }

    List<Map<String,Object>> readExcelToPath(String path){
        if(path){
            InputStream inputStream = new FileInputStream(path)
            String ext = path.substring(path.lastIndexOf("."))
            if(".xls" == ext){
                return readExcel(new HSSFWorkbook(inputStream))
            }else if(".xlsx" == ext){
                return readExcel(new XSSFWorkbook(inputStream))
            }else{
                throw new Exception("Workbook对象为空！")
            }
        }else{
            throw new Exception("Workbook对象为空！")
        }
    }

    List<Map<String,Object>> readExcelToUrl(String url){
        if(url){
            InputStream openStream = new URL(url).openStream()
            InputStream inputStream = new BufferedInputStream(openStream)
            String ext = url.substring(url.lastIndexOf("."))
            if(".xls" ==  ext){
                return readExcel(new HSSFWorkbook(inputStream))
            }else if(".xlsx" == ext){
                return readExcel(new XSSFWorkbook(inputStream))
            }else{
                throw new Exception("Workbook对象为空！")
            }
        }else{
            throw new Exception("Workbook对象为空！")
        }
    }

    List<Map<String,Object>> readExcel(Workbook workbook){
        String[] readExcelTitle = readExcelTitle(workbook)
        List<Map<String,Object>> contentList = new ArrayList<>()
        Sheet sheet = workbook.getSheetAt(0)
        // 得到总行数
        int rowNum = sheet.getLastRowNum()
        Row row = sheet.getRow(0)
        int colNum = row.getPhysicalNumberOfCells()
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 0; i < rowNum; i++) {
            row = sheet.getRow(i + 1)
            Map<String,Object> cellValue = new HashMap<String, Object>()
            for (int j = 0; j < colNum; j++) {
                cellValue.put(readExcelTitle[j], getCellFormatValue(row.getCell(j)))
            }
            contentList.add(cellValue)
        }
        return contentList;
    }

    String[] readExcelTitle(Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = String.valueOf(getCellFormatValue(row.getCell(i)));
//			title[i] = row.getCell(i).getCellFormula();
        }
        return title;
    }

    def getCellFormatValue(Cell cell) {
        def cellvalue = "";
        if (cell) {
            // 判断当前Cell的Type
            if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){// 如果当前Cell的Type为NUMERIC
                cellvalue = cell.getNumericCellValue()
                String test = String.valueOf(cellvalue)
                test = test.replace(".","")
                test = test.replace("E10","")
                cellvalue = test
            }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
                // 判断当前的cell是否为Date
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    // data格式是带时分秒的：2013-7-10 0:00:00
                    // cellvalue = cell.getDateCellValue().toLocaleString();
                    // data格式是不带带时分秒的：2013-7-10
                    java.util.Date date = cell.getDateCellValue()
                    cellvalue = date
                } else {// 如果是纯数字
                    // 取得当前Cell的数值
                    cellvalue = String.valueOf(cell.getNumericCellValue())
                }
            }else if(cell.getCellType() == Cell.CELL_TYPE_STRING){// 如果当前Cell的Type为STRING
                cellvalue = cell.getRichStringCellValue().getString()
            }else{
                cellvalue = cell
            }
        }
        return cellvalue;
    }

    /**
     * 导出excel表格
     **/
//    def exportExcelToCard(List<Card> list) {
//        // 第一步，创建一个webbook，对应一个Excel文件
//        HSSFWorkbook wb = new HSSFWorkbook()
//        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
//        HSSFSheet sheet = wb.createSheet("卡信息")
//        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
//        HSSFRow row = sheet.createRow((int) 0)
//        // 第四步，创建单元格，并设置值表头 设置表头居中
//        HSSFCellStyle style = wb.createCellStyle()
////        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//
//        HSSFCell cell = row.createCell((short) 0)
//        cell.setCellValue("卡号")
//        cell.setCellStyle(style)
//        cell = row.createCell((short) 1)
//        cell.setCellValue("密码")
//        cell.setCellStyle(style)
//        cell = row.createCell((short) 2)
//        cell.setCellValue("金额")
//        cell.setCellStyle(style)
//        cell = row.createCell((short) 3)
//        cell.setCellValue("链接")
//        cell.setCellStyle(style)
//
//        for (int i = 0; i < list.size(); i++) {
//            row = sheet.createRow((int) i + 1)
//            Card card = list.get(i)
//            // 第四步，创建单元格，并设置值
//            row.createCell((short) 0).setCellValue(card.num + "")
//            row.createCell((short) 1).setCellValue(card.pass + "")
//            row.createCell((short) 2).setCellValue((card.amount / 100) + "")
//            row.createCell((short) 3).setCellValue(card.qrCodeUrl + "")
//        }
//        // 第六步，将文件存到指定位置s
//        // TODO 调研 HSSFWorkbook 转 FileInputStream
//        String name = "card-" + UUID.randomUUID().toString().replace("-", "")+ ".xls"
//        File file = new File("/tmp/"+name)
//        try {
//            FileOutputStream fout = new FileOutputStream(file);
//            wb.write(fout);
//            fout.close();
//
//            FileInputStream inputStream = new FileInputStream(file)
//            if(inputStream){
//                def url = fileUploadService.upload(name, inputStream)
//                log.debug("-----------exportExcelToCard----------" + url)
//                return url
//            }else{
//                log.error("没有找到文件")
//                return null
//            }
//        }catch (Exception e) {
//            log.error(e.getMessage())
//            return null
//        }
//    }

}
