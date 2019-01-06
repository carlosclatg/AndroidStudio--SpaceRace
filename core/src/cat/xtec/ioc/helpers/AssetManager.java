package cat.xtec.ioc.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cat.xtec.ioc.utils.Settings;

public class AssetManager {

    // Sprite Sheet
    public static Texture sheet;
    public static Texture sheet2;
    public static Texture texturepause;

    // Nau i fons
    public static TextureRegion spacecraft, spacecraftDown, spacecraftUp, background;

    // Asteroid
    public static TextureRegion[] asteroid;
    public static Animation asteroidAnim;

    //Moneda
    public static TextureRegion coins;
    public static Sound coinSound;

    //Pause
    public static TextureRegion pause;

    // Explosió
    public static TextureRegion[] explosion;
    public static Animation explosionAnim;

    // Sons
    public static Sound explosionSound;
    public static Sound laserSound;
    public static Music music;

    // Font
    public static BitmapFont font;
    public static BitmapFont font2;

    //BotoLaser
    public static Texture laser;
    public static TextureRegion laserBullet;
    public static TextureRegion laserButton;

    public static void load() {

        laser = new Texture(Gdx.files.internal("bullets.png"));
        laserBullet = new TextureRegion(laser, 0,0,512,512);

        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        sheet2 = new Texture(Gdx.files.internal("coin.png"));
        sheet2.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        coins = new TextureRegion(sheet2, 0,0,10,10);

        //Pause
        texturepause = new Texture(Gdx.files.internal("pause.png"));
        pause = new TextureRegion(texturepause, 0, 0, 16, 16);
        pause.flip(false, true);

        sheet = new Texture(Gdx.files.internal("sheet.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Sprites de la nau
        spacecraft = new TextureRegion(sheet, 0, 0, 36, 15);
        spacecraft.flip(false, true);

        spacecraftUp = new TextureRegion(sheet, 36, 0, 36, 15);
        spacecraftUp.flip(false, true);

        spacecraftDown = new TextureRegion(sheet, 72, 0, 36, 15);
        spacecraftDown.flip(false, true);

        laserButton = new TextureRegion(sheet, 139, 0, 22, 15);
        laserButton.flip(false,true);


        // Carreguem els 16 estats de l'asteroid
        asteroid = new TextureRegion[16];
        for (int i = 0; i < asteroid.length; i++) {

            asteroid[i] = new TextureRegion(sheet, i * 34, 15, 34, 34);
            asteroid[i].flip(false, true);

        }

        // Creem l'animació de l'asteroid i fem que s'executi contínuament en sentit anti-horari
        asteroidAnim = new Animation(0.05f, asteroid);
        asteroidAnim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);

        // Creem els 16 estats de l'explosió
        explosion = new TextureRegion[16];

        // Carreguem els 16 estats de l'explosió
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                explosion[index++] = new TextureRegion(sheet, j * 64,  i * 64 + 49, 64, 64);
                explosion[index-1].flip(false, true);
            }
        }

        // Finalment creem l'animació
        explosionAnim = new Animation(0.04f, explosion);


        // Fons de pantalla
        background = new TextureRegion(sheet, 0, 177, 480, 135);
        background.flip(false, true);

        /******************************* Sounds *************************************/
        // Explosió
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lasershot.wav"));

        //CoinSound
        coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));

        // Música del joc
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Afterburner.ogg"));
        music.setVolume(0.2f);
        music.setLooping(true);

        /******************************* Text *************************************/
        // Font space
        FileHandle fontFile = Gdx.files.internal("fonts/space.fnt");
        font = new BitmapFont(fontFile, true);
        font.getData().setScale(0.4f);
        // TODO Exercici 1c – canvi de tamany de font (creació nou estil)
        font2 = new BitmapFont(fontFile, true);
        font2.getData().setScale(0.3f);
    }

    public static void dispose() {

        // Descrtem els recursos
        sheet.dispose();
        explosionSound.dispose();
        coinSound.dispose();
        music.dispose();

    }
}
