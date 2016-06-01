package com.example.admin.androidmapsproject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Mendez Soto on 5/31/2016.
 */
public interface InterfaceApi<Object> {
    public boolean Save(Object object);

    public boolean Update(Object object);

    public boolean Delete(Object object);

    public ArrayList<Object> GetAll() throws ExecutionException, InterruptedException;

    public ArrayList<Object> GetBy(Object object);
}
