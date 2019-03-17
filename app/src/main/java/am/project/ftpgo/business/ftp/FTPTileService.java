// ###WS@M Project:PDFelement ###
package am.project.ftpgo.business.ftp;

import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import am.project.ftpgo.R;
import androidx.annotation.RequiresApi;

@RequiresApi(24)
public class FTPTileService extends TileService {

    @Override
    public void onClick() {
        super.onClick();
        final boolean active;
        if (FTPService.isStarted()) {
            FTPService.stop(this);
            active = false;
        } else {
            FTPService.start(this);
            active = true;
        }
        updateTile(active);
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTile(FTPService.isStarted());
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        updateTile(FTPService.isStarted());
    }

    private void updateTile(boolean active) {
        final Tile tile = getQsTile();
        if (active) {
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_tile_ftp_active));
            tile.setLabel(getString(R.string.ftp_tile_label_active));
            tile.setState(Tile.STATE_ACTIVE);
        } else {
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_tile_ftp_inactive));
            tile.setLabel(getString(R.string.ftp_tile_label_inactive));
            tile.setState(Tile.STATE_INACTIVE);
        }
        tile.updateTile();
    }
}