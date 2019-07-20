package xyz.chunshengyuan.mall.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.*;
import xyz.chunshengyuan.mall.configuration.RequiredRole;
import xyz.chunshengyuan.mall.constrant.UserRole;
import xyz.chunshengyuan.mall.exceptions.ApiOperationException;
import xyz.chunshengyuan.mall.mapper.AddressPlusMapper;
import xyz.chunshengyuan.mall.mapper.GoodsMapper;
import xyz.chunshengyuan.mall.mapper.OrderGoodRelMapper;
import xyz.chunshengyuan.mall.mapper.OrderMapper;
import xyz.chunshengyuan.mall.model.BaseResponse;
import xyz.chunshengyuan.mall.model.bo.OrderGoods;
import xyz.chunshengyuan.mall.model.po.Address;
import xyz.chunshengyuan.mall.model.po.Goods;
import xyz.chunshengyuan.mall.model.po.Order;
import xyz.chunshengyuan.mall.model.po.OrderGoodRel;
import xyz.chunshengyuan.mall.model.v1.V1OrderInfoVo;
import xyz.chunshengyuan.mall.model.v1.V1OrdersVo;
import xyz.chunshengyuan.mall.model.vo.OrderForm;
import xyz.chunshengyuan.mall.service.OrderService;
import xyz.chunshengyuan.mall.utils.RequestContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: V1OrderController
 * @date: 2019-07-10 22:09
 * @author: yuxinyang
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/v1/order")
public class V1OrderController {

    @Resource
    OrderService orderService;

    @Resource
    OrderMapper orderMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    AddressPlusMapper addressPlusMapper;

    @Resource
    OrderGoodRelMapper orderGoodRelMapper;

    @PostMapping
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.CONSUMER})
    public BaseResponse createOrder(@RequestBody OrderForm orderForm) throws ApiOperationException {
        orderForm.setUserId(RequestContext.get().getId());
        Long order = orderService.createOrder(orderForm);
        return BaseResponse.succes(order.toString());
    }

    @PutMapping
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.CONSUMER})
    public BaseResponse updateOrder(@RequestParam("order_id") Long orderId, @RequestParam("order_status") String orderStatus) {
        int state = 0;
        if ("CANCEL".equals(orderStatus)) {
            state = 5;
        } else if ("CONFIRM".equals(orderStatus)) {
            state = 2;
        } else if ("COMPLETED".equals(orderStatus)) {
            state = 4;
        }
        orderService.updateOrderStatus(orderId, state);
        return BaseResponse.success();
    }

    @GetMapping
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.CONSUMER})
    public BaseResponse getOrders(@RequestParam("order_state") Integer state) throws ApiOperationException {
        List<Order> collect = orderMapper.selectList(Wrappers.<Order>query().eq("user_id", RequestContext.get().getId()).orderByDesc("update_time"));
        if (state != -1) {
            collect = collect.stream().filter(i -> i.getStatus().equals(state)).collect(Collectors.toList());
        }
        List<V1OrdersVo> list = new ArrayList<>();
        collect.forEach(o -> {
            List<OrderGoodRel> rel = orderGoodRelMapper.selectList(Wrappers.<OrderGoodRel>query().eq("order_id", o.getId()));
            List<OrderGoods> goods = new ArrayList<>();
            rel.forEach(r -> {
                Goods g = goodsMapper.selectById(r.getGoodsId());
                OrderGoods orderGoods = new OrderGoods(g.getId(), g.getName(), g.getPrice(), g.getIntroduce(), g.getAvatar(), g.getAddTime(), g.getUpdateTime(), r.getGoodsNum());
                goods.add(orderGoods);
            });
            list.add(new V1OrdersVo(o, goods));
        });
        return BaseResponse.succes(list);
    }

    @GetMapping("info/{orderId}")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.CONSUMER})
    public BaseResponse getOrderInfo(@PathVariable long orderId) throws ApiOperationException {
        List<OrderGoods> userOrderById = orderService.getUserOrderById(orderId);
        Order order = orderMapper.selectById(orderId);
        Address address = addressPlusMapper.selectById(order.getAddressId());
        return BaseResponse.succes(new V1OrderInfoVo(userOrderById, order, address));
    }

}



