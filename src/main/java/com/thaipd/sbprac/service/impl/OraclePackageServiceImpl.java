package com.thaipd.sbprac.service.impl;

import com.thaipd.sbprac.repository.ProcedureRepository;
import com.thaipd.sbprac.service.OraclePackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OraclePackageServiceImpl implements OraclePackageService {
    private static final Logger logger = LoggerFactory.getLogger(OraclePackageServiceImpl.class);

    @Autowired
    ProcedureRepository procedureRepository;

    @Override
    public void callStoredProcedure() {
        procedureRepository.testCallProcedureOutList();
    }

    @Override
    public void testCallProcedureOutList() {
        procedureRepository.testCallProcedureOutList();
    }
}
