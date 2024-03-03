package com.bookroles.service.model;

import com.bookroles.controller.vo.DictionaryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: dlus91
 * @Date: 2023/9/28 10:14
 */
public record Dictionary(int id,String name,String type) {

    public Dictionary {
    }

    public Dictionary(int id, String name) {
        this(id, name, null);
    }

    static Logger logger = LoggerFactory.getLogger(Dictionary.class);

    private static Map<String, List<Dictionary>> dictionaryMap = new ConcurrentHashMap<>();

    public static List<String> getAllType(){
        return Arrays.stream(DictionaryEnum.values()).map(dictionaryEnum -> dictionaryEnum.getName()).toList();
    }

    public static Map<String, List<Dictionary>> getDictionary(){
        return dictionaryMap;
    }


    public static void setDictionary(List<Dictionary> list){
        List<Dictionary> authorList = new ArrayList<>();
        List<Dictionary> downloadFormatList = new ArrayList<>();
        List<Dictionary> dictionaryList = new ArrayList<>();
        for(Dictionary dictionary : list){
            if(dictionary.type().equals(DictionaryEnum.ARCHIVE_TYPE.getName())){
                authorList.add(dictionary);
            }else if(dictionary.type().equals(DictionaryEnum.DOWNLOAD_FORMAT.getName())){
                downloadFormatList.add(dictionary);
            }else if(dictionary.type().equals(DictionaryEnum.DICTIONARY.getName())){
                dictionaryList.add(dictionary);
            }

        }
        dictionaryMap.put(DictionaryEnum.ARCHIVE_TYPE.getName(), authorList);
        dictionaryMap.put(DictionaryEnum.DOWNLOAD_FORMAT.getName(), downloadFormatList);
        dictionaryMap.put(DictionaryEnum.DICTIONARY.getName(), dictionaryList);
        logger.info("=====初始化字典表成功=====");
    }

    public DictionaryVo transformVo() {
        DictionaryVo dictionaryVo = new DictionaryVo();
        dictionaryVo.setId(this.id());
        dictionaryVo.setName(this.name());
        dictionaryVo.setType(this.type());
        return dictionaryVo;
    }


    public enum DictionaryEnum {
        //下面两张则是读内存出来的
        ARCHIVE_TYPE("ARCHIVE_TYPE"),
        DOWNLOAD_FORMAT("DOWNLOAD_FORMAT"),
        DICTIONARY("DICTIONARY"),
        //以下两张表 实时查询
        AUTHOR("AUTHOR"),
        PUBLISH_HOUSE("PUBLISH_HOUSE");

        private final String name;

        DictionaryEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
