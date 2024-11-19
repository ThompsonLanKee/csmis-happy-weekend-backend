package com.spring.csmis.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public interface DoorLogAccessTransactionService {
    void importDoorLogFromExcel(MultipartFile file) throws IOException;
}
