package com.briup.common;

import java.sql.ResultSet;

public interface GetValueHelperForRS {
	//处理结果集rs
	Object getValue(ResultSet rs) throws Exception;
}
