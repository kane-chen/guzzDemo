package com.lasho.guzz ;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lasho.guzz.domain.custom.Cargo;
import com.lasho.guzz.service.CargoService;



public class CargoCustomTest extends TestCase{
	
	static ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	static CargoService cargoService = (CargoService)appContext.getBean("cargoCustomService", CargoService.class); 

	@Test
	public void testSaveBook(){
		 Cargo book = new Cargo() ;
         book.setName("book") ;
         book.setDescription("nice book ") ;
         book.setPrice(33.56) ;
         book.setStoreCount(10) ;
         book.setOnlineTime(new Date()) ;
         //ISBN, author and publisher
         book.getSpecialProps().put("ISBN", "isbn-bbb-1") ;
         book.getSpecialProps().put("author", "not me") ;
         book.getSpecialProps().put("publisher", "wolf") ;
		int saveResult = cargoService.save(book,"book");
		System.out.println(String.format("save result is %s", saveResult));
		Assert.assertTrue(saveResult > 0) ;
	}

	@Test
	public void testSaveBag(){
		Cargo bag = new Cargo() ;
		bag.setName("crossstitch1") ;
		bag.setDescription("nice bag ") ;
		bag.setPrice(233.56) ;
		bag.setStoreCount(10) ;
		bag.setOnlineTime(new Date()) ;
		//ISBN, author and publisher
		bag.getSpecialProps().put("gridNum", "10") ;
		bag.getSpecialProps().put("backColor", "black") ;
		bag.getSpecialProps().put("size", "xl") ;
		bag.getSpecialProps().put("brand", "big") ;
		int saveResult = cargoService.save(bag,"crossstitch");
		System.out.println(String.format("save result is %s", saveResult));
		Assert.assertTrue(saveResult > 0) ;
	}

	@Test
	public void testUpdate(){
		Cargo cargo = cargoService.findById(1,"book") ;
		cargo.setDescription("new description");
		boolean upResult = cargoService.update(cargo,"book");
		System.out.println(String.format("update result is %s", upResult));
	}
	
	@Test
	public void testFind(){
		Cargo cargo = cargoService.findById(1,"book") ;
		System.out.println(String.format("find result is %s", cargo.getName()));
		List<Cargo> all = cargoService.findAll("all") ;
		System.out.println(all.size());
	}
	
}
