package com.nazdaq.sms.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;


public class JrxmToJasper {
	public static void main(String[] args) throws JRException {
		// TODO Auto-generated method stub
		
        JasperCompileManager.compileReportToFile(
        		"E:\\git-workspace\\sts_390\\synergy-jams\\src\\main\\resources\\report\\jobAdvReportPending.jrxml", 
        		"E:\\git-workspace\\sts_390\\synergy-jams\\src\\main\\resources\\report\\jobAdvReportPending.jasper");
     }
	
}
