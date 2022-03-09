package com.ljn.server.pojo.res;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="接口返回对象", description="")
public class RespBean {
    private long code;
    private String message;
    private Object obj;

    public RespBean(long code, String message, Object obj) {
        this.code = code;
        this.message = message;
        this.obj = obj;
    }

    /**
     * 成功返回结果
     * @param message
     * @retrun
     */
    public static RespBean success(String message){
        return new RespBean(200,message,null);
    }
    /**
    * @Description: 成功返回结果
    * @Param: [message, obj]
    * @return: com.ljn.server.pojo.res.RespBean
    */
    public static RespBean success(String message,Object obj){
        return new RespBean(200,message,obj);
    }
    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static RespBean error(String message){
        return new RespBean(500,message,null);
    }
}
