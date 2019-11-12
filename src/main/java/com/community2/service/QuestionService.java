package com.community2.service;

import com.community2.dto.QuestionDTO;
import com.community2.model.Question;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author weijia
 * @Date 2019/9/26 19:36
 * @Version 1.0
 */
public interface QuestionService {

    PageInfo<QuestionDTO> pageHelper(Integer pageNum, Integer pageSize,String search);

    QuestionDTO getById(Long id);

    void createOrUpdate(Question question);


    PageInfo<QuestionDTO> getByUserId(Integer pageNum, Integer pageSize, Long id);

    void incView(Long id);

    List<QuestionDTO> selectRelated(QuestionDTO questionDTO);
}
