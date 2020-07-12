package com.test.app.configuration;

import com.test.app.dto.FileForLater;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SingleFolderByteMap {

    private static ConcurrentHashMap<String, List<FileForLater>> folderMultipartMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, List<FileForLater>> getFolderMultipartMap() {
        return folderMultipartMap;
    }
}
