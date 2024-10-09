package com.negocio.semana04comsumorest;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.negocio.semana04comsumorest.entity.User;
import com.negocio.semana04comsumorest.service.UserService;
import com.negocio.semana04comsumorest.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //Al crear el spinner se crean tres objectos
    Spinner   spnUsuarios;
    ArrayAdapter<String> adaptadorUsuarios;
    ArrayList<String> listaUsuarios = new ArrayList<String>();

    Button btnFiltrar;
    TextView txtResultado;

    //Servicio de usuario de retrofit
    UserService userService;

    //Aqui estaran toda la data de usuarios
    List<User> lstSalida ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adaptadorUsuarios = new ArrayAdapter<String>(
                this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listaUsuarios);
        spnUsuarios = findViewById(R.id.spnUsuarios);
        spnUsuarios.setAdapter(adaptadorUsuarios);

        btnFiltrar = findViewById(R.id.btnFiltrar);
        txtResultado = findViewById(R.id.txtResultado);

        userService = ConnectionRest.getConnecion().create(UserService.class);

        cargaUsuarios();

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 int idUsuario = (int)spnUsuarios.getSelectedItemId();
                 User objUser = lstSalida.get(idUsuario);
                 String salida = "User: ";
                 salida += "Name : " + objUser.getName() +"\n";
                 salida += "Email : " + objUser.getEmail() +"\n";
                 salida += "Address => Street  : " + objUser.getAddress().getStreet() +"\n";
                 salida += "Address => suite  : " + objUser.getAddress().getSuite() +"\n";
                 salida += "Address => Geo => Lat  : " + objUser.getAddress().getGeo().getLat() +"\n";
                 salida += "Address => Geo => Long  : " + objUser.getAddress().getGeo().getLng() +"\n";
                 salida += "Phone : " + objUser.getPhone() +"\n";
                 salida += "company => name : " + objUser.getCompany().getName() +"\n";
                 txtResultado.setText(salida);

            }
        });

    }

    void cargaUsuarios(){
       Call<List<User>> call =  userService.listaUsuarios();
       call.enqueue(new Callback<List<User>>() {
           @Override
           public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                //Las respuesta es exitosa  del servicio Rest
               if (response.isSuccessful()){
                   lstSalida = response.body();
                   for (User obj: lstSalida){
                       listaUsuarios.add(obj.getName());
                   }
                   adaptadorUsuarios.notifyDataSetChanged();
               }
           }
           @Override
           public void onFailure(Call<List<User>> call, Throwable t) {
                //No existe respuesta del servicio Rest

           }
       });
    }
}