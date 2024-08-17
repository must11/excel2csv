package com.github.must11.excel2csv;

import com.alibaba.excel.EasyExcel;

import java.io.File;

public class ExcelDeal {

    public static void toCSV(String source, String target) {
        EasyExcel.read(source, new NoModelDataListener(target)).sheet().doRead();
    }

    public static void toCSV(File source, String target) {
        EasyExcel.read(source, new NoModelDataListener(target)).sheet().doRead();
    }

}
