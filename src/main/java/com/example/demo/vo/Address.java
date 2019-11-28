package com.example.demo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ip;
	private String province;
	private String city;
	private String country;
	private String osName; // 操作系统
	private String device; // 设备型号
}
