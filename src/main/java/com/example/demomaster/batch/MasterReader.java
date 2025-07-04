package com.example.demomaster.batch;

import com.example.demomaster.dto.PincodeCreateDTO;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Component
@StepScope
public class MasterReader implements ItemReader<PincodeCreateDTO> {

    private final Iterator<Row> rowIterator;
    private final Workbook workbook;

    public MasterReader(@Value("#{jobParameters['filePath']}") String filePath) throws IOException {

        InputStream inputStream = new FileInputStream(filePath);
        this.workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        this.rowIterator = sheet.rowIterator();
        if(this.rowIterator.hasNext()) this.rowIterator.next();
    }



    @Override
    public PincodeCreateDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (!rowIterator.hasNext()) return null;

        Row row = rowIterator.next();
        PincodeCreateDTO pincodeCreateDTO = new PincodeCreateDTO();

        DataFormatter formatter = new DataFormatter();

        String rawPincode = formatter.formatCellValue(row.getCell(1)).trim();
        if (rawPincode.endsWith(".0")) {
            rawPincode = rawPincode.substring(0, rawPincode.length() - 2);
        }
        pincodeCreateDTO.setPincode(rawPincode);

        String cityId = formatter.formatCellValue(row.getCell(2));
;
        pincodeCreateDTO.setCityId((long) row.getCell(2).getNumericCellValue());

        return pincodeCreateDTO;
    }

    public void closeWorkBook() throws IOException{
        workbook.close();
    }
}
