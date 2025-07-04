package com.example.demomaster.batch;

import com.example.demomaster.dto.PincodeCreateDTO;
import com.example.demomaster.service.PincodeService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MasterWriter implements ItemWriter<PincodeCreateDTO> {

    @Autowired
    private PincodeService pincodeService;

    @Override
    public void write(Chunk<? extends PincodeCreateDTO> chunk) throws Exception {

        for (PincodeCreateDTO pincodeCreateDTO : chunk){
            pincodeService.saveItemWriter(pincodeCreateDTO);
        }
    }
}
