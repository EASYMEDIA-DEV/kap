package com.kap.core.utility;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

@Slf4j
public class ResourceCloseUtil {
    /**
     * Resource close 처리.
     * @param resources
     */
    public static void close(Closeable... resources)
    {
        for (Closeable resource : resources)
        {
            if (resource != null)
            {
                try
                {
                    resource.close();
                }
                catch (IOException ignore)
                {
                    //KISA 보안약점 조치 (2018-10-29, 윤창원)
                    log.debug("Occurred IOException to close resource is ingored!!");
                }
                catch (Exception ignore)
                {
                    log.debug("Occurred Exception to close resource is ingored!!");
                }
            }
        }
    }

    /**
     * JDBC 관련 resource 객체 close 처리
     * @param objects
     */
    public static void closeDBObjects(Wrapper... objects)
    {
        for (Object object : objects)
        {
            if (object != null)
            {
                if (object instanceof ResultSet)
                {
                    try
                    {
                        ((ResultSet)object).close();
                    }
                    catch (SQLException ignore)
                    {
                        //KISA 보안약점 조치 (2018-10-29, 윤창원)
                        log.debug("Occurred SQLException to close resource is ingored!!");
                    }
                    catch (Exception ignore)
                    {
                        log.debug("Occurred Exception to close resource is ingored!!");
                    }
                }
                else if (object instanceof Statement)
                {
                    try
                    {
                        ((Statement)object).close();
                    }
                    catch (SQLException ignore)
                    {
                        //KISA 보안약점 조치 (2018-10-29, 윤창원)
                        log.debug("Occurred SQLException to close resource is ingored!!");
                    }
                    catch (Exception ignore)
                    {
                        log.debug("Occurred Exception to close resource is ingored!!");
                    }
                }
                else if (object instanceof Connection)
                {
                    try
                    {
                        ((Connection)object).close();
                    }
                    catch (SQLException ignore)
                    {
                        log.debug("Occurred SQLException to close resource is ingored!!");
                    }
                    catch (Exception ignore)
                    {
                        log.debug("Occurred Exception to close resource is ingored!!");
                    }
                }
                else
                {
                    throw new IllegalArgumentException("Wrapper type is not found : " + object.toString());
                }
            }
        }
    }

    /**
     * Socket 관련 resource 객체 close 처리
     * @param socket
     */
    public static void closeSocketObjects(Socket socket, ServerSocket server)
    {
        if (socket != null)
        {
            try
            {
                socket.shutdownOutput();
            }
            catch (IOException ignore)
            {
                log.debug("Occurred IOException to close resource is ingored!!");
            }
            catch (Exception ignore)
            {
                log.debug("Occurred Exception to shutdown ouput is ignored!!");
            }

            try
            {
                socket.close();
            }
            catch (IOException ignore)
            {
                log.debug("Occurred IOException to close resource is ingored!!");
            }
            catch (Exception ignore)
            {
                log.debug("Occurred Exception to close resource is ignored!!");
            }
        }

        if (server != null)
        {
            try
            {
                server.close();
            }
            catch (IOException ignore)
            {
                log.debug("Occurred IOException to close resource is ingored!!");
            }
            catch (Exception ignore)
            {
                log.debug("Occurred Exception to close resource is ignored!!");
            }
        }
    }

    /**
     *  Socket 관련 resource 객체 close 처리
     *
     * @param sockets
     */
    public static void closeSockets(Socket... sockets)
    {
        for (Socket socket : sockets)
        {
            if (socket != null)
            {
                try
                {
                    socket.shutdownOutput();
                }
                catch (IOException ignore)
                {
                    log.debug("Occurred IOException to close resource is ingored!!");
                }
                catch (Exception ignore)
                {
                    log.debug("Occurred Exception to shutdown ouput is ignored!!");
                }

                try
                {
                    socket.close();
                }
                catch (IOException ignore)
                {
                    log.debug("Occurred IOException to close resource is ingored!!");
                }
                catch (Exception ignore)
                {
                    log.debug("Occurred Exception to close resource is ignored!!");
                }
            }
        }
    }
}
