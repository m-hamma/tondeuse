package com.mhamma.tondeuse.main;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mhamma.tondeuse.service.TondeuseService;
import com.mhamma.tondeuse.service.impl.TondeuseServiceImpl;

public class TondeuseApplication {
	private static final Log log = LogFactory.getLog(TondeuseApplication.class);
	@Inject
	static TondeuseService tondeuseService=new TondeuseServiceImpl();
	
	public static void main(String[] args) {
		log.debug(tondeuseService.piloterTondeuse());
	}

}
