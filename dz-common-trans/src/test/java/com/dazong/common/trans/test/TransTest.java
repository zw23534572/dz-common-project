package com.dazong.common.trans.test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

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
import com.dazong.common.trans.test.utils.CacheUtil;


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
	/**
	 * <B>方法名称：测试嵌套事务</B><BR>
	 * <B>概要说明：测试属性MANDATORY</B><BR>
	 */
	@Test
	public void testDoTransPropagationForMandatory1(){
		try {
			this.transService.doTransPropagationForMandatory1("doTransPropagationForMandatory1");
			Assert.assertTrue(false);
		} catch (Exception e) {
			String rid = TransContext.getCurrentContext().get("doTransPropagationForMandatory1-rid").toString();
			List<DzTransactionObject> os = this.mapper.queryTransactionsByRid(rid);
			Assert.assertTrue(os != null && os.size() == 1);
		}
	}
	/**
	 * <B>方法名称：测试嵌套事务</B><BR>
	 * <B>概要说明：测试属性MANDATORY</B><BR>
	 */
	@Test
	public void testDoTransPropagationForMandatory2(){
		try {
			this.transService.doTransPropagationForMandatory2("doTransPropagationForMandatory2");
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof DzTransactionException);
		}
	}
	/**
	 * <B>方法名称：测试嵌套事务</B><BR>
	 * <B>概要说明：测试属性INTERRUPT_NOT_NEW</B><BR>
	 */
	@Test
	public void testDoTransPropagationForInterruptNotNew(){
		try {
			this.transService.doTransPropagationForInterruptNotNew("doTransPropagationForInterruptNotNew");
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof DzTransactionException);
		}
	}
	
	/**
	 * <B>方法名称：测试嵌套事务</B><BR>
	 * <B>概要说明：测试属性NESTED</B><BR>
	 */
	@Test
	public void testDoTransPropagationForNested(){
		try {
			this.transService.doTransPropagationForNested("doTransPropagationForNested");
			Assert.assertTrue(false);
		} catch (Exception e) {
			String rid = TransContext.getCurrentContext().get("doTransPropagationForNested-rid").toString();
			List<DzTransactionObject> os = this.mapper.queryTransactionsByRid(rid);
			Assert.assertTrue(os != null && os.size() == 2);
		}
	}
	@Test
	public void testDoTransAsync(){
		CountDownLatch cd = new CountDownLatch(1);
		long threadId = Thread.currentThread().getId();
		this.transService.doTransAsync(cd);
		try {
			cd.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Long doTransAsyncThreadId = (Long)CacheUtil.get("doTransAsync-thread-id");
		Assert.assertTrue(threadId != doTransAsyncThreadId);
	}
	
	@Test
	public void testDoTransAsyncException(){
		CountDownLatch cd = new CountDownLatch(1);
		this.transService.doTransAsyncException(cd);
		try {
			cd.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DzTransactionObject o = this.mapper.selectByPrimaryKey(CacheUtil.get("test4-uid").toString());
		Assert.assertTrue(o != null);
	}
	
	@Test
	public void testDoTransBussinessId(){
		try {
			this.transService.doTransBussinessId(1000L);
		} catch (Exception e) {
			e.printStackTrace();
			DzTransactionObject o = this.mapper.selectByPrimaryKey(
					TransContext.getCurrentContext().get("doTransBussinessId-uid").toString());
			Assert.assertTrue(o.getBussinessId().equals(String.valueOf(1000L)));
		}
	}
}
