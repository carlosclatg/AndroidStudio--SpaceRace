package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Set;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;


public class SplashScreen implements Screen {

    private Stage stage;
    private SpaceRace game;

    private Label.LabelStyle textStyle;
    private Label.LabelStyle textStyle2;
    private Label textLbl;
    private Label textLb2;

    public SplashScreen(SpaceRace game) {

        this.game = game;

        // Creem la càmera de les dimensions del joc
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        // Posant el paràmetre a true configurem la càmera per a
        // que faci servir el sistema de coordenades Y-Down
        camera.setToOrtho(true);

        // Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        // Creem l'stage i assginem el viewport
        stage = new Stage(viewport);

        // Afegim el fons
        stage.addActor(new Image(AssetManager.background));

        // Creem l'estil de l'etiqueta i l'etiqueta
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("SpaceRace", textStyle);

        // Creem el contenidor necessari per aplicar-li les accions
        Container container = new Container(textLbl);
        container.setTransform(true);
        container.center();
        // TODO Exercici 1a – Situa el títol a 1/3 part de la pantalla, es canvia el divisor.
        container.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 3);

        // Afegim les accions de escalar: primer es fa gran i després torna a l'estat original ininterrompudament
        container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 1), Actions.scaleTo(1, 1, 1))));
        // TODO Exercici 1b – afegeixo dos actions que es repetixen per sempre.
        container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.alpha(1, 3/2, Interpolation.linear), Actions.alpha(0, 3/2, Interpolation.linear) )));
        stage.addActor(container);

        // Creem la imatge de la nau i li assignem el moviment en horitzontal
        //Image spacecraft = new Image(AssetManager.spacecraft);

        //float y = Settings.GAME_HEIGHT / 2 + textLbl.getHeight();
        // TODO Exercici 1e posició aleatoria nau i puja o baixa segons Y.
        Image spacecraft;
        float y = Methods.randomFloat(0, Settings.GAME_HEIGHT);
        float yfinal = Methods.randomFloat(0, Settings.GAME_HEIGHT);

        if(y - yfinal > 0){
            spacecraft = new Image (AssetManager.spacecraftUp);
        } else if (y - yfinal < 0){
            spacecraft = new Image (AssetManager.spacecraftDown);
        } else {
            spacecraft = new Image (AssetManager.spacecraft);
        }
        spacecraft.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.moveTo(0 - spacecraft.getWidth(), y), Actions.moveTo(Settings.GAME_WIDTH, yfinal, 5))));
        stage.addActor(spacecraft);

        // TODO Exercici 1c – Situa el títol "Tap Screen to Start" a 2/3 part de la pantalla, es canvia el divisor.
        textStyle2 = new Label.LabelStyle(AssetManager.font2, null);
        textLb2 = new Label ("Tap Screen to start", textStyle2);
        Container container2 = new Container(textLb2);
        container2.setTransform(true);
        container2.center();
        container2.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT * 2 / 3);
        // TODO Exercici 1d –Animació de parpelleig
        container2.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.alpha(1, (float) 0.5, Interpolation.linear), Actions.alpha(0, (float) 0.5, Interpolation.linear) )));
        stage.addActor(container2);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        stage.draw();
        stage.act(delta);

        // Si es fa clic en la pantalla, canviem la pantalla
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
