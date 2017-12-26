# Exception
1. 对  Exception 的结构进行了定义。  
2. 对 Exception 的定义和存放提供了统一的模板。
3. 对 Exception 的组装提供了默认的实现。
4. 定义了公司内部统一的公共的异常码。 （[CommonErrors.java](dz-common-util/src/main/java/com/dazong/common/exceptions/CommonErrors.java)）

## 异常结构
异常结构图如下：  
![异常规范](dz-common-util/src/main/java/com/dazong/common/exceptions/异常规范.png)

## Quick Start
例：  
**异常定义规范统一** [CommonErrors.java](dz-common-util/src/main/java/com/dazong/common/exceptions/CommonErrors.java)（新系统可以使用这个规范，老系统可以继续保持，后面可以考虑向这个方向靠拢） 
>
	public enum CommonErrors implements IErrors<DataResponse<?>> {
		SUCCESS(CommonRespStatus.SUCCESS.code, "成功"),
		ILLEGAL_PARAM(101, "参数[{0}]错误"),
		SYSTEM_ERROR(199, "系统异常");
		private int code;
		private String message;
		private CommonErrors(int code, String message) {
			this.code = code;
			this.message = message;
		}
		.........
	}

[UniversalChainErrors.java](dz-universal-chain/src/main/java/com/dazong/universal/exception/UniversalChainErrors.java)  
>
	public enum UniversalChainErrors implements IErrors<DataResponse<?>> {
		EMPTY_FILTER(1001, "filters is empty"),
		OTHER_THREAD_PROCESSING(1002, "幂等异常，其他线程正在操作"),
		RETURNCLASS_NOT_FOUND(1003, "幂等记录中的returnclass[{0}]不存在"),
		LOCK_ERROR(1004, "lock error");
		private int code;
		private String message;
		private UniversalChainErrors(int code, String message) {
			this.code = code;
			this.message = message;
		}
		......
	}

**异常抛出时统一用法：**  
throw CommonErrors.ILLEGAL_PARAM.exp("arg0");  
参考实例：[IdempotentFilter.java](dz-universal-chain/src/main/java/com/dazong/universal/filter/idempotent/IdempotentFilter.java)  
