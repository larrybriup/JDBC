package com.briup.common;

import java.sql.PreparedStatement;

public interface SetValueHelperForPS {
	void setValue(PreparedStatement ps,Object o) throws Exception;
}
