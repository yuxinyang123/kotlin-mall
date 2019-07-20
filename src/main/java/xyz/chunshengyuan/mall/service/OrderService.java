package xyz.chunshengyuan.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.chunshengyuan.mall.exceptions.ApiOperationException;
import xyz.chunshengyuan.mall.mapper.GoodsPlusMapper;
import xyz.chunshengyuan.mall.mapper.OrderGoodRelMapper;
import xyz.chunshengyuan.mall.mapper.OrderMapper;
import xyz.chunshengyuan.mall.model.bo.OrderGoods;
import xyz.chunshengyuan.mall.model.bo.UserOrder;
import xyz.chunshengyuan.mall.model.po.Goods;
import xyz.chunshengyuan.mall.model.po.Order;
import xyz.chunshengyuan.mall.model.po.OrderGoodRel;
import xyz.chunshengyuan.mall.model.vo.OrderForm;
import xyz.chunshengyuan.mall.model.vo.OrderListVo;
import xyz.chunshengyuan.mall.model.vo.UserOrderListVo;
import xyz.chunshengyuan.mall.utils.RequestContext;
import xyz.chunshengyuan.mall.utils.SnowFlakeIdGenerator;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    @Resource
    private GoodsPlusMapper goodsPlusMapper;

    @Resource
    private OrderGoodRelMapper orderGoodRelMapper;

    @Resource
    private RelService relService;

    /**
     * 管理段获取 用户订单
     *
     * @param userId
     * @param limit
     * @param offset
     * @return
     */
    public UserOrderListVo selectUserOrders(Long userId, Integer limit, Integer offset) {
        Page<UserOrder> userOrderPage = new Page<>();
        userOrderPage.setCurrent(offset);
        userOrderPage.setSize(limit);

        IPage<UserOrder> userOrderIPage = null;

        if (userId == null) {
            userOrderIPage = orderMapper.selectAllUserOrders(userOrderPage);
        } else {
            userOrderIPage = orderMapper.selectAllUserOrdersByuserID(userOrderPage, userId);
        }

        UserOrderListVo listVo = new UserOrderListVo();
        listVo.setTotal(userOrderIPage.getTotal());
        listVo.setData(userOrderIPage.getRecords());

        return listVo;
    }

    public void updateOrderlogicse(Long orderId, String carrier, String express) {
        Order order = new Order();
        order.setId(orderId);
        order.setLogistics(express);
        order.setStatus(3);
        order.setCarrierName(carrier);
        orderMapper.updateById(order);
    }

    public void updateOrderStatus(Long orderId, Integer status) {
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(status);
        orderMapper.updateById(order);
    }

    /**
     * 预先创建订单，用户确认订单后才能进行下一步
     *
     * @param form
     * @return
     * @throws ApiOperationException
     */
    @Transactional
    public Long createOrder(OrderForm form) throws ApiOperationException {
        BigDecimal orderSum = new BigDecimal(0);
        Long orderId = snowFlakeIdGenerator.nextId();
        List<Long> goodsId = form.getOrder().stream().map(OrderForm.Entry::getGoodsId).collect(Collectors.toList());
        Map<Long, Integer> goodsNumMap = form.getOrder().stream().collect(
                Collectors.toMap(
                        OrderForm.Entry::getGoodsId,
                        OrderForm.Entry::getGoodsNum
                )
        );
        List<Goods> goods = goodsPlusMapper.selectBatchIds(goodsId);
        List<OrderGoodRel> rels = new ArrayList<>();
        // 检查订单库存是否可以支付
        for (Goods good : goods) {
            Integer num = goodsNumMap.get(good.getId());
            if (num > good.getInventory()) {
                throw new ApiOperationException(good.getName() + "库存不足", "库存不足" + good.getName());
            }
            orderSum = orderSum.add(good.getPrice().multiply(new BigDecimal(num)));
            OrderGoodRel rel = new OrderGoodRel();
            rel.setOrderId(orderId);
            rel.setGoodsNum(num);
            rel.setGoodsId(good.getId());
            rels.add(rel);
        }

        relService.saveBatch(rels);

        Order order = new Order();
        order.setId(orderId);
        order.setSum(orderSum);
        order.setUserId(form.getUserId());
        order.setStatus(0);
        order.setAddressId(form.getAddressId());

        orderMapper.insert(order);

        return orderId;
    }

    public OrderListVo getUserOrder(Integer limi, Integer offset) throws ApiOperationException {
        try {
            Long userId = RequestContext.get().getId();
            Page<OrderGoods> page = new Page<>();
            page.setSize(limi);
            page.setCurrent(offset);
            IPage<OrderGoods> orderGoodsIPage = orderMapper.selectAllOrderGoods(page, userId);

            OrderListVo listVo = new OrderListVo();
            listVo.setTotal(orderGoodsIPage.getTotal());
            listVo.setData(orderGoodsIPage.getRecords());
            return listVo;
        } catch (NullPointerException e) {
            throw new ApiOperationException("用户信息无效", "用户信息无效");
        }
    }

    public OrderGoods getUserOrderById(Long orderId) throws ApiOperationException {
        try {
            Long userId = RequestContext.get().getId();
            return orderMapper.selectOrderById(orderId, userId);
        } catch (NullPointerException e) {
            throw new ApiOperationException("用户信息无效", "用户信息无效");
        }
    }

    @Service
    public static class RelService extends ServiceImpl<OrderGoodRelMapper, OrderGoodRel> {

    }

}
