package com.test.app.smartbox;

import org.apache.solr.client.solrj.beans.Field;

public class SmartDocument {

    @Field("id")
    private String id;

    @Field("doc_id")
    private String doc_id;

    @Field("name")
    private String name;

    @Field("path")
    private String path;

    @Field("type")
    private String type;

    @Field("owner")
    private String owner;

    @Field("color")
    private String color;

    @Field("folder_path")
    private String folderPath;

    @Field("is_pdf")
    private String isPdf;

    @Field("ai_finished")
    private String aiFinished;

    @Field("modified")
    private String modified;

    @Field("document_bytes")
    private String documentBytes;

    @Field("document_size")
    private String documentSize;

    @Field("file_type")
    private String file_type;

    @Field("total_pages")
    private String total_pages;

    @Field("landscape")
    private String landscape;

    @Field("aspect_ratio")
    private String aspectRatio;

    @Field("height")
    private String height;

    @Field("widht")
    private String widht;

    @Field("thumbnail")
    private String thumbnails;

    @Field("canvas_element")
    private String canvasElement;

    @Field("pdf_validation")
    private String pdf_validation;

    @Field("errorMessage")
    private String error_message;

    @Field("document_notes")
    private String documentNotes;

    @Field("setting_created")
    private String settingCreated;

    @Field("setting_authors")
    private String settingAuthors;

    @Field("setting_availability")
    private String settingAvailability;

    @Field("setting_location")
    private String settingLocation;

    @Field("setting_documents")
    private String settingDocuments;

    @Field("setting_download")
    private String settingDownload;

    @Field("setting_access_options")
    private String settingAccessOptions;

    @Field("setting_size")
    private String settingSize;

    @Field("setting_sharing_permissions")
    private String settingSharingPermissions;

    @Field("images")
    private String images;

    @Field("setting_tags")
    private String settingTags;

    @Field("setting_categories")
    private String settingCategories;

    @Field("setting_found_names")
    private String settingFoundNames;

    @Field("setting_total_redactions")
    private String settingTotalRedactions;

    @Field("setting_total_annotations")
    private String settingTotalAnnotations;

    @Field("big_file")
    private String bigFile;

    @Field("big_file_processed")
    private String bigFileProcessed;

    @Field("is_opened")
    private String isOpened;

    @Field("all_processing_done")
    private String allBulkProcessingDone;

    @Field("bulk_processing_started")
    private String bulkProcessingStarted;

    @Field("setting_ai_tags")
    private String settingAiTags;

    @Field("setting_regex_tags")
    private String settingRegexTags;

    @Field("finalized_doc_path")
    private String finalizedDocPath;

    @Field("setting_manual_tags")
    private String settingManualTags;

    @Field("analytics_filter")
    private String analyticsFilter;

    @Field("ui_path")
    private String uiPath;

    @Field("is_ocr")
    private String isOcr;

    @Field("tags")
    private String tags;

    @Field("word_status")
    private String wordStatus;

    @Field("directory")
    private String dictionary;

    @Field("finalise_folder")
    private String finaliseFolder;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getIsPdf() {
        return isPdf;
    }

    public void setIsPdf(String isPdf) {
        this.isPdf = isPdf;
    }

    public String getAiFinished() {
        return aiFinished;
    }

