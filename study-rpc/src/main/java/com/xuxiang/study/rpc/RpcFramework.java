package com.xuxiang.study.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by xuxiang on 15/11/24.
 */
public class RpcFramework {

    /**
     * 暴露服务
     * @param service
     * @param port
     * @throws Exception
     */
    public static void export(final Object service, int port) throws Exception{
        if(service == null)
            throw new IllegalAccessException("service instance == null");
        if(port <= 0 || port > 65535)
            throw new IllegalAccessException("Invaild port" + port);
        System.out.println("Export service " + service.getClass().getName() + "on port" + port);
        final ServerSocket serverSocket = new ServerSocket(port);
        for(;;){
            try{
                final Socket socket = serverSocket.accept();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try {
                                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                                try {
                                    String methodName = inputStream.readUTF();
                                    Class<?>[] parameterTypes = (Class < ?>[])inputStream.readObject();
                                    Object[] arguments = (Object[])inputStream.readObject();

                                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                                    try {
                                        Method method = service.getClass().getMethod(methodName, parameterTypes);

                                        Object result = method.invoke(service, arguments);

                                        outputStream.writeObject(result);
                                    }catch (Throwable t){
                                        outputStream.writeObject(t);
                                    }finally {
                                        outputStream.close();
                                    }
                                }finally {
                                    inputStream.close();
                                }
                            }finally {
                                socket.close();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port)
        throws Exception{
        if(interfaceClass == null)
            throw new IllegalAccessException("interfaceClass == null");
        if(!interfaceClass.isInterface())
            throw new IllegalAccessException("The " + interfaceClass.getName() + "must be interface class");
        if(host == null || host.length() == 0)
            throw new IllegalAccessException("Host == null");
        if(port <= 0 || port > 65535)
            throw new IllegalAccessException("Invaild port" + port);
        System.out.println("Get remote service " + interfaceClass.getName() + " from server "
            + host + ":" + port);
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = new Socket(host, port);
                        try {
                            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                            try {
                                outputStream.writeUTF(method.getName());
                                outputStream.writeObject(method.getParameterTypes());
                                outputStream.writeObject(args);
                                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                                try {
                                    Object result = inputStream.readObject();
                                    if(result instanceof Throwable)
                                        throw (Throwable)result;
                                    return result;
                                }finally {
                                    inputStream.close();
                                }
                            }finally {
                                outputStream.close();
                            }
                        }finally {
                            socket.close();
                        }
                    }
                });
    }
}
