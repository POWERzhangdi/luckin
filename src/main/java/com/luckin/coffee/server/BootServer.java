package com.luckin.coffee.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class BootServer {

    private final int port;

    public BootServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        //用于处理连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //用于处理连接后的数据传输
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建一个服务器启动器
            ServerBootstrap b = new ServerBootstrap();
            //配置服务器的两个事件循环组
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) //指定服务器的通道类型为非阻塞模式的服务器端
                    .option(ChannelOption.SO_BACKLOG, 100) //设置通道的选项，SO_BACKLOG 指定了等待连接的最大队列长度
                    .handler(new LoggingHandler(LogLevel.INFO)) //添加一个日志处理器，记录服务器的各种事件，日志级别为 INFO
                    .childHandler(new ChannelInitializer<SocketChannel>() { //为每个连接创建一个新的通道初始化器，用于设置管道处理器
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new HttpServerCodec()); //添加 HTTP 服务器编解码器，将字节流解析为 HTTP 请求和响应
                            ch.pipeline().addLast(new HttpObjectAggregator(65536)); //添加 HTTP 对象聚合器，将多个 HTTP 消息部分聚合成完整的 HTTP 消息
                            ch.pipeline().addLast(new HttpServerHandler()); //添加自定义的请求处理器 HttpServerHandler，它处理 HTTP 请求并生成响应
                        }
                    });

            ChannelFuture f = b.bind(port).sync(); //绑定服务器到指定端口并启动服务器，sync() 方法阻塞当前线程，直到绑定操作完成
            System.out.println("Server started and listening on " + f.channel().localAddress()); //输出服务器启动成功的消息和监听地址
            f.channel().closeFuture().sync(); //阻塞当前线程，直到服务器通道关闭
        } finally {
            //确保在服务器关闭时，优雅地关闭事件循环组，释放所有资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }



}
