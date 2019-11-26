package com.reyco.test.core.domain;

public class Result {

	private Integer code;
	private Object data;
	private String msg;
	
	public Result() {
	}
	public Result(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public static Result success() {
		return success(200,null);
	}
	public static Result success(Object data) {
		return success(200,data);
	}
	public static Result success(Integer code,Object data) {
		Result result = new Result();
		result.setCode(code);
		result.setData(data);
		result.setMsg(null);
		return result;
	}
	public static Result fail() {
		return success(201,"未知失败，请联系管理员");
	}
	public static Result fail(String msg) {
		return success(201,msg);
	}
	public static Result fail(Integer code,String msg) {
		Result result = new Result();
		result.setCode(code);
		result.setData(null);
		result.setMsg(msg);
		return result;
	}
	public static Result error() {
		return success(500,"未知异常，请联系管理员");
	}
	public static Result error(String msg) {
		return success(500,msg);
	}
	public static Result error(Integer code,String msg) {
		Result result = new Result();
		result.setCode(code);
		result.setData(null);
		result.setMsg(msg);
		return result;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
