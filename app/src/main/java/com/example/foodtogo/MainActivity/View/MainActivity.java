package com.example.foodtogo.MainActivity.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodtogo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        navController = Navigation.findNavController(this, R.id.frag_host);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mealsbycategories), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            // Attempt to navigate to NoNetworkFragment
            navController.navigate(R.id.noNetworkFragment);
        } catch (IllegalArgumentException e) {
            // Log or handle the navigation error
            e.printStackTrace();
        }
        // Listen for network changes
        listenForNetworkChanges();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities networkCapabilities =
                        connectivityManager.getNetworkCapabilities(network);
                return networkCapabilities != null &&
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }
        return false;
    }

    private void listenForNetworkChanges() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    runOnUiThread(() -> {
                        // If the network becomes available, go back to the main flow
                        if (navController.getCurrentDestination().getId() == R.id.noNetworkFragment) {
                            navController.popBackStack(); // Go back to the previous fragment
                        }
                    });
                }

                @Override
                public void onLost(Network network) {
                    runOnUiThread(() -> {
                        // If the network is lost, navigate to the "No Network" fragment
                        navController.navigate(R.id.noNetworkFragment);
                    });
                }
            });
        }
    }
}
