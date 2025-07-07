package mod.base.components.worlds.graphics.offline.interfaces;

import game_logic.repositories.Identifier;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import mod.base.components.worlds.party.GameParty;
import mod.base.modification.graphics.components.SoundsGraphicComponent;

public class PartyMenu extends SoundsGraphicComponent {
    private GameParty party;
    private ImageView paint;
    public PartyMenu(Identifier id) {
        super(id);
        paint = new ImageView();
        getAnchorPane().getChildren().add(paint);
    }

    @Override
    public void CustomUpdate() {
        super.CustomUpdate();
        if (party != null) {
            Canvas c = party.getFrame();
            if (c != null) {
                paint.setImage(c.snapshot(null, null));
            }
        }
    }

    @Override
    public void onRemoveFromScreen() {
        super.onRemoveFromScreen();
        if (party != null) {
            party.end();
        }
    }

    @Override
    public void back() {
        super.back();
        party.end();
    }

    public void setGameParty (GameParty party) {
        if (party != null)
            this.party = party;
    }
}
