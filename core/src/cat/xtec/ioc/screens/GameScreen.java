package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.helpers.InputHandler;
import cat.xtec.ioc.objects.Asteroid;
import cat.xtec.ioc.objects.Explosion;
import cat.xtec.ioc.objects.Laser;
import cat.xtec.ioc.objects.Pause;
import cat.xtec.ioc.objects.ScrollHandler;
import cat.xtec.ioc.objects.Spacecraft;
import cat.xtec.ioc.utils.Settings;

public class GameScreen implements Screen {

    public ArrayList<Laser> getArraylasers() {
        return arraylasers;
    }

    public void setArraylasers(ArrayList<Laser> arraylasers) {
        this.arraylasers = arraylasers;
    }

    // Els estats del joc
    public enum GameState {

        READY, RUNNING, GAMEOVER, PAUSE

    }

    private GameState currentState;

    // Objectes necessaris
    private Stage stage;
    private Spacecraft spacecraft;
    private ScrollHandler scrollHandler;

    // Encarregats de dibuixar elements per pantalla
    private ShapeRenderer shapeRenderer;
    private Batch batch;

    // Per controlar l'animació de l'explosió
    private float explosionTime = 0;

    // Preparem el textLayout per escriure text
    private GlyphLayout textLayout;

    //text puntuacio
    private Label.LabelStyle textStyle;
    private String StringPuntuacio;
    private Label LabelPuntuacio;
    private Container container;

    //lasers
    private ArrayList<Laser> arraylasers;
    private ArrayList<Explosion> arrayExplosions;


