package com.example.msloader.service;

import com.example.msloader.model.CDR;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CDRParser {
    List<CDR> parse(InputStream inputStream, String filename) throws IOException;
}
