package com.example.bank.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "jplaceholder", url = "http://localhost:9090/api/notifications")
public interface FeignNotification {

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Void> createNotification(@RequestParam String message);
}