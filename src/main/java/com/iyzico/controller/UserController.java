package com.iyzico.controller;

import com.iyzico.model.User;
import com.iyzico.service.UserService;

import org.apache.catalina.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

/**
 * Date: 10/12/2016 Time: 13:00
 *
 * @author dogukan.ozturkan (https://github.com/dogukanozturkan)
 */

@Controller
@RequestMapping
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @RequestMapping("/") String index() {
        return "userLogin";
    }

    @RequestMapping(value = "/login")
    public String homePage() {
        return "userLogin";
    }

    @RequestMapping("/userRegister")
    public String userRegister(Model model) {
        model.addAttribute("user", new User());
        return "userRegister";
    }

    protected static final String ERROR_CODE_EMAIL_EXIST = "NotExist.user.email";
    protected static final String MODEL_NAME_REGISTRATION_DTO = "user";
    protected static final String VIEW_NAME_REGISTRATION_PAGE = "userRegister";

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("user") User userAccountData,BindingResult result,
                           WebRequest request) {

        LOGGER.info("Registering user account with information: {}", userAccountData);

        if (result.hasErrors()) {
            LOGGER.debug("Validation errors found. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
        }

        LOGGER.info("No validation errors found. Continuing registration process.");

        service.register(userAccountData);

        LOGGER.info(String.format("User %s has been signed in",userAccountData.getEmail()));

        return "redirect:/login";
    }
}
