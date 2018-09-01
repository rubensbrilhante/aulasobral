package com.teste.myapplication;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import static com.teste.myapplication.PlacesConsts.PLACE_ARCO;
import static com.teste.myapplication.PlacesConsts.PLACE_CULTURA;
import static com.teste.myapplication.PlacesConsts.PLACE_MUSEU;
import static com.teste.myapplication.PlacesConsts.PLACE_ROSARIO;
import static com.teste.myapplication.PlacesConsts.PLACE_SE;
import static com.teste.myapplication.PlacesConsts.PLACE_SOBRAL;
import static com.teste.myapplication.PlacesConsts.PLACE_THEATRO;

public enum Place {

    SOBRAL(PLACE_SOBRAL, "Sobral"),
    THEATRO(PLACE_THEATRO, "Theatro São João"),
    MUSEU(PLACE_MUSEU, "Museu Dom José"),
    CULTURA(PLACE_CULTURA, "Casa da Cultura"),
    ARCO(PLACE_ARCO, "Arco de Nossa Senhora de Fátima"),
    ROSARIO(PLACE_ROSARIO, "Igreja do Rosário"),
    SE(PLACE_SE, "Igreja da Sé");

    public final LatLng latLng;
    public final String nome;

    Place(LatLng latLng, String nome) {
        this.latLng = latLng;
        this.nome = nome;
    }

    public static Place getByName(@NonNull String name) {
        Place[] values = Place.values();
        for (Place p : values) {
            if (name.equalsIgnoreCase(p.nome)) {
                return p;
            }
        }
        return SOBRAL;
    }

}
