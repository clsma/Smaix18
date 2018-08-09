/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Carlos Pinto Jimenez ( Cl-Sma )
 * @Created 5/08/2016 04:41:17 PM
 * @Archivo ContextListener
 */
public class ContextListener implements ServletContextListener {

    Thread threadCurrent;
    Scheduler scheduler;
    Scheduler ThreadScheduler;
    BlockingQueue<AsyncContextBean> queue;
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
 
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        sce.getServletContext().setAttribute("numberOfSession", 0);
        ThreadScheduler = new Scheduler(1);
        scheduler = new Scheduler(100);
        queue = new LinkedBlockingQueue<AsyncContextBean>();
        sce.getServletContext().setAttribute("schedulerQueue", queue);

        loger.info("Iniciando Scheduler y Queue para peticiones asincronas");

        Runnable runSchedule = new Runnable() {
            @Override
            public void run() {

                if (queue != null && !queue.isEmpty()) {
                    final AsyncContextBean run = queue.poll();

                    if (run != null) {

                        run.getAsync().addListener(new AsyncListener() {
                            @Override
                            public void onComplete(AsyncEvent event) throws IOException {
                                Util.logInfo(" Async Activity " + run.getAsyncIdentifier() + " Completed");
                            }

                            @Override
                            public void onTimeout(AsyncEvent event) throws IOException {
                                Util.logInfo("timeOut Reached on  Async Activity " + run.getAsyncIdentifier());
                            }

                            @Override
                            public void onError(AsyncEvent event) throws IOException {
                                Util.logError(event.getThrowable());
                            }

                            @Override
                            public void onStartAsync(AsyncEvent event) throws IOException {
                                Util.logInfo("Init Async Activity " + run.getAsyncIdentifier());
                            }
                        });
                        loger.info("Ejecutando scheduler " + new Date() + " Proceso " + run.getAsyncIdentifier());
                        scheduler.initExecute(run.getRunner());
                        if (run.isAsyncCompleteImmediate()) {
                            run.getAsync().complete();
                        }

                    }
                }
            }
        };

        ThreadScheduler.initFixedDelay("Executor Scheduler", runSchedule, 5, TimeUnit.SECONDS);

    }

    @Override

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Last number of Sessions : " + sce.getServletContext().getAttribute("numberOfSession"));
        sce.getServletContext().removeAttribute("numberOfSession");

        if (scheduler != null) {
            scheduler.shutdownScheduler();
            loger.info("Terminando Executions Scheduler");
        }
        if (ThreadScheduler != null) {
            ThreadScheduler.shutdownScheduler();
            loger.info("Terminando SchedulerThread");
        }
        if (queue != null) {
            queue.clear();
            loger.info("Limpiando la cola de ejecuciones");
        }

        sce.getServletContext().removeAttribute("schedulerQueue");

    }

}