    public GameScreen(Batch prevBatch, Viewport prevViewport) {

        // Iniciem la música
        AssetManager.music.play();

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem l'stage i assginem el viewport
        stage = new Stage(prevViewport, prevBatch);

        batch = stage.getBatch();

        // Creem la nau i la resta d'objectes
        spacecraft = new Spacecraft(Settings.SPACECRAFT_STARTX, Settings.SPACECRAFT_STARTY, Settings.SPACECRAFT_WIDTH, Settings.SPACECRAFT_HEIGHT);
        scrollHandler = new ScrollHandler();

        // Inicialitzem puntuacio
        StringPuntuacio = "0";
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        LabelPuntuacio = new Label(StringPuntuacio, textStyle);
        LabelPuntuacio.setText(StringPuntuacio);
        container = new Container(LabelPuntuacio);
        container.setTransform(true);
        container.setPosition(Settings.SPACECRAFT_STARTX, 0 + Settings.SPACECRAFT_HEIGHT);
        container.setName("containerpuntuacio");

        //GlyphLayout(BitmapFont font, java.lang.CharSequence str, Color color, float targetWidth, int halign, boolean wrap)
        spacecraft.setName("spacecraft");

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(spacecraft);
        stage.addActor(container);
        // Donem nom a l'Actor


        //Actor de Pause
        Image pause = new Image (AssetManager.pause);
        pause.setPosition((Settings.GAME_WIDTH) - pause.getWidth() - 5, 5);
        pause.setName(Settings.PAUSESTRING);
        pause.setVisible(true);
        stage.addActor(pause);


        //Actor de BotoLaser
        Image laserButton = new Image(AssetManager.laserButton);
        laserButton.setVisible(true);
        laserButton.setName(Settings.LASERBUTTON);
        laserButton.setPosition((Settings.GAME_WIDTH) - laserButton.getWidth() - 10, (Settings.GAME_HEIGHT) - laserButton.getHeight() - 5);
        stage.addActor(laserButton);

        //Lasers
        arraylasers = new ArrayList<Laser>();
        arrayExplosions = new ArrayList<Explosion>();



        // Iniciem el GlyphLayout
        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "Are you\nready?");
        currentState = GameState.READY;

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

    }

    private void drawElements() {

        // Recollim les propietats del Batch de l'Stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Pintem el fons de negre per evitar el "flickering"
        //Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));

        // Pintem la nau
        shapeRenderer.rect(spacecraft.getX(), spacecraft.getY(), spacecraft.getWidth(), spacecraft.getHeight());

        // Recollim tots els Asteroid
        ArrayList<Asteroid> asteroids = scrollHandler.getAsteroids();
        Asteroid asteroid;

        for (int i = 0; i < asteroids.size(); i++) {

            asteroid = asteroids.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(asteroid.getX() + asteroid.getWidth() / 2, asteroid.getY() + asteroid.getWidth() / 2, asteroid.getWidth() / 2);

        }

        shapeRenderer.end();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

            // Dibuixem tots els actors de l'stage
            stage.draw();

            // Depenent de l'estat del joc farem unes accions o unes altres
            switch (currentState) {

                case GAMEOVER:
                    updateGameOver(delta);
                    break;
                case RUNNING:
                    updateRunning(delta);
                    break;
                case READY:
                    updateReady();
                    break;
                // TODO Exercici 3 -PAUSE, posem un delta = 0// TODO Exercici 3 -PAUSE, posem un delta = 0 (Settings.PauseInt) per simular la pausa (actors quiets)
                case PAUSE:
                    textLayout.setText(AssetManager.font, "Pause");
                    updatePause(Settings.PAUSEINT);
                    break;

            }

        //drawElements();

    }

    // TODO Exercici 3 -PAUSE, a aquesta funció arriba un delta = 0, imatges estàtiques doncs.
    private void updatePause(float delta) {
        stage.act(delta);
        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        batch.end();

    }

    private void updateReady() {

        //Resetejem per a que el coin surti del fons i no de la ultima posicio

        scrollHandler.getCoin().reset(Settings.GAME_WIDTH);
        // Dibuixem el text al centre de la pantalla
        batch.begin();
        // TODO Exercici 1a – Situa el títol a 1/3 part de la pantalla, es canvia el divisor.
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 3) - textLayout.height / 2);
        //stage.addActor(textLbl);
        batch.end();

    }

    private synchronized void updateRunning(float delta) {

        stage.act(delta);

        if (scrollHandler.collides(spacecraft)) {
            // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
            AssetManager.explosionSound.play();
            stage.getRoot().findActor("spacecraft").remove();
            textLayout.setText(AssetManager.font, "Game Over :'(");
            StringPuntuacio = "0";
            LabelPuntuacio.setText(StringPuntuacio);
            currentState = GameState.GAMEOVER;
            // TODO Exercici 4 - esborrem lasers
            for (Iterator<Laser> iterator = arraylasers.iterator(); iterator.hasNext(); ) {
                Laser laser = iterator.next();
                iterator.remove();
                laser.remove();

            }
        }
        // TODO Exercici 2 - captura de moneda
        if (scrollHandler.isCapturedCoin(spacecraft)) {
            //Incrementem en 100 la puntuació
            //incrementaPuntuacio();
            int p = Integer.parseInt(StringPuntuacio);
            p = p + 100;
            StringPuntuacio = Integer.toString(p);
            //GlyphLayout(BitmapFont font, java.lang.CharSequence str, Color color, float targetWidth, int halign, boolean wrap)
            LabelPuntuacio.setText(StringPuntuacio);
            container.addAction(Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 0.3f, Interpolation.linear), Actions.scaleTo(1, 1, 0.3f, Interpolation.linear)));
            stage.addActor(container);
            AssetManager.coinSound.play();

        }

        //Condicions de laser y asteroid
        // TODO Exercici 4 - veiem si creua el laser amb asteroide.
        for (Iterator<Laser> iterator = arraylasers.iterator(); iterator.hasNext(); ){
            Laser laser = iterator.next();
            if (laser.isRigthOfScreen()) {
                iterator.remove();
                laser.remove();
            } else {
                Asteroid asteroid = scrollHandler.collidesLaser(laser);
                if (asteroid != null) {
                    // Si hi ha hagut col·lisió: reproducció del so i de l'animació i eliminem el laser i l'asteroide
                    AssetManager.explosionSound.play();
                    arrayExplosions.add(new Explosion(AssetManager.explosionAnim, (asteroid.getX() + asteroid.getWidth() / 2 - 16) ,
                             asteroid.getY() - 16 + asteroid.getHeight() / 2 , asteroid.getWidth(),asteroid.getWidth(), delta));
                    arraylasers.remove(laser);
                    laser.remove();
                    scrollHandler.removeAsteroid(asteroid);
                    break;
                }
            }

        }

        // TODO Exercici 4 - veiem si creua el laser amb asteroide.
        if (arrayExplosions.size() > 0) {
            for (Explosion e : arrayExplosions) {
                if (!e.isFinished()) {
                    batch.begin();
                    // Si l'animació no ha finalitzat
                    batch.draw(e.getAnim().getKeyFrame(e.getDelta(), true), e.getX(), e.getY(), e.getWidth(), e.getHeight());
                    batch.end();
                    e.setDelta(e.getDelta() + delta);
                    break;
                } else {
                    //Si l'animació ha finalitzat
                    arrayExplosions.remove(e);
                    break;
                }
            }
        }

    }

    /*private void incrementaPuntuacio() {
        int p = Integer.parseInt(StringPuntuacio);
        p = p + 100;
        StringPuntuacio =  String.valueOf(p);
        puntuacio.setText(AssetManager.font, StringPuntuacio, Color.BLUE, 0, Align.topLeft, true);
    }*/

    private void updateGameOver(float delta) {
        stage.act(delta);

        batch.begin();
        // TODO Exercici 1a – Situa el títol a 1/3 part de la pantalla, es canvia el divisor.
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH - textLayout.width) / 2, (Settings.GAME_HEIGHT - textLayout.height) / 3);
        // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
        batch.draw(AssetManager.explosionAnim.getKeyFrame(explosionTime, false), (spacecraft.getX() + spacecraft.getWidth() / 2) - 32, spacecraft.getY() + spacecraft.getHeight() / 2 - 32, 64, 64);
        batch.end();

        explosionTime += delta;

    }

    public void reset() {

        // Posem el text d'inici
        textLayout.setText(AssetManager.font, "Are you\nready?");
        // Cridem als restart dels elements.
        spacecraft.reset();
        scrollHandler.reset();


        // Posem l'estat a 'Ready'
        currentState = GameState.READY;

        // Afegim la nau a l'stage
        stage.addActor(spacecraft);
        StringPuntuacio = "0";

        // Posem a 0 les variables per controlar el temps jugat i l'animació de l'explosió
        explosionTime = 0.0f;

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

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public Stage getStage() {
        return stage;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        // TODO Exercici 3 - PAUSE - Segons estat ocultem o no els botons, i baixem o pujem la música
        switch (currentState){
            case PAUSE:
                stage.getRoot().findActor(Settings.PAUSESTRING).setVisible(false);
                AssetManager.music.setVolume(0.1f); //BAixem volum
                break;

            case READY:
                stage.getRoot().findActor(Settings.PAUSESTRING).setVisible(false);
                break;
            case RUNNING:
                AssetManager.music.setVolume(1f);
                stage.getRoot().findActor(Settings.PAUSESTRING).setVisible(true);
                break;
            case GAMEOVER:
                break;
        }

        this.currentState = currentState;
    }
}
