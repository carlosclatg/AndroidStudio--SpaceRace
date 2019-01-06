package cat.xtec.ioc;

import com.badlogic.gdx.Game;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.screens.SplashScreen;

public class SpaceRace extends Game {

    @Override
    public void create() {

        // A l'iniciar el joc carreguem els recursos
        AssetManager.load();
        // I definim la pantalla d'splash com a pantalla
        setScreen(new SplashScreen(this));

    }

    // Cridem per descartar els recursos carregats.
    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();

    }
}