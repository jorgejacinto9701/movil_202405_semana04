package com.negocio.semana04comsumorest.service;

import com.negocio.semana04comsumorest.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("users")
    public abstract Call<List<User>> listaUsuarios();

}
