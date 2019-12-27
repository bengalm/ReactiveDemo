package com.example.demo.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.InetSocketAddress;

@Controller
public class indexController {

    @RequestMapping("/")
    public String index(final Model model, ServerHttpRequest serverHttpRequest) {
        InetSocketAddress remoteAddress = serverHttpRequest.getRemoteAddress();

      //  Address address = IPUtil.getIpAddress(request, response);
        model.addAttribute("address", remoteAddress);
        return "index";
    }
}
