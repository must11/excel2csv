package com.github.must11.excel2csv;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 2;
    private boolean firstLine=true;
    private List<String[]> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private final String saveFilePath;
    private int colSize;
    private  CSVWriter writer;
    public NoModelDataListener(String savefilePath) {
        this.saveFilePath = savefilePath;
        this.firstLine=true;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        if (this.firstLine) {

            this.colSize =data.size();
            this.firstLine=false;
            try {
                 this.writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(saveFilePath)).withSeparator(',').build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        cachedDataList.add(create_save_data(data));
        if (this.firstLine || cachedDataList.size() >= BATCH_COUNT) {

                saveData();

            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private String[] create_save_data(Map<Integer, String> data) {
        String[]r =new String[colSize];
        for(Integer i=0;i<colSize;i++) {
           if (data.containsKey(i)) {
               r[i]=data.get(i);
//            }else {
//               r[i]="";
        }
        }
        return r;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
            saveData();
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        for (String[] row:this.cachedDataList) {
            writer.writeNext(row);
        }

    }
}
