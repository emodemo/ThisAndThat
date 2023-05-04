package com.pocs;

public interface SomeIface {

	default String getImplName(){
		return getClass().getSimpleName();
	}

}

class SomeImpl implements SomeIface{

}
