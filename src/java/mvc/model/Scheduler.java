/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ( Cl-sma ) Carlos Pinto Jimenez
 * @Created on 20/04/2016 10:51:44 AM
 */
public class Scheduler {

    private ScheduledExecutorService service = null;
    private Object[] arrSchedules = null;
    private Object[] dataScheduler = null;
    private int countSchedule = 0;
    private final int MIN_BEGIN = 5;
    private final int MIN_DELAY = 5;
    private final TimeUnit MIN_TIMEUNIT = TimeUnit.MINUTES;

    public Scheduler(int poolNumber) {
        if (this.service != null) {
            this.service.shutdown();
        }
        this.service = Executors.newScheduledThreadPool(poolNumber);
        arrSchedules = new Object[poolNumber];
        countSchedule = 0;
    }

    public Scheduler() {
        this.service = Executors.newScheduledThreadPool(10);
        arrSchedules = new Object[10];
    }

    public ScheduledExecutorService getService() {
        return this.service;
    }

    public Scheduler initFixedDelay(String identifier, Runnable schedule, int delay, TimeUnit timeU) {
        dataScheduler = new Object[3];
        dataScheduler[0] = identifier;
        ScheduledFuture sf = service.scheduleWithFixedDelay(schedule, this.MIN_BEGIN, delay, timeU);
        dataScheduler[1] = sf;
        dataScheduler[2] = "Activo";
        arrSchedules[countSchedule] = dataScheduler;
        countSchedule++;
        return this;
    }

    public Scheduler initFixedDelay(String identifier, Runnable schedule) {
        dataScheduler = new Object[3];
        dataScheduler[0] = identifier;
        ScheduledFuture sf = service.scheduleWithFixedDelay(schedule, this.MIN_BEGIN, this.MIN_DELAY, this.MIN_TIMEUNIT);
        dataScheduler[1] = sf;
        dataScheduler[2] = "Activo";
        arrSchedules[countSchedule] = dataScheduler;
        countSchedule++;
        return this;
    }

    public Scheduler initFixedDelay(Runnable schedule) {
        dataScheduler = new Object[3];
        dataScheduler[0] = countSchedule + "";
        ScheduledFuture sf = service.scheduleWithFixedDelay(schedule, this.MIN_BEGIN, this.MIN_DELAY, this.MIN_TIMEUNIT);
        dataScheduler[1] = sf;
        dataScheduler[2] = "Activo";
        arrSchedules[countSchedule] = dataScheduler;
        countSchedule++;
        return this;
    }

    public Scheduler initFixedDelay(Runnable schedule, int delay, TimeUnit timeU) {
        dataScheduler = new Object[3];
        dataScheduler[0] = countSchedule + "";
        ScheduledFuture sf = service.scheduleWithFixedDelay(schedule, this.MIN_BEGIN, delay, timeU);
        dataScheduler[1] = sf;
        dataScheduler[2] = "Activo";
        arrSchedules[countSchedule] = dataScheduler;
        countSchedule++;
        return this;
    }

    public Scheduler initFixedDelay(String identifier, Runnable schedule, int beginAt, int delay, TimeUnit timeunit) {
        dataScheduler = new Object[3];
        dataScheduler[0] = identifier;
        ScheduledFuture sf = service.scheduleWithFixedDelay(schedule, beginAt, delay, timeunit);
        dataScheduler[1] = sf;
        dataScheduler[2] = "Activo";
        arrSchedules[countSchedule] = dataScheduler;
        countSchedule++;
        return this;
    }

    public Scheduler initFixedDelay(Runnable schedule, int beginAt, int delay, TimeUnit timeunit) {
        dataScheduler = new Object[3];
        dataScheduler[0] = countSchedule + "";
        ScheduledFuture sf = service.scheduleWithFixedDelay(schedule, beginAt, delay, timeunit);
        dataScheduler[1] = sf;
        dataScheduler[2] = "Activo";
        arrSchedules[countSchedule] = dataScheduler;
        countSchedule++;
        return this;
    }

