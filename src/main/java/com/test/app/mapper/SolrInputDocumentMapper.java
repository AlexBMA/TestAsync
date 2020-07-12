package com.test.app.mapper;

import com.test.app.dto.SmartDocumentDTO;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public  class SolrInputDocumentMapper {

    public static SmartDocumentDTO mapMultipartToDocumentDTO(MultipartFile file, String UPLOAD_DIR){
        String originalFilename = file.getOriginalFilename();
        String filenameExtension = StringUtils.getFilenameExtension(originalFilename);

        SmartDocumentDTO smartDocumentDTO = new SmartDocumentDTO();
        smartDocumentDTO.id = System.currentTimeMillis()+"."+Math.random();
        smartDocumentDTO.name = StringUtils.getFilename(originalFilename);
        if (filenameExtension.equals("pdf")) smartDocumentDTO.isPdf = "true";
        else smartDocumentDTO.isPdf = "false";
        smartDocumentDTO.aiReady = "false";
        smartDocumentDTO.path = UPLOAD_DIR+originalFilename;
        smartDocumentDTO.folderPath = UPLOAD_DIR+originalFilename;
        smartDocumentDTO.type = filenameExtension;

        return smartDocumentDTO;

    }

    public static SmartDocumentDTO mapSolrInputDocumentToDto(SolrInputDocument item) {
        SmartDocumentDTO smartDocumentDTO = new SmartDocumentDTO();
        smartDocumentDTO.id = item.get("id").getValue().toString();
        smartDocumentDTO.name = item.get("name").getValue().toString();
        smartDocumentDTO.isPdf = item.get("is_pdf").getValue().toString();
        smartDocumentDTO.aiReady = item.get("ai_finished").getValue().toString();
        smartDocumentDTO.path = item.get("path").getValue().toString();
        smartDocumentDTO.folderPath = item.get("folder_path").getValue().toString();
        smartDocumentDTO.type = item.get("type").getValue().toString();
        return smartDocumentDTO;
    }

    public static SolrInputDocument mapDTOToSolrInputDocument(SmartDocumentDTO item) {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.setField("id",item.id);
        solrInputDocument.setField("folder_path",item.folderPath);
        solrInputDocument.setField("name",item.name);
        solrInputDocument.setField("path",item.path);
        solrInputDocument.setField("type",item.type);
        solrInputDocument.setField("ai_finished",item.aiReady);
        solrInputDocument.setField("is_pdf",item.isPdf);
        return solrInputDocument;
    }

    public static SmartDocumentDTO mapSolrInputDocumentToDto(SolrDocument item) {
        return null;
    }
}
