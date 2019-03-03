package com.community.util;

/**
 * Runner 用于实现在同一个事务中执行多个 DML 操作。<br>
 */
public interface Runner {
	
	/**
	 * 对于需要通过 JdbcHelper 执行的多个 DML 操作，可以在 Runner 实现类的 doInTransaction 完成<br>
	 * 最后，在 JdbcHelper 实例的 execute 方法内部 调用 Runner 实例的 doInTransaction
	 * @throws RuntimeException
	 */
	void doInTransaction() throws RuntimeException ;

}
