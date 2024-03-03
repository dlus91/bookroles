package com.bookroles.service.impl;

import com.bookroles.mapper.DictionaryMapper;
import com.bookroles.service.model.Dictionary;
import com.bookroles.service.IDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: dlus91
 * @Date: 2023/9/27 22:16
 */
@Service
public final class DictionaryServiceImpl implements IDictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    public List<String> search(){
        if(Dictionary.getDictionary().isEmpty()){
            List<Dictionary> list = dictionaryMapper.query();
            Dictionary.setDictionary(list);
        }
        return Dictionary.getAllType();
    }

    @Override
    public List<Dictionary> search(Dictionary dictionary) {
        if (dictionary.type().equals(Dictionary.DictionaryEnum.AUTHOR.getName())) {
            return dictionaryMapper.queryAuthor(dictionary);
        } else if (dictionary.type().equals(Dictionary.DictionaryEnum.PUBLISH_HOUSE.getName())) {
            return dictionaryMapper.queryPublishHouse(dictionary);
        }else {
            if (dictionary.name() == null || StringUtils.isEmpty(dictionary.name())) {
                return Dictionary.getDictionary().get(dictionary.type());
            } else {
                return this.getCacheName(dictionary);
            }
        }
    }

    @Override
    public List<Dictionary> getCacheName(Dictionary dictionary) {
        List<Dictionary> list = new ArrayList<>();
        for(Dictionary cacheDictionary : Dictionary.getDictionary().get(dictionary.type())){
            if(cacheDictionary.name().contains(dictionary.name())){
                list.add(dictionary);
            }
        }
        return list;
    }

    @Override
    public void refresh() {
        List<Dictionary> list = dictionaryMapper.query();
        Dictionary.setDictionary(list);
    }

    @Override
    public boolean validAuthor(Dictionary dictionary) {
        return dictionaryMapper.validAuthorId(dictionary) > 0 ? false : true;
    }

    @Override
    public boolean validPublishHouse(Dictionary dictionary) {
        return dictionaryMapper.validPublishHouseId(dictionary) > 0 ? false : true;
    }

    @Override
    public boolean validCacheName(int id,String type) {
        AtomicBoolean result = new AtomicBoolean(true);
        Optional.ofNullable(Dictionary.getDictionary().get(type)).ifPresent(list -> {
            if(list.stream().anyMatch(resultDic -> resultDic.id() == id)){
                result.set(false);
            }
        });
        return result.get();
    }

    @Override
    public boolean validCacheName(String name,String type) {
        AtomicBoolean result = new AtomicBoolean(true);
        Optional.ofNullable(Dictionary.getDictionary().get(type)).ifPresent(list -> {
            if(list.stream().anyMatch(resultDic -> resultDic.name().equals(name))){
                result.set(false);
            }
        });
        return result.get();
    }

    @Override
    public int addName(Dictionary dictionary) {
        int count = 0;
        if(dictionary.type().equals(Dictionary.DictionaryEnum.ARCHIVE_TYPE.getName())){
            if(validCacheName(dictionary.name(),dictionary.type())) {
                count = dictionaryMapper.saveArchiveTypeName(dictionary);
                if(count > 0){
                    this.refresh();
                }
            }
        }else if(dictionary.type().equals(Dictionary.DictionaryEnum.AUTHOR.getName())){
            if(validAuthor(new Dictionary(0, dictionary.name(), null))){
                count = dictionaryMapper.saveAuthorName(dictionary);
            }
        }else if(dictionary.type().equals(Dictionary.DictionaryEnum.PUBLISH_HOUSE.getName())){
            if(validPublishHouse(new Dictionary(0, dictionary.name(), null))) {
                count = dictionaryMapper.savePublishHouseName(dictionary);
            }
        }else{
            if(validCacheName(dictionary.name(),dictionary.type())) {
                count = dictionaryMapper.saveDictionaryOther(dictionary);
                if(count > 0){
                    this.refresh();
                }
            }
        }
        return count;
    }

    @Override
    public int modifyName(Dictionary dictionary) {
        int count = 0;
        if(dictionary.type().equals(Dictionary.DictionaryEnum.ARCHIVE_TYPE.getName())){
            if(validCacheName(dictionary.name(),dictionary.type())) {
                count = dictionaryMapper.updateArchiveTypeName(dictionary);
                if(count > 0){
                    this.refresh();
                }
            }
        }else if(dictionary.type().equals(Dictionary.DictionaryEnum.AUTHOR.getName())){
            if(validAuthor(new Dictionary(0, dictionary.name(), null))) {
                count = dictionaryMapper.updateAuthorName(dictionary);
            }
        }else if(dictionary.type().equals(Dictionary.DictionaryEnum.PUBLISH_HOUSE.getName())){
            if(validPublishHouse(new Dictionary(0, dictionary.name(), null))) {
                count = dictionaryMapper.updatePublishHouseName(dictionary);
            }
        }else{
            if(validCacheName(dictionary.name(),dictionary.type())) {
                count = dictionaryMapper.updateDictionaryOther(dictionary);
                if(count > 0){
                    this.refresh();
                }
            }
        }
        return count;
    }


}
