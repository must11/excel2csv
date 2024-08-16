package com.github.must11.excel2csv;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

//		String fileName ="D:/java/demo.xlsx";
//		EasyExcel.read(fileName, new NoModelDataListener("D:/java/demo.csv")).sheet().doRead();
	}

}
