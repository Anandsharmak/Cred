package com.example.cred.controller;

import com.example.cred.model.OrderRequest;
import com.example.cred.service.CategoryServiceClass;
import com.example.cred.service.OrderServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class OrderController {
    @Autowired
    CategoryServiceClass catService;
    @Autowired
    OrderServiceClass orderService;

    @GetMapping("/")
    public String online(){
        return "online";
    }

    //API canFulfil
    @PostMapping("/order/canfulfil")
    public boolean canFulfil(@RequestBody OrderRequest orderRequest){
        try {
            catService.isReservePossible(orderRequest);
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        try {
            orderService.isReservePossible(orderRequest);
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    //API Reserve An Order
    @PostMapping("/order/reserve")
    public boolean reserveOrder(@RequestBody OrderRequest orderRequest){
        if (canFulfil(orderRequest)){
            return true;
        }
            return false;
    }

    //insert
    @PostMapping("/order/insert")
    public  HashMap<String,HashMap<Integer, HashMap<String,Integer>>> insertOrder(@RequestBody OrderRequest orderRequest){
        catService.insert(orderRequest);
        return orderService.insert(orderRequest);
    }

}
