package com.eviro.assesment.grad001.bhekimautjana.file_parser;

import java.io.File;
import java.net.URI;

public interface FileParser {

    void parseCSv(File csvFile);
    File convertCSVDataToImage(String base64ImageData,String extension);
    URI createImageLink(File fileImage);
}