    public void setAiFinished(String aiFinished) {
        this.aiFinished = aiFinished;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getDocumentBytes() {
        return documentBytes;
    }

    public void setDocumentBytes(String documentBytes) {
        this.documentBytes = documentBytes;
    }

    public String getDocumentSize() {
        return documentSize;
    }

    public void setDocumentSize(String documentSize) {
        this.documentSize = documentSize;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getLandscape() {
        return landscape;
    }

    public void setLandscape(String landscape) {
        this.landscape = landscape;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidht() {
        return widht;
    }

    public void setWidht(String widht) {
        this.widht = widht;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getCanvasElement() {
        return canvasElement;
    }

    public void setCanvasElement(String canvasElement) {
        this.canvasElement = canvasElement;
    }

    public String getPdf_validation() {
        return pdf_validation;
    }

    public void setPdf_validation(String pdf_validation) {
        this.pdf_validation = pdf_validation;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getDocumentNotes() {
        return documentNotes;
    }

    public void setDocumentNotes(String documentNotes) {
        this.documentNotes = documentNotes;
    }

    public String getSettingCreated() {
        return settingCreated;
    }

    public void setSettingCreated(String settingCreated) {
        this.settingCreated = settingCreated;
    }

    public String getSettingAuthors() {
        return settingAuthors;
    }

    public void setSettingAuthors(String settingAuthors) {
        this.settingAuthors = settingAuthors;
    }

    public String getSettingAvailability() {
        return settingAvailability;
    }

    public void setSettingAvailability(String settingAvailability) {
        this.settingAvailability = settingAvailability;
    }

    public String getSettingLocation() {
        return settingLocation;
    }

    public void setSettingLocation(String settingLocation) {
        this.settingLocation = settingLocation;
    }

    public String getSettingDocuments() {
        return settingDocuments;
    }

    public void setSettingDocuments(String settingDocuments) {
        this.settingDocuments = settingDocuments;
    }

    public String getSettingDownload() {
        return settingDownload;
    }

    public void setSettingDownload(String settingDownload) {
        this.settingDownload = settingDownload;
    }

    public String getSettingAccessOptions() {
        return settingAccessOptions;
    }

    public void setSettingAccessOptions(String settingAccessOptions) {
        this.settingAccessOptions = settingAccessOptions;
    }

    public String getSettingSize() {
        return settingSize;
    }

    public void setSettingSize(String settingSize) {
        this.settingSize = settingSize;
    }

    public String getSettingSharingPermissions() {
        return settingSharingPermissions;
    }

    public void setSettingSharingPermissions(String settingSharingPermissions) {
        this.settingSharingPermissions = settingSharingPermissions;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getSettingTags() {
        return settingTags;
    }

    public void setSettingTags(String settingTags) {
        this.settingTags = settingTags;
    }

    public String getSettingCategories() {
        return settingCategories;
    }

    public void setSettingCategories(String settingCategories) {
        this.settingCategories = settingCategories;
    }

    public String getSettingFoundNames() {
        return settingFoundNames;
    }

    public void setSettingFoundNames(String settingFoundNames) {
        this.settingFoundNames = settingFoundNames;
    }

    public String getSettingTotalRedactions() {
        return settingTotalRedactions;
    }

    public void setSettingTotalRedactions(String settingTotalRedactions) {
        this.settingTotalRedactions = settingTotalRedactions;
    }

    public String getSettingTotalAnnotations() {
        return settingTotalAnnotations;
    }

    public void setSettingTotalAnnotations(String settingTotalAnnotations) {
        this.settingTotalAnnotations = settingTotalAnnotations;
    }

    public String getBigFile() {
        return bigFile;
    }

    public void setBigFile(String bigFile) {
        this.bigFile = bigFile;
    }

    public String getBigFileProcessed() {
        return bigFileProcessed;
    }

    public void setBigFileProcessed(String bigFileProcessed) {
        this.bigFileProcessed = bigFileProcessed;
    }

    public String getIsOpened() {
        return isOpened;
    }

    public void setIsOpened(String isOpened) {
        this.isOpened = isOpened;
    }

    public String getAllBulkProcessingDone() {
        return allBulkProcessingDone;
    }

    public void setAllBulkProcessingDone(String allBulkProcessingDone) {
        this.allBulkProcessingDone = allBulkProcessingDone;
    }

    public String getBulkProcessingStarted() {
        return bulkProcessingStarted;
    }

    public void setBulkProcessingStarted(String bulkProcessingStarted) {
        this.bulkProcessingStarted = bulkProcessingStarted;
    }

    public String getSettingAiTags() {
        return settingAiTags;
    }

    public void setSettingAiTags(String settingAiTags) {
        this.settingAiTags = settingAiTags;
    }

    public String getSettingRegexTags() {
        return settingRegexTags;
    }

    public void setSettingRegexTags(String settingRegexTags) {
        this.settingRegexTags = settingRegexTags;
    }

    public String getFinalizedDocPath() {
        return finalizedDocPath;
    }

    public void setFinalizedDocPath(String finalizedDocPath) {
        this.finalizedDocPath = finalizedDocPath;
    }

    public String getSettingManualTags() {
        return settingManualTags;
    }

    public void setSettingManualTags(String settingManualTags) {
        this.settingManualTags = settingManualTags;
    }

    public String getAnalyticsFilter() {
        return analyticsFilter;
    }

    public void setAnalyticsFilter(String analyticsFilter) {
        this.analyticsFilter = analyticsFilter;
    }

    public String getUiPath() {
        return uiPath;
    }

    public void setUiPath(String uiPath) {
        this.uiPath = uiPath;
    }

    public String getIsOcr() {
        return isOcr;
    }

    public void setIsOcr(String isOcr) {
        this.isOcr = isOcr;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getWordStatus() {
        return wordStatus;
    }

    public void setWordStatus(String wordStatus) {
        this.wordStatus = wordStatus;
    }

    public String getDictionary() {
        return dictionary;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    public String getFinaliseFolder() {
        return finaliseFolder;
    }

    public void setFinaliseFolder(String finaliseFolder) {
        this.finaliseFolder = finaliseFolder;
    }
}
