package com.wow.wowmeet.screens.main.map;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Event;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View infoView;

    public MapInfoWindowAdapter(View infoView) {
        this.infoView = infoView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        ImageView imgProfile = (ImageView) infoView.findViewById(R.id.imgMapProfilePicture);
        TextView txtUsername = (TextView) infoView.findViewById(R.id.txtMapUsername);
        TextView txtActivityType = (TextView) infoView.findViewById(R.id.txtActivityType);
        TextView txtActivityTime = (TextView) infoView.findViewById(R.id.txtActivityTime);
        Button btnGo = (Button) infoView.findViewById(R.id.btnGo);

        Event e = (Event) marker.getTag();
        txtUsername.setText(e.getCreator().getUsername());

        return infoView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
