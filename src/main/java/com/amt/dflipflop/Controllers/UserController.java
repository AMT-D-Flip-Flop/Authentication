package com.amt.dflipflop.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @GetMapping("/user/orders")
    public String getUserOrders(Model model) { return "orders"; }

    @GetMapping("/user/addresses")
    public String getAddressesPage(Model model) {
        return "addresses";
    }

    @GetMapping("/user/add-address")
    public String getAddAddressPage(Model model) {
        return "add-address";
    }

    @PostMapping(path="/user/add-address") // Map ONLY POST Requests
    public @ResponseBody
    String addNewAddress () {
        return "add-address";
    }

}