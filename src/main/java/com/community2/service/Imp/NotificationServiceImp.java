package com.community2.service.Imp;

import com.community2.dto.NotificationDTO;
import com.community2.enums.NotificationStatusEnum;
import com.community2.enums.NotificationTypeEnum;
import com.community2.exception.CustomizeErrorCode;
import com.community2.exception.CustomizeException;
import com.community2.mapper.NotificationMapper;
import com.community2.mapper.UserMapper;
import com.community2.model.Notification;
import com.community2.model.NotificationExample;
import com.community2.model.User;
import com.community2.service.NotificationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author weijia
 * @Date 2019/10/21 17:33
 * @Version 1.0
 */
@Service
public class NotificationServiceImp implements NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageInfo<NotificationDTO> getByUserId(Integer pageNum, Integer pageSize, Long id) {
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
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(id);
        example.setOrderByClause("gmt_create desc");
        PageHelper.startPage(pageNum, pageSize);
        try {
            List<Notification> notifications = notificationMapper.selectByExample(example);
            PageInfo pageInfo = new PageInfo<>(notifications, pageSize);
            List<NotificationDTO> notificationDTOList = new ArrayList<>();
            for (Notification notification : notifications) {
                NotificationDTO notificationDTO = new NotificationDTO();
                BeanUtils.copyProperties(notification, notificationDTO);
                notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
                notificationDTOList.add(notificationDTO);
            }
            pageInfo.setList(notificationDTOList);

            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }

    @Override
    public Long unreadCount(Long userId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());

        return notificationMapper.countByExample(example);
    }

    @Override
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification ==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
