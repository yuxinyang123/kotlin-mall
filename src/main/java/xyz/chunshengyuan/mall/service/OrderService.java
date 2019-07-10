package xyz.chunshengyuan.mall.service;

import org.springframework.stereotype.Service;
import xyz.chunshengyuan.mall.mapper.OrderMapper;

import javax.annotation.Resource;

/**
 * @author leemaster
 * @Title: OrderService
 * @Package xyz.chunshengyuan.mall.service
 * @Description:
 * @date 2019-07-1017:49
 */
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;


}
