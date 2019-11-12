package com.community2.service.Imp;

import com.community2.dto.QuestionDTO;
import com.community2.exception.CustomizeErrorCode;
import com.community2.exception.CustomizeException;
import com.community2.mapper.QuestionExtMapper;
import com.community2.mapper.QuestionMapper;
import com.community2.mapper.UserMapper;
import com.community2.model.Question;
import com.community2.model.QuestionExample;
import com.community2.model.User;
import com.community2.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author weijia
 * @Date 2019/9/26 19:38
 * @Version 1.0
 */
@Service
public class QuestionServiceImp implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ',');
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());

        return questionDTOS;
    }

    @Override
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    @Override
    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        } else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (update != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    @Override
    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public PageInfo<QuestionDTO> getByUserId(Integer pageNum, Integer pageSize, Long id) {

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(id);
        PageInfo<QuestionDTO> pageInfo = getPageInfo(pageNum, pageSize, example);
        if (pageInfo != null) return pageInfo;
        return null;
    }

    @Override
    public PageInfo<QuestionDTO> pageHelper(Integer pageNum, Integer pageSize,String search) {
        if (StringUtils.isNotBlank(search)) {
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andTitleLike("%"+search+"%");
            PageInfo<QuestionDTO> pageInfo = getPageInfo(pageNum, pageSize, questionExample);
            if (pageInfo != null)
                return pageInfo;
        }
    
        QuestionExample example = new QuestionExample();
        PageInfo<QuestionDTO> pageInfo = getPageInfo(pageNum, pageSize ,example);
        if (pageInfo != null) return pageInfo;
        return null;
    }

    private PageInfo<QuestionDTO> getPageInfo(Integer pageNum, Integer pageSize,QuestionExample example) {
        //判断非空
        if (pageNum == null) {
            pageNum = 1; //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5; //设置默认每页显示的数据数
        }
        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        example.setOrderByClause("gmt_create desc");
        PageHelper.startPage(pageNum, pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Question> questions = questionMapper.selectByExampleWithBLOBs(example);
            PageInfo pageInfo = new PageInfo<>(questions, pageSize);
            List<QuestionDTO> questionDTOList = new ArrayList<>();
            for (Question question : questions) {
                User user = userMapper.selectByPrimaryKey(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
            pageInfo.setList(questionDTOList);

            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }
}
