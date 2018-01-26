package com.dazong.common.trans.test;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dazong.common.trans.DzTransactionException;
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
		Assert.assertTrue(o == null);
	}
	/**
	 * <B>方法名称：测试异常事务，无嵌套</B><BR>
	 * <B>概要说明：</B><BR>
	 * @throws InterruptedException
	 */
	@Test
	public void testDoTransException() throws InterruptedException {
		try {
			this.transService.doTransException("doTransException");
			Assert.assertTrue(false);
		} catch (Exception e) {
			DzTransactionObject o = this.mapper.selectByPrimaryKey(
					TransContext.getCurrentContext().get("doTransException-uid").toString());
			Assert.assertTrue(o != null);
		}
	}
	/**
	 * <B>方法名称：测试指定异常事务回滚，无嵌套</B><BR>
	 * <B>概要说明：</B><BR>
	 */
	@Test
	public void testDoTransExceptionButRollback() {
		try {
			this.transService.doTransExceptionButRollback("doTransExceptionButRollback");
			Assert.assertTrue(false);
		} catch (Exception e) {
			DzTransactionObject o = this.mapper.selectByPrimaryKey(
					TransContext.getCurrentContext().get("doTransExceptionButRollback-uid").toString());
			Assert.assertTrue(o == null);
		}
	}
	@Test
	public void testDoTransPropagationForMandatory1(){
		try {
			this.transService.doTransPropagationForMandatory1("doTransPropagationForMandatory1");
			Assert.assertTrue(false);
		} catch (Exception e) {
			e.printStackTrace();
			String rid = TransContext.getCurrentContext().get("doTransPropagationForMandatory1-rid").toString();
			List<DzTransactionObject> os = this.mapper.queryTransactionsByRid(rid);
			Assert.assertTrue(os != null && os.size() == 1);
		}
	}
	@Test
	public void testDoTransPropagationForMandatory2(){
		try {
			this.transService.doTransPropagationForMandatory2("doTransPropagationForMandatory2");
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof DzTransactionException);
		}
	}
}
