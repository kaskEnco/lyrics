package com.lyrics.dao;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.FailureMode;
import net.spy.memcached.HashAlgorithm;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.OperationTimeoutException;

public class MemcachedConncetionFactory {

	static MemcachedClient cache;
	private static MemcachedConncetionFactory memcacheConnection = null;

	static {
		try {
			//cache = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			List<String> addresses = new ArrayList<String>();
			addresses.add("127.0.0.1:11211");
			addresses.add("127.0.0.1:11212");
			cache = new MemcachedClient(new ConnectionFactoryBuilder().setShouldOptimize(false).setTimeoutExceptionThreshold(100).setProtocol(ConnectionFactoryBuilder.Protocol.BINARY).setDaemon(true).setFailureMode(FailureMode.Redistribute).build(), AddrUtil.getAddresses(addresses));
			 			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static MemcachedConncetionFactory getInstance() throws OperationTimeoutException {
		if (memcacheConnection == null) {
			synchronized (MemcachedConncetionFactory.class) {
				if (memcacheConnection == null) {
					memcacheConnection = new MemcachedConncetionFactory();
					;
				}
			}
		}

		return memcacheConnection;
	}

	public MemcachedClient getConnection() throws SQLException {
		return cache;
	}

}
