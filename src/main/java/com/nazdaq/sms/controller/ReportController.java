package com.nazdaq.sms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

import com.nazdaq.sms.service.CommonService;
import com.nazdaq.sms.util.Constants;


@Controller
@PropertySource("classpath:common.properties")
public class ReportController implements Constants {

	@Autowired
	private CommonService commonService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${cc.email.addresss}")
	String ccEmailAddresss;


}
