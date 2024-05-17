package com.luckin.coffee.server;


import com.luckin.coffee.route.stored.MappingCollection;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Method;


public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // 处理 HTTP 请求
        String uri = request.uri();
        String responseContent;

        MappingCollection mapping = MappingCollection.getInstance();
        Method method = null;
        try {
            method = mapping.get(uri);
        } catch (Exception e) {
            exceptionCaught(ctx,e);
        }

        //获取类名
        assert method != null;
        String className = method.getDeclaringClass().getName();
        Class<?> zClass = Class.forName(className);

        //创建 Class 类的实例
        Object classInstance = zClass.getDeclaredConstructor().newInstance();

        // 获取 class 方法对象
        Method classMethod = zClass.getMethod(method.getName());

        // 调用方法
        responseContent = (String) classMethod.invoke(classInstance);

        // 构建 HTTP 响应
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.copiedBuffer(responseContent, CharsetUtil.UTF_8));

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

        if (HttpUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.writeAndFlush(response);
        } else {
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
