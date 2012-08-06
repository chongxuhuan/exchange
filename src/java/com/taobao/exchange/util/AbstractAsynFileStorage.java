/**
 * 
 */
package com.taobao.exchange.util;

import com.taobao.top.xbox.asynlog.AsynWriter;
import com.taobao.top.xbox.asynlog.FileWriterFactory;
import com.taobao.top.xbox.asynlog.IWriterScheduleFactory;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午11:36:26
 *
 */
public abstract class AbstractAsynFileStorage<T> implements IAsynFileStorage<T>{

	AsynWriter<T> asynWriter;
	String className;
	
	public AbstractAsynFileStorage(String className,boolean needStartAsynMode)
	{
		if (needStartAsynMode)
		{
			this.className = className;
			asynWriter = new AsynWriter<T>();
			
			IWriterScheduleFactory<T> writerFactory = new FileWriterFactory<T>(className);
			writerFactory.init();
			asynWriter.setWriterFactory(writerFactory);
			asynWriter.init();
		}
	}
	
	@Override
	public void setStoreFileName(String name) {
		this.className = name;
	}

	@Override
	public void asynStore(T obj) {
		asynWriter.write(obj);
	}

	public AsynWriter<T> getAsynWriter() {
		return asynWriter;
	}

	public void setAsynWriter(AsynWriter<T> asynWriter) {
		this.asynWriter = asynWriter;
	}

}
