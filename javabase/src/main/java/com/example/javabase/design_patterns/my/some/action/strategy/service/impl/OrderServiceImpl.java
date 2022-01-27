package com.example.javabase.design_patterns.my.some.action.strategy.service.impl;


import com.example.javabase.design_patterns.my.some.action.strategy.model.OrderDTO;
import com.example.javabase.design_patterns.my.some.action.strategy.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * @Author: soulx
 * @Description:
 * @Date: Created in 9:54 2019/2/2
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Override
    public String handle(OrderDTO dto) {
        String type = dto.getType();
        if ("1".equals(type)) {
            return "处理普通订单";
        } else if ("2".equals(type)) {
            return "处理团购订单";
        } else if ("3".equals(type)) {
            return "处理促销订单";
        }
        return null;
    }

}
