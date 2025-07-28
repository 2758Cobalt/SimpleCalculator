package com.cobaltumapps.simplecalculator.v15.google

import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import com.cobaltumapps.simplecalculator.ui.activities.main.MainCalculatorActivity

@RequiresApi(Build.VERSION_CODES.N)
class QuickLaunchCalculatorService : TileService() {
    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

    override fun onTileAdded() {
        qsTile?.state = Tile.STATE_INACTIVE
        qsTile?.updateTile()
    }

    override fun onClick() {
        val tile = qsTile
        val intent = Intent(this, MainCalculatorActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        tile.updateTile()
    }
}
