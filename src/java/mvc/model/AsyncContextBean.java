/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model;

import javax.servlet.AsyncContext;

/**
 *
 * @author Carlos Pinto Jimenez ( Cl-Sma )
 * @Created 17/01/2017 05:02:59 PM
 * @Archivo AsyncContextBean
 */
public class AsyncContextBean {

    private AsyncContext async;
    private Runnable runner;
    private String AsyncIdentifier;
    private boolean asyncCompleteImmediate;

    public AsyncContextBean(AsyncContext async, Runnable runner, String AsyncIdentifier) {
        this.async = async;
        this.runner = runner;
        this.AsyncIdentifier = AsyncIdentifier;
    }

    public AsyncContext getAsync() {
        return async;
    }

    public void setAsync(AsyncContext async) {
        this.async = async;
    }

    public Runnable getRunner() {
        return runner;
    }

    public void setRunner(Runnable runner) {
        this.runner = runner;
    }

    public String getAsyncIdentifier() {
        return AsyncIdentifier;
    }

    public void setAsyncIdentifier(String AsyncIdentifier) {
        this.AsyncIdentifier = AsyncIdentifier;
    }

    public boolean isAsyncCompleteImmediate() {
        return asyncCompleteImmediate;
    }

    public void setAsyncCompleteImmediate(boolean asyncCompleteImmediate) {
        this.asyncCompleteImmediate = asyncCompleteImmediate;
    }

}
