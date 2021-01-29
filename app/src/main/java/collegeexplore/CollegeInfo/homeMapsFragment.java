/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package collegeexplore.CollegeInfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class homeMapsFragment extends Fragment {
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    public static homeMapsFragment newInstance() {
        homeMapsFragment f = new homeMapsFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", 1);
        f.setArguments(args);

        return  f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*GoogleMapOptions options = new GoogleMapOptions();

        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(true)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);  */



        // SupportMapFragment.newInstance();
      //  GoogleMap mGoogleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SupportMapFragment mSupportMapFragment;

        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
      //  mSupportMapFragment.getMapAsync((OnMapReadyCallback) this);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null)
        {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {

                        googleMap.getUiSettings().setAllGesturesEnabled(true);

                       // -> marker_latlng // MAKE THIS WHATEVER YOU WANT

                        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(-33.87365, 151.20689) ).zoom(15.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        googleMap.moveCamera(cameraUpdate);

                    }

                }
            });

    }

        return inflater.inflate(R.layout.home_maps_layout, container, false);


}

    public void onDestroyView() {
        super.onDestroyView();

        try {
            Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
