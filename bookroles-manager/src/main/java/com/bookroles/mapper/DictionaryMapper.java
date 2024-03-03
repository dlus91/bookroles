package com.bookroles.mapper;

import com.bookroles.service.model.Dictionary;

import java.util.List;

/**
 * @Author: dlus91
 * @Date: 2023/9/27 9:45
 */
public interface DictionaryMapper {

    List<Dictionary> query();

    int saveArchiveTypeName(Dictionary dictionary);

    int updateArchiveTypeName(Dictionary dictionary);

    int saveDictionaryOther(Dictionary dictionary);

    int updateDictionaryOther(Dictionary dictionary);

    List<Dictionary> queryAuthor(Dictionary dictionary);

    int validAuthorId(Dictionary dictionary);

    int saveAuthorName(Dictionary dictionary);

    int updateAuthorName(Dictionary dictionary);

    List<Dictionary> queryPublishHouse(Dictionary dictionary);

    int validPublishHouseId(Dictionary dictionary);

    int savePublishHouseName(Dictionary dictionary);

    int updatePublishHouseName(Dictionary dictionary);

}
