package com.bookroles.tool;

/**
 * @Author: dlus91
 * @Date: 2023/10/11 17:56
 */
public enum LogEnum {

    BUSSINESS_TOMCAT_ACCESS(
            "/tomcat-pattern",
            "H:\\\\programmer\\\\Interviewspace\\\\bookroles\\\\bookroles-manager\\\\log\\\\client\\\\",
            "%{NUMBER:num},%{TOMCATDATE:dateTime},%{IP:ip},%{WORD:method},%{QUOTEDSTRING:url},%{WORDVERSION:httpVersion},%{WORDORNULL:sessionKey},%{LEAVEL:predictedValue},%{GREEDYDATA:params},%{NOTSPACE:httpStatus},%{NUMBER:runTime},%{NUMBER:dataSize},%{QUOTEDSTRING:httpReferrer},%{GREEDYDATA:userAgent}",
            "bookroles_access.",
            ".txt",
            "((202[0-9]-(0[1-9]|1[012]))-(0[1-9]|[12][0-9]|3[01]))",
            "(\\d{4}-\\d{2}-\\d{2})",
            "temp\\\\",
            "_(?:[0-9]+)",
            "bookroles_access.",
            ".tmp",
            "([1-9]|\\d{2})_(?:[0-9]+)"
    );

    private final String filePath;

    private final String patternsPath;

    private final String logRegex;

    private final String prefix;

    private final String suffix;

    private final String validFileNameRegex;

    private final String validAndGroupFileNameRegex;

    private final String tempFileDirectoryPath;

    private final String tempFileDirectoryRegex;

    private final String tempPrefix;

    private final String tempSuffix;

    private final String tempFileRegex;

    public String getPatternsPath() {
        return patternsPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getLogRegex() {
        return logRegex;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getValidFileNameRegex() {
        return validFileNameRegex;
    }

    public String getValidAndGroupFileNameRegex() {
        return validAndGroupFileNameRegex;
    }

    public String getTempFileDirectoryPath() {
        return tempFileDirectoryPath;
    }

    public String getTempFileDirectoryRegex() {
        return tempFileDirectoryRegex;
    }

    public String getTempPrefix() {
        return tempPrefix;
    }

    public String getTempSuffix() {
        return tempSuffix;
    }

    public String getTempFileRegex() {
        return tempFileRegex;
    }

    //文件前缀和 临时文件可以不一样的， 但暂时没想出更好的命名方式，后续更进
    LogEnum(String patternsPath, String filePath, String logRegex, String prefix, String suffix, String validFileNameRegex, String validAndGroupFileNameRegex,
            String tempFileDirectoryPath, String tempFileDirectoryRegex, String tempPrefix, String tempSuffix, String tempFileRegex) {
        this.patternsPath = patternsPath;
        this.filePath = filePath;
        this.logRegex = logRegex;
        this.prefix = prefix;
        this.suffix = suffix;
        this.validFileNameRegex = "^" + prefix + validFileNameRegex + suffix + "$";
        this.validAndGroupFileNameRegex = prefix + validAndGroupFileNameRegex + suffix;
        //临时文件夹在文件夹的下下一层
        this.tempFileDirectoryPath = filePath + tempFileDirectoryPath;
        this.tempFileDirectoryRegex = "^" + tempPrefix + validAndGroupFileNameRegex + tempFileDirectoryRegex;
        this.tempPrefix = tempPrefix;
        this.tempSuffix = tempSuffix;
        this.tempFileRegex = "^" + tempPrefix + tempFileRegex + tempSuffix;
    }

}
