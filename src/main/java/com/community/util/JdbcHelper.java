package com.community.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JdbcHelper {
	
	// 指定 在 配置文件中 各个 key 的 前缀 和 后缀
	private final String prefix = "jdbc.connection." ;
	private final String driverSuffix = ".driver" ;
	private final String urlSuffix = ".url" ;
	private final String usernameSuffix = ".username" ;
	private final String passwordSuffix = ".password" ;
	
	// 声明一批实例变量，用来保存连接数据库的基本信息
	private String driverClassName;
	private String URL ;
	private String username ;
	private String password ;
	
	private String configuration ;
	
	private static JdbcHelper jdbcHelper ;
	
	private JdbcHelper( String configuration ) {
		this.configuration = configuration ;
		this.init(); // 调用本类的 init 方法完成对 driverClassName 、URL 、username 、password 的初始化操作
	}
	
	/** 使用 默认位置( 类路径的根目录 )  默认名称  ( jdbc-config.properties ) 的配置文件 返回一个 JdbcHelper 实例*/
	public synchronized static JdbcHelper getInstance() {
		if( jdbcHelper == null ) {
			// 这个文件名是我们约定的文件名称，在Eclipse中开发时，它应该放在 src 目录下 ( 将来会 复制到 类路径 的根目录下 )
			jdbcHelper = new JdbcHelper( "jdbc-config.properties" ); 
		}
		return jdbcHelper ;
	}
	
	/** 根据指定的 配置文件 创建并返回一个 JdbcHelper 实例 <br>
	 *  @param  configuration 参数 configuration 用来指定配置文件，它应该是放在类路径根目录下 或 类路径下的某个目录中 
	 */
	public synchronized static JdbcHelper getInstance( String configuration ) {
		if( jdbcHelper == null ) {
			jdbcHelper = new JdbcHelper( configuration );
		}
		return jdbcHelper ;
	}
	
	/** 通过调用 本类的 read  和 load(InputStream) 方法完成对 driverClassName 、URL 、username 、password 的初始化操作*/
	private void init() {
		
		if( configuration == null || configuration.trim().isEmpty() ) {
			throw new RuntimeException( "配置文件不能为空" );
		}
		
		InputStream in = this.read(); // 调用本类中的方法返回配置文件对应的输入流
		
		this.load( in ); // 从输入流中读取内容到 当前对象的 各个字段中 ( driverClassName 、URL 、username 、password )
		
	}
	
	/** 创建一个 configuration 字段中存储的文件名称 对应 字节输入流 */
	private InputStream read() {
		// 获得 当前类 对应的 Class 对象
		Class<JdbcHelper> c = JdbcHelper.class;
		// 获得 加载了 当前类的 那个类加载器
		ClassLoader loader = c.getClassLoader(); // 获得当前类的 "类加载器"
		// 创建 configuration 对应的文件输入流
		InputStream in =loader.getResourceAsStream( configuration );
		return in ;
	}
	
	/** 从配置文件对应的字节输入流中 读取相应的 属性值并设置到 当前对象的 driverClassName 、URL 、username 、password */
	private void load( InputStream in ) {
		// 创建一个 Properties 对象 ( 它是一个 Map 集合)
		Properties props = new Properties(); // Properties 是 Hashtable 类的子类
		
		try {
			props.load( in ); // 使用 Properties 类的 load 方法可以读取 属性文件 ( .properties 文件 ) 中的 键-值对
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 用于确定连接那个数据库的变量，比如 mysql 或 oracle
		String connect = props.getProperty( "connect" ) ; // 读取 配置文件中 名称是 connect 的那个属性的值 ( 根据 key 获取 value )
		
		// 动态地拼接 被连接数据库 在  配置文件中 的 属性名称 ( 就是 Map 的 key )
		String driverKey = prefix + connect + driverSuffix ;
		String urlKey = prefix + connect + urlSuffix ;
		String usernameKey = prefix + connect + usernameSuffix ;
		String passwordKey = prefix + connect + passwordSuffix ;
		
		// 根据 属性名 ( key ) 获取 属性值 ( value )
		this.driverClassName = props.getProperty( driverKey );
		this.URL =  props.getProperty( urlKey );
		this.username = props.getProperty( usernameKey );
		this.password = props.getProperty( passwordKey );
		
		// 关闭输入流
		this.release( in );
	}
	
	/** 用于记录驱动是否已经被加载的字段 ( true 表示未加载 ， false 表示已经加载 ) */
	private boolean unloaded = true ; // 
	
	/** 加载驱动类 */
	private void load() {
		if( unloaded ) {
			try {
				// Class.forName( driverClassName );
				ClassLoader loader = JdbcHelper.class.getClassLoader(); // 获得 JdbcHelper 类的 "类加载器"
				// Class.forName( String name, boolean initialize, ClassLoader loader)
				Class.forName( driverClassName ,  true , loader ) ; // 使用指定的类加载器加载 驱动类，并在加载后完成对该类的初始化操作
				unloaded = false ; // 加载一次驱动后，将 unloaded 更改为 false 表示以后不需要再加载驱动
			} catch (ClassNotFoundException e) {
				System.err.println( "不能加载[ " + driverClassName + " ] 类: " + e.getMessage() );
				e.printStackTrace();
			}
		}
	}
	
	/** 用来 "缓存" 单个连接对象的字段*/
	private Connection connection ;
	
	/** 获得数据库连接 ，并将该连接 "缓存" 到 当前实例 的 connection 字段 */
	private void connect() {
		if( this.isInvalid() ) { // 如果连接是无效的
			try {
				// 先尝试释放那个无效的连接
				this.release( connection );
				// 则创建新的连接
				connection = DriverManager.getConnection( URL , username , password );
			} catch (SQLException e) {
				System.err.println( "建立数据库连接时发生错误: " + e.getMessage() );
				e.printStackTrace();
			}
		}
	}
	
	/** 判断连接是否是无效的 */
	private boolean isInvalid() {
		if( connection == null ) {
			return true ; // 如果 connection 是 空，则说明这个连接是无效的，不能用的
		}
		
		try {
			if( !connection.isValid( 10 ) ) {
				return true ; // 如果 connection 是无效的，则返回 true
			}
		} catch (SQLException e) {
			return true ; 
		}
		
		return false ; // 排除了以上所有的无效的情况之后，我们认为连接是有效的
	}
	
	/**
	 *  根据给定的SQL语句创建一个 PreparedStatement
	 * @param SQL 给定的SQL可以带有参数占位符，也可以不带有参数占位符
	 * @param generate 用于确定是否需要获取由数据库产生键 ( true 表示需要获取，false 表示不需要获取 )
	 * @return
	 */
	private PreparedStatement prepare( String SQL , boolean generate ) {
		this.load(); // 加载驱动
		this.connect(); // 获得数据库连接并 "缓存" 到 connection 变量中
		
		int autoGeneratedKeys = generate ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS ;
		
		try {
			// 1、将 SQL语句 发送给数据库服务器
			// 2、数据库服务器 接收到SQL后，分析SQL语法、编译接收到的SQL [ 凡是通过 connection.prepareStatement发送的SQL 数据库都应该预先编译]
			// 3、数据库服务器 如果能正常编译接收到的SQL，则会将 JDBC 程序反馈一个结果，这个结果中包含了调用该SQL的一个"标识(zhi)"
			// 4、JDBC程序根据数据库反馈的结果创建一个 PreparedStatement 对象 ( 这个对象中含有执行该SQL语句的 "标识")
			return connection.prepareStatement( SQL ,  autoGeneratedKeys );
		} catch (SQLException cause) {
			throw new RuntimeException( "创建PreparedStatement对象时发生错误" , cause);
		}
	}
	
	/**
	 * 为指定的 PreparedStatement 对象设置参数占位符的值
	 * @param ps 需要设置参数占位符的值 的 PreparedStatement 对象
	 * @param params 根据 PreparedStatement 对应的 SQL 参数占位符的顺序 依次传入 各个位置的值
	 */
	private void setParameters( PreparedStatement ps , Object... params ) {
		if( params != null && params.length > 0  ) {
			try {
				// 可变长参数在方法内部当作数组对待
				// 遍历数组，并将相应的值 设置掉 PreparedStatement 对象中
				for( int i = 0 ; i < params.length ; i++ ) {
					ps.setObject( i + 1 , params[ i ] );
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 执行任意给定的查询语句，如果该查询语句中含有参数占位符，则在SQL语句之后依次传入这些参数占位符的值
	 * @param SQL 需要被执行的查询语句( 它可能含有参数占位符，也可能没有参数占位符 )
	 * @param params 如果被执行的查询语句中含有 参数占位符 则依次序传入这些参数占位符的值
	 * @return
	 */
	public List< Map< String,Object > > query( String SQL , Object... params ) {
		// 我只执行查询语句
		
		PreparedStatement ps = this.prepare(SQL , false ); // 根据给定的SQL语句创建 PreparedStatement 对象
		
		this.setParameters( ps , params ); // 依次设置 参数占位符的值
		
		ResultSet rs = null ;
		
		try {
			rs = ps.executeQuery();
		} catch (SQLException cause) {
			throw new RuntimeException( "查询时发生错误" , cause) ;
		}
		
		// 将 结果集中的每一行 数据 转换成一个 Map 集合，然后将每一行对应 Map 添加到一个 List 集合中
		List< Map<String,Object> >  list = this.transform( rs );
		
		this.release( rs );
		this.release( ps );
		
		return list  ;
	}
	
	/**
	 * 将指定查询结果对应的结果集对象 "转换" 为 一个 List 对象，这个 List 对象中存放的是 Map 集合 。<br>
	 * 结果集中的每一行数据对应着一个 Map 集合。
	 * @param rs 需要处理的结果集对象
	 * @return
	 */
	private List< Map<String,Object> > transform( ResultSet rs ){
		if( rs == null ) {
			throw new RuntimeException( "结果集不能为空" );
		}
		
		List< Map<String,Object> >  list = new ArrayList<>();
		
		try {
			ResultSetMetaData rsmd = rs.getMetaData(); // 获得结果集元数据
			final int columnCount = rsmd.getColumnCount(); // 获得查询结果中的列数
			
			while( rs.next() ) {
				Map<String,Object> map = new HashMap<>(); // 创建一个 Map 集合用于存放 当前行的数据
				// 依次处理当前行的每个列( 以列名 或 别名 为 key ，以相应列的取值 为 value )
				for( int i = 1 ; i <= columnCount ; i++ ) {
					String label = rsmd.getColumnLabel( i ) ; // 获取 第 i 列 的名字 ( 作为 Map 集合的 key )
					Object value = rs.getObject( i ) ; // 获取 当前行 第 i 列 的值  ( 作为 Map 集合的 value )
					label = label.toLowerCase(); // 将所有的 列名  或 别名 更改为小写形式
					map.put( label , value ); // 将 "label-value" 对 添加到 Map 集合
				}
				list.add( map ); // 将当前行对应的Map集合添加到 List 集合中
			}
		} catch (SQLException e) {
			System.err.println( "处理结果集时发生错误: " + e.getMessage() );
			e.printStackTrace();
		}
		
		return list ;
	}
	
	/**
	 * 执行任意给定的 DML 语句，如果该DML语句中含有参数占位符，则在SQL语句之后依次传入这些参数占位符的值
	 * @param SQL 需要被执行的DML语句( 它可能含有参数占位符，也可能没有参数占位符 )
	 * @param params 如果被执行的DML语句中含有 参数占位符 则依次序传入这些参数占位符的值
	 * @return 返回受当前DML语句影响的记录数(数据库中多少行记录受到影响，比如删除多少行、更新多少行、添加多少行)
	 */
	public int update(  String SQL , Object... params ) {
		// 仅仅执行 DML 语句( INSERT、UPDATE、DELETE )
		
		PreparedStatement ps = this.prepare(SQL,  false );
		
		this.setParameters( ps , params );
		
		int count = 0 ;
		
		try {
			count = ps.executeUpdate();
		} catch (SQLException cause ) {
			throw new RuntimeException( "执行 DML 操作时发生错误" , cause);
		}
		
		this.release( ps );
		
		return count ;
	}
	
	/**
	 * 执行需要返回由数据库产生的键对应的 INSERT 语句，如果该INSERT语句中含有参数占位符，则在SQL语句之后依次传入这些参数占位符的值
	 * 如果期望能够返回由数据库产生键，则应该使用 这个方法执行 insert 操作。<br>
	 * 注意，该方法仅支持插入一条数据并返回由数据库产生的单个键<br>
	 * @param SQL 需要被执行的 INSERT 语句( 它可能含有参数占位符，也可能没有参数占位符 ) 
	 * @param params 如果被执行的INSERT语句中含有 参数占位符 则依次序传入这些参数占位符的值
	 * @return
	 */
	public int insert( String SQL , Object... params ) {
		
		// 我只执行 INSERT 语句
		
		PreparedStatement ps = this.prepare(SQL , true );
		
		this.setParameters( ps , params );
		
		int count = 0 ;
		try {
			count = ps.executeUpdate();
		} catch (SQLException cause ) {
			throw new RuntimeException( "插入数据失败" , cause) ;
		}
		
		int id = 0 ;
		
		if( count == 1 ) { // 因为这个方法仅仅执行插入一条数据的INSERT语句，所以 count 为 1 表示成功
			// 如果成功，就获取由数据库产生的键
			try {
				ResultSet rs = ps.getGeneratedKeys();
				if( rs.next()) {
					id = rs.getInt( 1 ) ; // 这个结果集中就一个 名称是 "GENERATED_KEYS" 的列
				}
				this.release( rs );
			} catch (SQLException cause) {
				throw new RuntimeException( "获取由数据库产生键时发生错误" , cause) ;
			}
		}
		
		this.release( ps );
		
		return id ;
	}
	
	/**
	 * 这是一个专门用来释放资源的方法。<br>
	 * 使用该方法可以关闭 Connection 、Statement、ResultSet 等资源。
	 * @param ac
	 */
	private void release( AutoCloseable ac ) {
		if( ac != null ) {
			try {
				ac.close();
			} catch (Exception e) {
				System.out.println( "【关闭资源时发生错误: " + e.getMessage() +"】" );
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 这个方法用于释放连接
	 */
	public void destory() {
		this.release( this.connection ); // 释放连接(关闭连接)
		JdbcHelper.jdbcHelper = null ; // 让 静态变量 jdbcHelper 为 null ( 有机会可以重写获得 JdbcHelper 实例 )
		System.gc(); // "建议" 垃圾回收线程 回收 jdbcHelper 等对象所占用的内存
	}
	
	/** 关闭当前连接对象上的事务的自动提交 */
	private void  disableAutocommit() {
		try {
			connection.setAutoCommit( false );
		} catch (SQLException e) {
			System.err.println( "禁用事务自动提交时发生错误: "  + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	/** 启用当前连接对象上的事务的自动提交 */
	private void  enableAutocommit() {
		try {
			connection.setAutoCommit( true );
		} catch (SQLException e) {
			System.err.println( "启用事务自动提交时发生错误: "  + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	/** "开启"一个新的事务 */
	private void begin() {
		try {
			connection.commit(); // 提交上一个事务，开启一个新的事务
		} catch (SQLException e) {
			System.out.println( "终结上一次事务就是开启一个新的事务，但是出错了: " + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	/** 提交事务 */
	private void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			System.out.println( "提交事务时出错了: " + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	/** 回滚事务 */
	private void rollback() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println( "回滚事务时出错了: " + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	/**
	 * 在同一个事务中执行 runner 的 doInTransaction 方法中包含的操作
	 * @param runner 如果右多个操作需要在同一个事务中完成，则应该创建一个新的 Runner 实例，并在其 doInTransaction 方法中完成这些操作。
	 */
	public void execute( Runner runner ) {
		this.load();
		this.connect();
		this.disableAutocommit(); // 禁用自动提交
		this.begin(); // 开启事务
		try {
		       runner.doInTransaction(); // 在同一个事务中执行多个操作
		       this.commit() ; // 提交事务
		 } catch ( RuntimeException e ) {
		       this.rollback(); // 回滚事务
		 }
		 this.enableAutocommit(); // 启用自动提交
	}
	
	public void setReadUncommitted() {
		try {
			connection.setTransactionIsolation( Connection.TRANSACTION_READ_UNCOMMITTED );
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setReadCommitted() {
		try {
			connection.setTransactionIsolation( Connection.TRANSACTION_READ_COMMITTED );
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setRepeatableRead() {
		try {
			connection.setTransactionIsolation( Connection.TRANSACTION_REPEATABLE_READ );
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setSerializable() {
		try {
			connection.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