    public Scheduler initSchedule(String identifier, Runnable schedule, int delay, TimeUnit timeU) {
        dataScheduler = new Object[3];
        dataScheduler[0] = identifier;
        ScheduledFuture sf = service.schedule(schedule, delay, timeU);
        dataScheduler[1] = sf;
        dataScheduler[2] = "Activo";
        arrSchedules[countSchedule] = dataScheduler;
        countSchedule++;
        return this;
    }

    public Scheduler initSchedule(String identifier, Runnable schedule) {
        dataScheduler = new Object[3];
        dataScheduler[0] = identifier;
        ScheduledFuture sf = service.schedule(schedule, this.MIN_DELAY, this.MIN_TIMEUNIT);
        dataScheduler[1] = sf;
        dataScheduler[2] = "Activo";
        arrSchedules[countSchedule] = dataScheduler;
        countSchedule++;
        return this;
    }

    public Scheduler initSchedule(Runnable schedule) {
        dataScheduler = new Object[3];
        dataScheduler[0] = countSchedule + "";
        ScheduledFuture sf = service.schedule(schedule, this.MIN_DELAY, this.MIN_TIMEUNIT);
        dataScheduler[1] = sf;
        dataScheduler[2] = "Activo";
        arrSchedules[countSchedule] = dataScheduler;
        countSchedule++;
        return this;
    }

    public Scheduler initSchedule(Runnable schedule, int delay, TimeUnit timeU) {
        dataScheduler = new Object[3];
        dataScheduler[0] = countSchedule + "";
        ScheduledFuture sf = service.schedule(schedule, delay, timeU);
        dataScheduler[1] = sf;
        dataScheduler[2] = "Activo";
        arrSchedules[countSchedule] = dataScheduler;
        countSchedule++;
        return this;
    }

    public Scheduler initExecute(Runnable schedule) {
        service.execute(schedule);
        return this;
    }

    public ScheduledFuture getSchedule(String identifier, String estado) {
        Object[] aux;
        if (arrSchedules != null) {
            for (int i = 0; i < arrSchedules.length; i++) {
                aux = (Object[]) arrSchedules[i];
                if (aux[0].equals(identifier) && aux[2].equals(estado)) {
                    return (ScheduledFuture) aux[1];
                }
            }
        }
        return null;
    }

    private Object[] getScheduleData(String identifier) {
        Object[] aux;
        if (arrSchedules != null) {
            for (int i = 0; i < arrSchedules.length; i++) {
                if (arrSchedules[i] == null) {
                    continue;
                }
                aux = (Object[]) arrSchedules[i];
                if (aux[0].equals(identifier)) {
                    return aux;
                }
            }
        }
        return null;
    }

    public void clear(String identifier) {
        Object[] sfO = getScheduleData(identifier);
        ScheduledFuture sf = (ScheduledFuture) sfO[1];
        if (sf != null) {
            sf.cancel(true);
        }
        sfO[2] = "Cancelado";
    }

    public static void clearAfter(final ScheduledFuture sf, int time, TimeUnit timeU) {
        ScheduledExecutorService service_ = Executors.newScheduledThreadPool(2);;
        if (sf != null) {
            service_.schedule(new Runnable() {
                @Override
                public void run() {
                    sf.cancel(true);
                }
            }, time, timeU);
            service_.shutdown();
        }
    }

    public void clearAfter(String identifier, int time, TimeUnit timeU) {
        final Object[] sfO = getScheduleData(identifier);
        final ScheduledFuture sf = (ScheduledFuture) sfO[1];
        ScheduledExecutorService service_ = Executors.newScheduledThreadPool(2);
        if (sf != null) {
            service_.schedule(new Runnable() {

                @Override
                public void run() {
                    sf.cancel(true);
                    sfO[2] = "Cancelado";
                }
            }, time, timeU);
            service_.shutdown();
        }
    }

    public void shutdownScheduler() {
        if (service != null) {
            service.shutdown();
        }
    }

}
