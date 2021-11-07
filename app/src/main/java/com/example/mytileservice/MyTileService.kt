package com.example.mytileservice

import android.content.Intent
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.N)
class MyTileService : TileService() {
    override fun onClick() {
        super.onClick()
        val i = Intent(this, MyService::class.java)

        if (qsTile.state != Tile.STATE_ACTIVE) {
            qsTile.state = Tile.STATE_ACTIVE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                startForegroundService(i)
            else
                startService(i)
        } else {
            qsTile.state = Tile.STATE_INACTIVE
            stopService(i)
        }

        qsTile.updateTile()
    }

    override fun onTileAdded() {
        // Set inactive state when tile added
        qsTile.state = Tile.STATE_INACTIVE
        qsTile.updateTile()
    }

    override fun onTileRemoved() {
    }

    override fun onStartListening() {
    }

    override fun onStopListening() {
    }
}