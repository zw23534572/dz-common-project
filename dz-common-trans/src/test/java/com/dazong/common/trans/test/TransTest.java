package com.dazong.common.trans.test;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dazong.common.trans.jdbc.mapper.DzTransactionObjectMapper;
import com.dazong.common.trans.support.DzTransactionObject;
import com.dazong.common.trans.test.service.ITransService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransApplicationStart.class)
public class TransTest {
	
	@Autowired
	private ITransService transService;
	@Autowired
	private DzTransactionObjectMapper mapper;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * <B>方法名称：测试正常事务（无嵌套事务）</B><BR>
	 * <B>概要说明：</B><BR>
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoTrans(){
		this.transService.doTrans("doTrans");
		DzTransactionObject o = this.mapper.selectByPrimaryKey(
				TransContext.getCurrentContext().get("doTrans-uid").toString());
		System.out.println(o);
		Assert.assertTrue(o == null);
	}
	
	@Test
	public void testDoTransException() throws InterruptedException {
		try {
			this.transService.doTransException("doTransException");
			Assert.assertTrue(false);
		} catch (Exception e) {
			DzTransactionObject o = this.mapper.selectByPrimaryKey(
					TransContext.getCurrentContext().get("doTransException-uid").toString());
			System.out.println(o);
			Assert.assertTrue(o != null);
		}
	}
	
	@Test
	public void testDoTransExceptionButRollback() {
		try {
			this.transService.doTransExceptionButRollback("doTransExceptionButRollback");
			Assert.assertTrue(false);
		} catch (Exception e) {
			DzTransactionObject o = this.mapper.selectByPrimaryKey(
					TransContext.getCurrentContext().get("doTransExceptionButRollback-uid").toString());
			System.out.println(o);
			Assert.assertTrue(o == null);
		}
	}
}
