package com.skyfilm.owner.communication;

import com.skyfilm.owner.exception.CsqException;

public interface String2Object<T> {
	T string2Object(String data) throws CsqException;
} 
