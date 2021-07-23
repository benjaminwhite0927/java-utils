package org.benjamin.util.excel;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * 测试导入Excel 97/2003
 */
public class TestImportExcel {

    @Test
    public void importXls() throws FileNotFoundException {
        File f=new File("src/test/resources/test.xls");
        InputStream inputStream= new FileInputStream(f);

        ExcelLogs logs =new ExcelLogs();
        Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs , 0);

        for(Map m : importExcel){
            System.out.println(m);
        }
    }

    @Test
    public void importXlsx() throws FileNotFoundException {
        File f=new File("src/test/resources/test.xlsx");
        InputStream inputStream= new FileInputStream(f);

        ExcelLogs logs =new ExcelLogs();
        Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs , 0);

        for(Map m : importExcel){
            System.out.println(m);
        }
    }

    @Test
    public void importXlsBean() throws FileNotFoundException {
        File f=new File("src/test/resources/beans.xls");
        InputStream inputStream= new FileInputStream(f);

        ExcelLogs logs =new ExcelLogs();
        Collection<Model> importExcel = ExcelUtil.importExcel(Model.class, inputStream, "yyyy/MM/dd", logs , 0);

        for(Model m : importExcel){
            System.out.println(m);
        }
    }

    @Test
    public void importXlsStudent() throws FileNotFoundException {
        File f=new File("src/test/resources/beans.xls");
        InputStream inputStream= new FileInputStream(f);

        ExcelLogs logs =new ExcelLogs();
        Collection<Student> importExcel = ExcelUtil.importExcel(Student.class, inputStream, "yyyy/MM/dd", logs , 0);

        for(Student m : importExcel){
            System.out.println(m);
        }
    }

}