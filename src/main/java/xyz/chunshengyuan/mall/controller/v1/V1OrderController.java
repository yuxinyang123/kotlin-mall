package xyz.chunshengyuan.mall.controller.v1;

import org.springframework.web.bind.annotation.*;
import xyz.chunshengyuan.mall.configuration.RequiredRole;
import xyz.chunshengyuan.mall.constrant.UserRole;
import xyz.chunshengyuan.mall.exceptions.ApiOperationException;
import xyz.chunshengyuan.mall.model.BaseResponse;
import xyz.chunshengyuan.mall.model.bo.OrderGoods;
import xyz.chunshengyuan.mall.model.vo.OrderForm;
import xyz.chunshengyuan.mall.model.vo.OrderListVo;
import xyz.chunshengyuan.mall.service.OrderService;
import xyz.chunshengyuan.mall.utils.RequestContext;

import javax.annotation.Resource;

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

    @PostMapping
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.CONSUMER})
    public BaseResponse createOrder(@RequestBody OrderForm orderForm) throws ApiOperationException {
        orderForm.setUserId(RequestContext.get().getId());
        Long order = orderService.createOrder(orderForm);
        return BaseResponse.succes(order);
    }

    @PutMapping
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.CONSUMER})
    public BaseResponse updateOrder(@RequestParam("order_id") Long orderId, @RequestParam("order_status") String orderStatus) {
        int state = 0;
        if ("CANCEL".equals(orderStatus)) {
            state = 5;
        } else if ("CONFIRM".equals(orderStatus)) {
            state = 1;
        } else if ("COMPLETED".equals(orderStatus)) {
            state = 4;
        }
        orderService.updateOrderStatus(orderId, state);
        return BaseResponse.success();
    }

    @GetMapping
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.CONSUMER})
    public BaseResponse getOrders(@RequestParam("order_state") Integer state) throws ApiOperationException {
        OrderListVo userOrder = orderService.getUserOrder(1, 10000);
        return BaseResponse.succes(userOrder);
    }

    @GetMapping("info/{orderId}")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.CONSUMER})
    public BaseResponse getOrderInfo(@PathVariable long orderId) throws ApiOperationException {
        OrderGoods userOrderById = orderService.getUserOrderById(orderId);
        return BaseResponse.succes(userOrderById);
    }

}



