package com.bookroles.controller.vo;

import com.bookroles.exception.AssertUtil;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2023/10/4 11:00
 */
public class R {

    private int code;
    private String msg;
    private Object data;

    public R() {
    }

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R ok(){
        return this.ok(HttpStatus.OK.value(), "请求成功");
    }

    public R ok(int code,String msg){
        AssertUtil.isTrue(code < 200 || code >= 300, "返回值info异常");
        this.setCode(code);
        this.setMsg(msg);
        return this;
    }

    public R fail(Object objVo){
        return this.found(HttpStatus.SEE_OTHER.value(), "没有该条件的记录,Vo:"+objVo.toString());
    }

    public R modifyError(Object objVo){
        return this.foundError(HttpStatus.NOT_FOUND.value(), "修改失败,Vo:"+ objVo.toString());
    }

    public R deleteError(Object objVo){
        return this.foundError(HttpStatus.NOT_FOUND.value(), "删除失败,Vo:" + objVo.toString());
    }

    public R found(int code,String msg){
        AssertUtil.isTrue(code < 300 || code >= 400, "返回值found异常");
        this.setCode(code);
        this.setMsg(msg);
        return this;
    }

    //code 400以后
    public R foundError(int code,String msg){
        AssertUtil.isTrue(code < 400 || code >= 500, "返回值foundError异常");
        this.setCode(code);
        this.setMsg(msg);
        return this;
    }

    public R saveError(Object objVo){
        return this.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败,Vo:" + objVo.toString());
    }

    //code 500以后
    public R error(int code,String msg){
        AssertUtil.isTrue(code < 500 || code >= 600, "返回值error异常");
        this.setCode(code);
        this.setMsg(msg);
        return this;
    }

    public int getCode() {
        return code;
    }

    public R setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public R setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData(){
        return this.data;
    }

    public R setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
