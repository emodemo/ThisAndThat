package com.example.controllers;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

public class MultipartBody {

//	@FormParam("file")
//	@PartType(MediaType.APPLICATION_OCTET_STREAM)
//	public InputStream inputStream;
//
//	@FormParam("fileName")
//	@PartType(MediaType.TEXT_PLAIN)
//	public String fileName;

	private byte[] data;

	public byte[] getData() {
		return data;
	}

	@FormParam("grades")
	@PartType("application/octet-stream")
	public void setData(byte[] data) {
		this.data = data;
	}
}
